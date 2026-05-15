package com.microservices.cart.cart.service.impl;

import com.microservices.cart.cart.client.ProductClient;
import com.microservices.cart.cart.dto.CartDTO;
import com.microservices.cart.cart.dto.CartRequestDTO;
import com.microservices.cart.cart.external.Product;
import com.microservices.cart.cart.model.Cart;
import com.microservices.cart.cart.model.CartItem;
import com.microservices.cart.cart.repository.CartItemRepository;
import com.microservices.cart.cart.repository.CartRepository;
import com.microservices.cart.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public CartDTO addToCart(CartRequestDTO cartRequestDto, HttpServletRequest httpServletRequest) {
        Long userId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID"));

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
                cart.getTotalAmount() + cartRequestDto.getQuantity() * product.getAmount()
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
                (cartItem.getQuantity() == null ? 0 : cartItem.getQuantity()) + cartRequestDto.getQuantity()
        );
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
            for(CartItem item: cartItems) {
                if(item.getProductId().equals(productId)) {
                    item.setQuantity(cartItem.getQuantity());
                }
            }
            cart.setCartItems(cartItems);
        }

        return CartDTO.builder(cart);
    }
}
