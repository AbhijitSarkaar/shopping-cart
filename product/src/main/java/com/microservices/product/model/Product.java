package com.microservices.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "product_desc")
    private String productDescription;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    public Product(String productName, String productDescription, Double amount) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.amount = amount;
    }
}
