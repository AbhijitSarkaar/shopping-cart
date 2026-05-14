package com.microservices.cart.cart.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long cartItemId;
    private Integer quantity;
    private Long productId;
}
