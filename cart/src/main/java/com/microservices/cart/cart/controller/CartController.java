package com.microservices.cart.cart.controller;

import com.microservices.cart.cart.dto.CartDTO;
import com.microservices.cart.cart.dto.CartRequestDTO;
import com.microservices.cart.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
