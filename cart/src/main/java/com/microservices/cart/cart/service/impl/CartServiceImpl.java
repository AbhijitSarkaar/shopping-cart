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

        Long userId = Long.valueOf(httpServletRequest.getHeader("X-USER-ID")) ;
        log.info("userId: " + userId);
        Cart cart = cartRepository.findByUserId(userId);
        log.info("cart", cart);
        if(cart == null) {
            cart = new Cart();
        }

        Long productId = cartRequestDto.getProductId();

        // fetch product details
        Product product = productClient.getProductById(productId);
        log.info("product:: {}", product.getProductName());

        cart.setUserId(userId);
        cart.setTotalAmount(
                cart.getTotalAmount() + cartRequestDto.getQuantity() * product.getAmount()
        );
        cartRepository.save(cart);

        // create cart item
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartRequestDto.getQuantity());
        cartItem.setProductId(product.getProductId());
        cartItem.setCart(cart);
        cart.setCartItem(cartItem);

        cartItemRepository.save(cartItem);

        log.info("cartItem.getProductId(): {}", cartItem.getProductId());


        return CartDTO.builder(cart);
    }
}
