package com.microservices.product.service;

import com.microservices.product.dto.ProductDTO;
import com.microservices.product.dto.ProductRequestDTO;
import com.microservices.product.exception.response.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;

public interface ProductService {
    ProductDTO create(@Valid ProductRequestDTO productRequestDto);

    ProductDTO getProductDetails(Long productId);

    ProductDTO update(@Valid ProductRequestDTO productRequestDto, Long productId,  HttpServletRequest httpServletRequest);

    CustomResponse delete(Long productId, HttpServletRequest httpServletRequest);
}


