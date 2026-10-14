package com.shoppingxpress.products.exception;

import com.shoppingxpress.products.exception.custom.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(CustomNotFoundException notFound){
        Map<String, Object> response = new HashMap<>();
        response.put("message", notFound.getMessage());
        response.put("status", HttpStatus.NOT_FOUND);
        response.put("timestamp", Instant.now());
        response.put("cause", notFound.getCause());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
