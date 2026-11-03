package com.example.demo.common;

public record ApiErrorResponse(int status, String error, String message) {
}
