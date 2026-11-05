package com.example.demo.auth;

import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.LoginResponse;
import com.example.demo.jwt.JwtService;
import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import com.example.demo.role.UserRole;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public AuthService(
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService) {

                this.userRepository = userRepository;
                this.roleRepository = roleRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
        }

        public LoginResponse login(LoginRequest request) {

                // 1. Find user by work email
                User user = userRepository
                                .findByWorkEmailIgnoreCase(request.getWorkEmail())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "incorrect email or password"));

                // 2. Validate password
                if (!passwordEncoder.matches(
                                request.getPassword(),
                                user.getPassword())) {

                        throw new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "incorrect email or password");
                }

                // 3. Find requested role in database
                Role requestedRole = roleRepository
                                .findByNameIgnoreCase(request.getRoleName())
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "You are not authorized to login with this role"));

                // 4. Check whether user owns the requested role
                boolean hasRole = user.getUserRoles()
                                .stream()
                                .map(UserRole::getRole)
                                .anyMatch(role -> role.getId().equals(requestedRole.getId()));

                if (!hasRole) {
                        throw new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "You are not authorized to login with this role");
                }

                // 5. Generate JWT
                String token = jwtService.generateToken(
                                user.getWorkEmail(),
                                requestedRole.getName());

                // 6. Return login response
                return new LoginResponse(
                                user.getWorkEmail(),
                                requestedRole.getName(),
                                token);
        }
}
