package com.microservices.cart.client;

import com.microservices.cart.external.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "PRODUCT-SERVICE"
)
public interface ProductClient {
    @GetMapping("/api/products/{productId}")
    Product getProductById(@PathVariable("productId") Long productId);
}
