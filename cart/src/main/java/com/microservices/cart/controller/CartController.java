package com.microservices.cart.controller;

import com.microservices.cart.dto.CartDTO;
import com.microservices.cart.dto.CartRequestDTO;
import com.microservices.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/carts/add")
    public ResponseEntity<CartDTO> addToCart(
            @Valid @RequestBody CartRequestDTO cartRequestDto,
            HttpServletRequest httpServletRequest
            ) {
        return new ResponseEntity<>(
                cartService.addToCart(cartRequestDto, httpServletRequest),
                HttpStatus.OK
        );
    }

    @GetMapping("/carts/details")
    public ResponseEntity<CartDTO> fetchCartDetails(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                cartService.fetchCartDetails(httpServletRequest),
                HttpStatus.OK
        );
    }

    @PutMapping("/carts/products/{productId}")
    public void handleProductUpdate(
            HttpServletRequest httpServletRequest,
            @PathVariable("productId") Long productId
    ) {
        cartService.handleProductUpdate(httpServletRequest, productId);
    }

    @DeleteMapping("/carts/products/{productId}")
    public void handleProductDelete(
            HttpServletRequest httpServletRequest,
            @PathVariable("productId") Long productId
    ) {
        log.info(":::handleProductDelete:::");
        cartService.handleProductDelete(httpServletRequest, productId);
    }

}
