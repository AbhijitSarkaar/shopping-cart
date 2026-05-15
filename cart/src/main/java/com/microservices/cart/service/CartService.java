package com.microservices.cart.service;

import com.microservices.cart.dto.CartDTO;
import com.microservices.cart.dto.CartRequestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {
    CartDTO addToCart(CartRequestDTO cartRequestDto, HttpServletRequest httpServletRequest);

    CartDTO fetchCartDetails(HttpServletRequest httpServletRequest);
}
