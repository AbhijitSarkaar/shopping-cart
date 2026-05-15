package com.microservices.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private Double totalAmount;
    private List<CartItemDTO> cartItems;

}
