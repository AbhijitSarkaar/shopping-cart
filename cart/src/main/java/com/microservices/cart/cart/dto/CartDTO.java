package com.microservices.cart.cart.dto;

import com.microservices.cart.cart.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private Double totalAmount;
    private List<CartItemDTO> cartItems;

    public static CartDTO builder(Cart cart) {

        CartDTO cartDto = new CartDTO();

        cartDto.setCartId(cart.getCartId());
        cartDto.setTotalAmount(cart.getTotalAmount());
        List<CartItemDTO> cartItems1 = cart.getCartItems()
                .stream()
                .map(item -> {
                    CartItemDTO cartItemDto = new CartItemDTO();
                    cartItemDto.setCartItemId(item.getCartItemId());
                    cartItemDto.setProductId(item.getProductId());
                    cartItemDto.setQuantity(item.getQuantity());
                    return cartItemDto;
                })
                .toList();

        cartDto.setCartItems(cartItems1);

        return cartDto;
    }

}
