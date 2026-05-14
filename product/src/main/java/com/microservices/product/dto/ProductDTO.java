package com.microservices.product.dto;

import com.microservices.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private Double amount;

    public static ProductDTO builder(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getAmount()
        );
    }

}
