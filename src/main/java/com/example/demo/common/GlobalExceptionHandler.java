package com.example.demo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
                        ResponseStatusException exception) {
                int status = exception.getStatusCode().value();
                HttpStatus httpStatus = HttpStatus.resolve(status);
                String error = httpStatus == null ? "Request failed" : httpStatus.getReasonPhrase();
                String message = exception.getReason() == null
                                ? error
                                : exception.getReason();

                return ResponseEntity.status(status)
                                .body(new ApiErrorResponse(status, error, message));
        }
}
