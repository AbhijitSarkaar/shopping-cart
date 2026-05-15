package com.microservices.cart.util;

import com.microservices.cart.client.ProductClient;
import com.microservices.cart.dto.CartDTO;
import com.microservices.cart.dto.CartItemDTO;
import com.microservices.cart.external.Product;
import com.microservices.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DTOBuilder {

    @Autowired
    ProductClient productClient;

    public CartDTO CartDtoBuilder(Cart cart) {
        CartDTO cartDto = new CartDTO();

        cartDto.setCartId(cart.getCartId());
        cartDto.setTotalAmount(cart.getTotalAmount());

        List<CartItemDTO> cartItems1 = cart.getCartItems()
                .stream()
                .map(item -> {
                    CartItemDTO cartItemDto = new CartItemDTO();
                    cartItemDto.setCartItemId(item.getCartItemId());

                    Product product = productClient.getProductById(item.getProductId());
                    cartItemDto.setProduct(product);

                    cartItemDto.setQuantity(item.getQuantity());
                    return cartItemDto;
                })
                .toList();
        cartDto.setCartItems(cartItems1);

        return cartDto;
    }
}
