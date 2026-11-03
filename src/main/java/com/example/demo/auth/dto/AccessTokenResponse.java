package com.example.demo.auth.dto;

public record AccessTokenResponse(
        String accessToken,
        String tokenType,
        long expiresIn) {
}
