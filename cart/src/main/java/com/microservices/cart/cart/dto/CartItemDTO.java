package com.microservices.cart.cart.dto;

import com.microservices.cart.cart.external.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long cartItemId;
    private Integer quantity;
    private Product product;
}
