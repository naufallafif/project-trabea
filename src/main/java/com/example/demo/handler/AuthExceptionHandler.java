package com.example.demo.handler;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
        
import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<Map<String, String>> handleResponseStatusException(
                        ResponseStatusException exception) {

                return ResponseEntity
                                .status(exception.getStatusCode())
                                .body(Map.of(
                                                "message",
                                                exception.getReason()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationException(
                        MethodArgumentNotValidException exception) {

                Map<String, String> errors = new java.util.LinkedHashMap<>();

                exception.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.putIfAbsent(
                                                error.getField(),
                                                error.getDefaultMessage()));

                return ResponseEntity
                                .badRequest()
                                .body(errors);
        }
}