package com.microservices.cart.cart.service;

import com.microservices.cart.cart.dto.CartDTO;
import com.microservices.cart.cart.dto.CartRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface CartService {
    CartDTO addToCart(CartRequestDTO cartRequestDto, HttpServletRequest httpServletRequest);
}


