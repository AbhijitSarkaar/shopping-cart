package com.microservices.product.controller;

import com.microservices.product.dto.ProductDTO;
import com.microservices.product.dto.ProductRequestDTO;
import com.microservices.product.exception.response.CustomResponse;
import com.microservices.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDto) {
        return new ResponseEntity<>(
            productService.create(productRequestDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProductDetails(
            @PathVariable("productId") Long productId
    ) {
        return new ResponseEntity<>(
                productService.getProductDetails(productId),
                HttpStatus.OK
        );
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> update(
            @Valid @RequestBody ProductRequestDTO productRequestDto,
            @PathVariable("productId") Long productId
            ) {
        return new ResponseEntity<>(
                productService.update(productRequestDto, productId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<CustomResponse> delete(
            @PathVariable("productId") Long productId
    ) {
        return new ResponseEntity<>(
                productService.delete(productId),
                HttpStatus.OK
        );
    }

}
