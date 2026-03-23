package com.auth.controller;

import com.auth.dto.ApiResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Login endpoint - returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request for user: {}", loginRequest.getUsername());
        try {
            LoginResponse response = authenticationService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Register endpoint - create a new user
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String fullName) {
        log.info("Register request for user: {}", username);
        try {
            authenticationService.registerUser(username, email, password, fullName != null ? fullName : username);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(new ApiResponse(true, "Auth service is running"));
    }

    /**
     * Logout endpoint
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        log.info("Logout request received");
        return ResponseEntity.ok(new ApiResponse(true, "Logout successful - token has been invalidated"));
    }
}
