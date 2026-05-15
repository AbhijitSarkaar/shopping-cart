package com.microservices.cart.cart.dto;

import com.microservices.cart.cart.model.Cart;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private Double totalAmount;
    private List<CartItemDTO> cartItems;

}
