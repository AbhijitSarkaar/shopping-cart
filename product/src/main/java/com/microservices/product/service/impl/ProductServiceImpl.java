
package com.microservices.product.service.impl;

import com.microservices.product.dto.ProductDTO;
import com.microservices.product.dto.ProductRequestDTO;
import com.microservices.product.exception.response.CustomResponse;
import com.microservices.product.model.Product;
import com.microservices.product.repository.ProductRepository;
import com.microservices.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDTO create(ProductRequestDTO productRequestDto) {
        Product product = new Product(
                productRequestDto.getProductName(),
                productRequestDto.getProductDescription(),
                productRequestDto.getAmount()
        );
        product = productRepository.save(product);
        return ProductDTO.builder(product);
    }

    @Override
    public ProductDTO getProductDetails(Long productId) {
        Product product = getProductById(productId);
        return ProductDTO.builder(product);
    }

    @Override
    public ProductDTO update(ProductRequestDTO productRequestDto, Long productId) {
        Product product = getProductById(productId);
        product.setProductDescription(productRequestDto.getProductDescription());
        product.setProductName(productRequestDto.getProductName());
        product.setAmount(productRequestDto.getAmount());

        product = productRepository.save(product);
        return ProductDTO.builder(product);
    }

    @Override
    public CustomResponse delete(Long productId) {
        getProductById(productId);
        productRepository.deleteById(productId);
        return new CustomResponse("Product with id " + productId + " deleted");
    }

    Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with product id " + productId + " not found"));
    }

}
