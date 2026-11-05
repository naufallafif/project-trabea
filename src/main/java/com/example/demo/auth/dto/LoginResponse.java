package com.example.demo.auth.dto;

public record LoginResponse(
        String workEmail,
        String roleName,
        String token) {
}