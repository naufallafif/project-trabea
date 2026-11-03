package com.example.demo.auth;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.LoginResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService) {

                this.authService = authService;
        }

        @PostMapping("/login")
        @SecurityRequirements
        public ResponseEntity<LoginResponse> login(
                        @RequestBody LoginRequest request) {

                LoginResponse response = authService.login(request);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(response);
        }
}