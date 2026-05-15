package com.microservices.cart.service.impl;

import com.microservices.cart.client.ProductClient;
import com.microservices.cart.dto.CartDTO;
import com.microservices.cart.dto.CartRequestDTO;
import com.microservices.cart.external.Product;
import com.microservices.cart.model.Cart;
import com.microservices.cart.model.CartItem;
import com.microservices.cart.repository.CartItemRepository;
import com.microservices.cart.repository.CartRepository;
import com.microservices.cart.service.CartService;
import com.microservices.cart.util.DTOBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductClient productClient;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    DTOBuilder dtoBuilder;

    @Override
    public CartDTO addToCart(CartRequestDTO cartRequestDto, HttpServletRequest httpServletRequest) {

        String x_user_id = httpServletRequest.getHeader("X-USER-ID");
        if(x_user_id == null) {
            throw new RuntimeException("Unauthorized access");
        }

        Long userId = Long.valueOf(x_user_id);

        // fetch cart details if a cart exists
        Cart cart = cartRepository.findByUserId(userId);

        // create a new instance of cart
        if(cart == null) {
            cart = new Cart();
        }
        // fetch product details
        Long productId = cartRequestDto.getProductId();
        Product product = productClient.getProductById(productId);

        // set cart details
        cart.setUserId(userId);
        cart.setTotalAmount(
                cart.getTotalAmount() +  product.getAmount()
        );
        cartRepository.save(cart);

        // update existing cart item if present
        CartItem cartItem = new CartItem();
        for(CartItem item: cart.getCartItems()) {
            if(item.getProductId().equals(productId)) {
                cartItem.setCartItemId(item.getCartItemId());
                cartItem.setQuantity(item.getQuantity());
            }
        }

        cartItem.setQuantity(
                cartItem.getQuantity() + 1
        );
        cartItem.setAmount(product.getAmount());
        cartItem.setProductId(product.getProductId());
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        // update cartItem details on cart
        List<CartItem> cartItems = cart.getCartItems()
                .stream()
                .filter(item -> item.getProductId().equals(productId))
                .toList();

        if(cartItems.isEmpty()) {
            cart.setCartItem(cartItem);
        } else {
            List<CartItem> cartItems1 = new ArrayList<>();
            for(CartItem item: cart.getCartItems()) {
                if(item.getProductId().equals(productId)) {
                    item.setQuantity(cartItem.getQuantity());
                }
                cartItems1.add(item);
            }
            cart.setCartItems(cartItems1);
        }

        return dtoBuilder.CartDtoBuilder(cart);
    }

    @Override
    public CartDTO fetchCartDetails(HttpServletRequest httpServletRequest) {
        String x_user_id = httpServletRequest.getHeader("X-USER-ID");
        if(x_user_id == null) {
            throw new RuntimeException("Unauthorized access");
        }

        Long userId = Long.valueOf(x_user_id);

        // fetch cart details if a cart exists
        Cart cart = cartRepository.findByUserId(userId);
        if(cart == null) {
            throw new RuntimeException("Cart not found");
        }

        return dtoBuilder.CartDtoBuilder(cart);
    }

    @Override
    public void handleProductUpdate(HttpServletRequest httpServletRequest, Long productId) {
        String userId = httpServletRequest.getHeader("X-USER-ID");
        if(userId == null) return;

        // fetch cart details if a cart exists
        Cart cart = cartRepository.findByUserId(Long.valueOf(userId));
        if(cart == null) {
            return;
        }

        Product product = productClient.getProductById(productId);

        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem: cartItems) {
            if(cartItem.getProductId().equals(productId)) {

                cart.setTotalAmount(
                        cart.getTotalAmount() - ( cartItem.getAmount() * cartItem.getQuantity() ) + ( product.getAmount() * cartItem.getQuantity() )
                );

                CartItem cartItem1 = new CartItem();
                cartItem1.setAmount(product.getAmount());
                cartItem1.setQuantity(cartItem.getQuantity());
                cartItem1.setCartItemId(cartItem.getCartItemId());
                cartItem1.setProductId(cartItem.getProductId());
                cartItem1.setCart(cart);

                cartItemRepository.save(cartItem1);
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public void handleProductDelete(HttpServletRequest httpServletRequest, Long productId) {

        String userId = httpServletRequest.getHeader("X-USER-ID");
        if(userId == null) return;

        // fetch cart details if a cart exists
        Cart cart = cartRepository.findByUserId(Long.valueOf(userId));
        if(cart == null) {
            return;
        }

        // compute total cart amount
        Double totalAmount = 0.0;
        for(CartItem cartItem: cart.getCartItems()) {
            totalAmount += cartItem.getAmount();
        }
        cart.setTotalAmount(totalAmount);

        // delete empty cart
        if(cart.getCartItems().isEmpty()) {
            cartRepository.delete(cart);
            return;
        }

        cartRepository.save(cart);
    }
}
