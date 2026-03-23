package com.auth.config;

import com.auth.model.User;
import com.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class DataInitializationConfig {

    /**
     * Initialize database with test users on startup
     */
    @Bean
    public CommandLineRunner initializeTestData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                log.info("Initializing test users...");

                // Create test user 1
                User user1 = User.builder()
                        .username("user123")
                        .email("user123@test.com")
                        .password(passwordEncoder.encode("password123"))
                        .fullName("Test User One")
                        .isActive(true)
                        .build();

                // Create test user 2
                User user2 = User.builder()
                        .username("admin")
                        .email("admin@test.com")
                        .password(passwordEncoder.encode("admin123"))
                        .fullName("Administrator")
                        .isActive(true)
                        .build();

                // Create test user 3
                User user3 = User.builder()
                        .username("demo")
                        .email("demo@test.com")
                        .password(passwordEncoder.encode("demo123"))
                        .fullName("Demo User")
                        .isActive(true)
                        .build();

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                log.info("Test users created successfully!");
                log.info("Available test credentials:");
                log.info("1. Username: user123, Password: password123");
                log.info("2. Username: admin, Password: admin123");
                log.info("3. Username: demo, Password: demo123");
            }
        };
    }
}
