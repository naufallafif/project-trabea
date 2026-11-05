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

import com.example.demo.jwt.JwtAuthenticationFilter;

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
                                                // Manager endpoints
                                                .requestMatchers("/api/manager/**")
                                                .hasRole("MANAGER")

                                                // Administrator to see all part time employee
                                                .requestMatchers("/api/show-all-part-time")
                                                .hasRole("ADMINISTRATOR")

                                                // Administrator to see part-time employee by id
                                                .requestMatchers(HttpMethod.GET, "/api/show-all-part-time/{id}")
                                                .hasRole("ADMINISTRATOR")

                                                // Part-time employee endpoints
                                                .requestMatchers("/api/part-time/**")
                                                .hasRole("PART_TIMER")

                                                // Administrator to register part time employee
                                                .requestMatchers("/api/register-part-time")
                                                .hasRole("ADMINISTRATOR")

                                                // Administrator to update Part Time Employee
                                                .requestMatchers("/api/update-part-time/{id}")
                                                .hasRole("ADMINISTRATOR")

                                                // Manager and Part Time Employee to see all work schedules
                                                .requestMatchers("/api/work-schedules/show-all-schedules")
                                                .hasAnyRole("MANAGER", "PARTTIMER")

                                                // Manager to view schedules awaiting approval or rejected
                                                .requestMatchers(HttpMethod.GET, "/api/work-schedules/pending-approval")
                                                .hasRole("MANAGER")

                                                // Manager to view details of an approved schedule
                                                .requestMatchers(HttpMethod.GET, "/api/work-schedules/*")
                                                .hasRole("MANAGER")

                                                // Manager to approve a rejected schedule
                                                .requestMatchers(HttpMethod.PATCH, "/api/work-schedules/*/approve")
                                                .hasRole("MANAGER")

                                                // Part time to request Shift
                                                .requestMatchers("/api/work-schedules/request-shift")
                                                .hasRole("PARTTIMER")
                                                // All other endpoints require authentication
                                                .anyRequest()
                                                .authenticated())

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}