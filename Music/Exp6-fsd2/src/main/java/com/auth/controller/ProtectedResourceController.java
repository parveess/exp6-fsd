package com.auth.controller;

import com.auth.dto.ApiResponse;
import com.auth.model.User;
import com.auth.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProtectedResourceController {

    private final CustomUserDetailsService userDetailsService;

    public ProtectedResourceController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Protected endpoint - returns user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile() {
        log.info("Getting user profile");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userDetailsService.findByUsername(username);

            Map<String, Object> profileData = new HashMap<>();
            profileData.put("id", user.getId());
            profileData.put("username", user.getUsername());
            profileData.put("email", user.getEmail());
            profileData.put("fullName", user.getFullName());
            profileData.put("isActive", user.isActive());
            profileData.put("createdAt", user.getCreatedAt());

            return ResponseEntity.ok(new ApiResponse(true, "User profile retrieved successfully", profileData));
        } catch (Exception e) {
            log.error("Error retrieving profile: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ApiResponse(false, "Unauthorized access"));
        }
    }

    /**
     * Protected endpoint - returns user data
     */
    @GetMapping("/data")
    public ResponseEntity<ApiResponse> getProtectedData() {
        log.info("Accessing protected data");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Map<String, Object> data = new HashMap<>();
            data.put("message", "This is protected data");
            data.put("accessedBy", username);
            data.put("timestamp", System.currentTimeMillis());
            data.put("status", "Success");

            return ResponseEntity.ok(new ApiResponse(true, "Protected resource accessed successfully", data));
        } catch (Exception e) {
            log.error("Error accessing protected data: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ApiResponse(false, "Unauthorized access"));
        }
    }

    /**
     * Protected endpoint - returns dashboard info
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboard() {
        log.info("Accessing dashboard");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Map<String, Object> dashboard = new HashMap<>();
            dashboard.put("username", username);
            dashboard.put("role", "USER");
            dashboard.put("permissions", new String[]{"READ", "WRITE"});
            dashboard.put("lastLogin", System.currentTimeMillis());
            dashboard.put("status", "Active");

            return ResponseEntity.ok(new ApiResponse(true, "Dashboard loaded successfully", dashboard));
        } catch (Exception e) {
            log.error("Error loading dashboard: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ApiResponse(false, "Unauthorized access"));
        }
    }

    /**
     * Health check for protected endpoint
     */
    @GetMapping("/protected-health")
    public ResponseEntity<ApiResponse> protectedHealth() {
        return ResponseEntity.ok(new ApiResponse(true, "Protected endpoint is accessible"));
    }
}
