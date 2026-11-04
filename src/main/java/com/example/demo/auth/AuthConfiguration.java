package com.example.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class AuthConfiguration {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public AuthConfiguration(
                        JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // Login endpoint
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/auth/login")
                                                .permitAll()
                                                // Swagger UI and OpenAPI definition
                                                .requestMatchers(
                                                                "/swagger-ui.html",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                // Admin endpoints
                                                .requestMatchers("/api/admin/**")
                                                .hasRole("ADMINISTRATOR")
                                                // Manager endpoints
                                                .requestMatchers("/api/manager/**")
                                                .hasRole("MANAGER")
                                                // Part-time employee endpoints
                                                .requestMatchers("/api/part-time/**")
                                                .hasRole("PART_TIMER")
                                                // Administrator to see all part-time employees
                                                .requestMatchers("/api/show-all-part-time")
                                                .hasRole("ADMINISTRATOR")
                                                // Administrator to register part-time employees
                                                .requestMatchers("/api/register")
                                                .hasRole("ADMINISTRATOR")
                                                // All other endpoints require authentication
                                                .anyRequest()
                                                .authenticated())

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}