package com.microservices.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "CART-SERVICE"
)
public interface CartClient {
    @PutMapping("/api/carts/products/{productId}")
    public void updateCart(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable("productId") String productId
    );

    @DeleteMapping("/api/carts/products/{productId}")
    public void handleProductDelete(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable("productId") String productId
    );
}
