# JWT Authentication System - Spring Boot

A comprehensive Spring Boot implementation of JWT (JSON Web Token) authentication system with user login, session management, and token validation.

## Project Overview

This project demonstrates:
- **User Authentication**: Login with username and password
- **JWT Token Generation**: Secure token creation and management
- **Token Validation**: Filter-based JWT verification on protected routes
- **Session Management**: Stateless session handling using JWT
- **Protected Endpoints**: Secure routes requiring valid JWT tokens
- **User Profile Management**: Access user information via protected endpoints

## Technologies & Dependencies

- **Java 17+**
- **Spring Boot 3.1.5**
- **Spring Security**: For authentication and authorization
- **Spring Data JPA**: For database operations
- **JWT (JJWT 0.12.3)**: For token creation and validation
- **H2 Database**: In-memory database for easy testing
- **BCrypt**: For password encryption
- **Lombok**: For reducing boilerplate code

## Project Structure

```
Exp6-fsd2/
├── src/
│   ├── main/
│   │   ├── java/com/auth/
│   │   │   ├── JwtAuthenticationApplication.java       # Main Spring Boot application
│   │   │   ├── controller/
│   │   │   │   ├── AuthenticationController.java       # Login & registration endpoints
│   │   │   │   └── ProtectedResourceController.java    # Protected endpoints
│   │   │   ├── model/
│   │   │   │   └── User.java                          # User entity
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java                # User data access
│   │   │   ├── service/
│   │   │   │   ├── AuthenticationService.java         # Auth business logic
│   │   │   │   └── CustomUserDetailsService.java      # User details loading
│   │   │   ├── security/
│   │   │   │   ├── JwtAuthenticationFilter.java       # JWT filter
│   │   │   │   └── JwtAuthenticationEntryPoint.java   # Unauthorized handler
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java                # Security configuration
│   │   │   │   └── DataInitializationConfig.java      # Test data setup
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java                  # Login request DTO
│   │   │   │   ├── LoginResponse.java                 # Login response DTO
│   │   │   │   └── ApiResponse.java                   # Generic API response
│   │   │   └── utils/
│   │   │       └── JwtTokenProvider.java              # JWT utility methods
│   │   └── resources/
│   │       └── application.properties                  # Application configuration
│   └── test/
└── pom.xml                                             # Maven dependencies
```

## API Endpoints

### Authentication Endpoints

#### 1. **Login** (POST)
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "user123",
  "password": "password123"
}

Response (200):
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "username": "user123",
  "email": "user123@test.com",
  "expiresIn": 3600000,
  "message": "Login successful"
}
```

#### 2. **Register** (POST)
```
POST /api/auth/register?username=newuser&email=new@test.com&password=pass123&fullName=New User
```

#### 3. **Logout** (POST)
```
POST /api/auth/logout
Authorization: Bearer {token}
```

### Protected Endpoints

#### 1. **Get User Profile** (GET)
```
GET /api/profile
Authorization: Bearer {token}

Response (200):
{
  "success": true,
  "message": "User profile retrieved successfully",
  "data": {
    "id": 1,
    "username": "user123",
    "email": "user123@test.com",
    "fullName": "Test User One",
    "isActive": true,
    "createdAt": 1699123456789
  }
}
```

#### 2. **Get Protected Data** (GET)
```
GET /api/data
Authorization: Bearer {token}
```

#### 3. **Get Dashboard** (GET)
```
GET /api/dashboard
Authorization: Bearer {token}
```

## Setup & Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Postman (for testing)

### Steps

1. **Navigate to project directory**
```bash
cd /home/parv/Music/Exp6-fsd2
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

4. **Access H2 Database Console** (Optional)
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:authdb
Username: sa
Password: (leave empty)
```

## Test Credentials

The application auto-populates three test users:

| Username | Password | Email | Full Name |
|----------|----------|-------|-----------|
| user123 | password123 | user123@test.com | Test User One |
| admin | admin123 | admin@test.com | Administrator |
| demo | demo123 | demo@test.com | Demo User |

## JWT Token Details

### Token Structure
```
Header.Payload.Signature
```

### Token Claims
- **sub**: Username (subject)
- **iat**: Issued at timestamp
- **exp**: Expiration timestamp

### Configuration (application.properties)
```properties
jwt.secret=your_super_secret_jwt_key_that_should_be_at_least_256_bits_long_and_very_secure_2024
jwt.expiration=3600000          # 1 hour in milliseconds
jwt.refresh-expiration=604800000 # 7 days in milliseconds
```

## Testing with Postman

### Step 1: Login Request
1. Create a POST request to `http://localhost:8080/api/auth/login`
2. Set Header: `Content-Type: application/json`
3. Body:
```json
{
  "username": "user123",
  "password": "password123"
}
```
4. Send request
5. Copy the `token` value from response

### Step 2: Access Protected Route with Token
1. Create a GET request to `http://localhost:8080/api/profile`
2. Set Header: `Authorization: Bearer {paste_token_here}`
3. Send request
4. View user profile data

### Step 3: Logout
1. Create a POST request to `http://localhost:8080/api/auth/logout`
2. Set Header: `Authorization: Bearer {token}`
3. Send request

## Security Features

1. **Password Encryption**: BCrypt hashing for password security
2. **JWT Token-based Authentication**: Stateless authentication
3. **Token Validation**: Signature and expiration verification
4. **CORS Handling**: Configured for secure cross-origin requests
5. **SQL Injection Prevention**: JPA parameterized queries
6. **CSRF Protection**: Disabled for stateless API (recommended for APIs)
7. **Unauthorized Entry Point**: Proper error handling for unauthenticated requests

## Error Handling

### Common Error Responses

**401 Unauthorized** - Invalid or missing token
```json
{
  "success": false,
  "status": 401,
  "message": "Unauthorized - Invalid or missing JWT token",
  "error": "..."
}
```

**400 Bad Request** - Invalid credentials
```json
{
  "success": false,
  "message": "Authentication failed: Invalid username or password"
}
```

## Implementation Details

### Authentication Flow
1. User sends credentials to `/api/auth/login`
2. `AuthenticationService` validates credentials using `AuthenticationManager`
3. `JwtTokenProvider` generates JWT token with expiration
4. Token returned in `LoginResponse`
5. Client includes token in Authorization header for protected requests
6. `JwtAuthenticationFilter` extracts and validates token
7. `SecurityContextHolder` sets authenticated user for the request

### Token Validation Process
1. Extract Bearer token from Authorization header
2. Parse JWT signature using secret key
3. Verify token signature
4. Check token expiration
5. Extract username from token claims
6. Load user details from database
7. Set authentication in security context

## Key Components

### JwtTokenProvider
Handles all JWT operations:
- Token generation with claims
- Token parsing and validation
- Username extraction
- Expiration time calculation

### JwtAuthenticationFilter
Per-request filter that:
- Extracts JWT from request headers
- Validates token
- Sets authentication in security context

### SecurityConfig
Configures Spring Security:
- HTTP security rules
- Filter chain setup
- Password encoder bean
- Authentication manager

### AuthenticationService
Business logic for:
- User authentication
- User registration
- Token validation
- Token claims extraction

## Running Tests

### Postman Collection Steps

**Test 1: User Login**
- Send POST to /api/auth/login with credentials
- Verify JWT token in response

**Test 2: Access Protected Route**
- Send GET to /api/profile with token in Authorization header
- Verify user data is returned

**Test 3: Token Validation**
- Use invalid token to access protected route
- Verify 401 Unauthorized response

## Troubleshooting

### Issue: Application won't start
- Check Java version: `java -version` (should be 17+)
- Check Maven: `mvn -version`
- Clear cache: `mvn clean install`

### Issue: Login returns 401
- Verify username and password from test credentials table
- Check database initialization in logs
- Ensure user table is created

### Issue: Protected routes return 401
- Verify token is included in Authorization header
- Check token format: `Bearer {token}`
- Verify token hasn't expired

### Issue: CORS errors
- Check if client is on same origin
- Verify SecurityConfig allows requests

## Production Considerations

1. **Use environment variables** for JWT secret key
2. **Implement refresh token mechanism** for token renewal
3. **Add rate limiting** to prevent brute force attacks
4. **Use HTTPS** in production
5. **Implement token blacklist** for logout functionality
6. **Monitor authentication failures** for security audit
7. **Use persistent database** (MySQL, PostgreSQL) instead of H2
8. **Add API versioning** for backward compatibility
9. **Implement request logging** for debugging
10. **Add monitoring and alerting** for authentication failures

## Future Enhancements

- [ ] Refresh token mechanism
- [ ] Token blacklist for logout
- [ ] Role-based access control (RBAC)
- [ ] OAuth2 integration
- [ ] Two-factor authentication (2FA)
- [ ] Email verification
- [ ] Password reset functionality
- [ ] API rate limiting
- [ ] Audit logging

## Contributing

This is a demo project for learning purposes. Feel free to extend and improve the implementation.

## License

This project is open source and available under the MIT License.

## Author

JWT Authentication System - Spring Boot Implementation
Date: March 2026
Repository: full-stack-2

---

## Screenshots Documentation

Three required Postman screenshots demonstrating:

1. **Login Request Screenshot**: Shows successful login with JWT token reception
2. **Protected Route Access Screenshot**: Shows accessing protected endpoint with JWT token in Authorization header
3. **Logout Screenshot**: Shows logout process and token invalidation

All screenshots are stored in `/home/parv/Music/Exp6-fsd2/screenshots/` directory.

---

For questions or issues, refer to the Spring Security and JWT documentation.
