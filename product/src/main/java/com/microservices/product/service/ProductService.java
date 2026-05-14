package com.microservices.product.service;

import com.microservices.product.dto.ProductDTO;
import com.microservices.product.dto.ProductRequestDTO;
import com.microservices.product.exception.response.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;

public interface ProductService {
    ProductDTO create(@Valid ProductRequestDTO productRequestDto);

    ProductDTO getProductDetails(Long productId);

    ProductDTO update(@Valid ProductRequestDTO productRequestDto, Long productId);

    CustomResponse delete(Long productId);
}


