package com.microservices.cart.cart.exception;

import com.microservices.cart.cart.exception.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse> runtimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(
                new CustomResponse(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
