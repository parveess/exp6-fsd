package com.auth.service;

import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.model.User;
import com.auth.repository.UserRepository;
import com.auth.utils.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                JwtTokenProvider jwtTokenProvider,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticate user and generate JWT token
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        log.info("Authenticating user: {}", loginRequest.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(user);
            Long expiresIn = jwtTokenProvider.getTokenExpirationTime(token);

            log.info("User authenticated successfully: {}", loginRequest.getUsername());

            return LoginResponse.builder()
                    .token(token)
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .expiresIn(expiresIn)
                    .message("Login successful")
                    .build();

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", loginRequest.getUsername());
            throw new RuntimeException("Invalid username or password", e);
        }
    }

    /**
     * Register a new user
     */
    public User registerUser(String username, String email, String password, String fullName) {
        log.info("Registering new user: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * Validate JWT token
     */
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    /**
     * Get username from token
     */
    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsernameFromToken(token);
    }
}
