
package com.microservices.product.service.impl;

import com.microservices.product.client.CartClient;
import com.microservices.product.dto.ProductDTO;
import com.microservices.product.dto.ProductRequestDTO;
import com.microservices.product.exception.response.CustomResponse;
import com.microservices.product.model.Product;
import com.microservices.product.repository.ProductRepository;
import com.microservices.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartClient cartClient;

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
    public ProductDTO update(ProductRequestDTO productRequestDto, Long productId, HttpServletRequest httpServletRequest) {
        Product product = getProductById(productId);
        product.setProductDescription(productRequestDto.getProductDescription());
        product.setProductName(productRequestDto.getProductName());
        product.setAmount(productRequestDto.getAmount());

        product = productRepository.save(product);

        System.out.println("productId: " + productId);
        System.out.println("product.getProductId(): " + product.getProductId());

        cartClient.updateCart(
                httpServletRequest.getHeader("X-USER-ID"),
                productId.toString()
        );

        return ProductDTO.builder(product);
    }

    @Override
    public CustomResponse delete(Long productId, HttpServletRequest httpServletRequest) {
        getProductById(productId);
        productRepository.deleteById(productId);
        cartClient.handleProductDelete(
                httpServletRequest.getHeader("X-USER-ID"),
                productId.toString()
        );
        return new CustomResponse("Product with id " + productId + " deleted");
    }

    Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with product id " + productId + " not found"));
    }

}
