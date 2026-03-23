# Postman Testing Guide - JWT Authentication

This guide demonstrates how to test the JWT Authentication system using Postman with the exact steps and screenshots required for submission.

## Prerequisites
- Postman installed (https://www.postman.com/)
- Spring Boot application running on http://localhost:8080
- Test credentials (see README.md for details)

---

## Test Credentials

```
Username: user123
Password: password123

Username: admin
Password: admin123

Username: demo
Password: demo123
```

---

## Screenshot 1: Login Request and JWT Token Reception

### Steps to Create This Screenshot:

1. **Open Postman** and create a new request
2. **Set Request Type**: SELECT `POST` method
3. **Enter URL**: `http://localhost:8080/api/auth/login`
4. **Set Headers**:
   - Key: `Content-Type`
   - Value: `application/json`

5. **Set Body** (raw JSON):
```json
{
  "username": "user123",
  "password": "password123"
}
```

6. **Click Send** button

### Expected Response (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzc0MjU3NjUzLCJleHAiOjE3NzQyNjEyNTN9.iAceNdp9hDuaRZBkxTTZzrQKt-cY4SoDOGMmdRxwI-0FqpjytmNL7TrowFVVtyG73pZ5O5rlIZu7FCvmZEFC8A",
  "username": "user123",
  "email": "user123@test.com",
  "expiresIn": 3600000,
  "message": "Login successful"
}
```

### Screenshot Details:
- **Show**: Request URL, method, headers, body with credentials
- **Show**: Complete response with JWT token
- **Title**: "Screenshot 1: Login Request with JWT Token Response"

---

## Screenshot 2: Accessing Protected Route with JWT Token

### Steps to Create This Screenshot:

1. **Create a New Request** in Postman
2. **Set Request Type**: SELECT `GET` method
3. **Enter URL**: `http://localhost:8080/api/profile`
4. **Set Headers**:
   - Key: `Authorization`
   - Value: `Bearer {paste_your_token_from_screenshot_1_here}`
   
   **Example**:
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzc0MjU3NjUzLCJleHAiOjE3NzQyNjEyNTN9.iAceNdp9hDuaRZBkxTTZzrQKt-cY4SoDOGMmdRxwI-0FqpjytmNL7TrowFVVtyG73pZ5O5rlIZu7FCvmZEFC8A
   ```

5. **Do NOT set body** (it's a GET request)
6. **Click Send** button

### Expected Response (200 OK):
```json
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

### Screenshot Details:
- **Show**: Authorization header with Bearer token
- **Show**: GET request to protected endpoint
- **Show**: Successful response with user profile data
- **Title**: "Screenshot 2: Protected Route Access with JWT Token in Authorization Header"

---

## Screenshot 3: Logout Process (Token Invalidation)

### Steps to Create This Screenshot:

1. **Create a New Request** in Postman
2. **Set Request Type**: SELECT `POST` method
3. **Enter URL**: `http://localhost:8080/api/auth/logout`
4. **Set Headers**:
   - Key: `Authorization`
   - Value: `Bearer {paste_your_token_here}`

5. **Do NOT set body** (it's a POST logout)
6. **Click Send** button

### Expected Response (200 OK):
```json
{
  "success": true,
  "message": "Logout successful. Token invalidated.",
  "timestamp": "2026-03-23T14:52:00.000+05:30"
}
```

### Screenshot Details:
- **Show**: POST request to logout endpoint
- **Show**: Authorization header with Bearer token
- **Show**: Successful logout response
- **Title**: "Screenshot 3: Logout Process and Token Invalidation"

---

## Additional Test Scenarios (Optional)

### Test: Invalid Credentials (Should Return 400/401)
```
POST /api/auth/login
{
  "username": "user123",
  "password": "wrongpassword"
}

Response: 401 Unauthorized
{
  "success": false,
  "message": "Authentication failed: Invalid username or password"
}
```

### Test: Missing Token (Should Return 401)
```
GET /api/profile
(No Authorization header)

Response: 401 Unauthorized
{
  "success": false,
  "status": 401,
  "message": "Unauthorized - Invalid or missing JWT token"
}
```

### Test: Expired Token (Should Return 401)
```
GET /api/profile
Authorization: Bearer {expired_token}

Response: 401 Unauthorized
{
  "success": false,
  "message": "JWT token is expired"
}
```

---

## Postman Collection Export

You can also create a Postman Collection with these requests for easy sharing:

1. In Postman, click **File** → **Export**
2. Select the collection and export as JSON
3. Share the collection JSON file with your submission

### Collection file should include:
- Login request
- Get Profile request
- Logout request
- Dashboard request
- Protected Data request

---

## Key Points to Remember

1. **Token Format**: JWT tokens must be included as `Bearer {token}` in the Authorization header
2. **Token Expiration**: Tokens expire after 1 hour (3600000 ms)
3. **CORS Headers**: Application allows requests from localhost
4. **Content-Type**: All requests should have `Content-Type: application/json`
5. **Security**: Tokens should be kept secure and never exposed publicly

---

## Submission Checklist

- [ ] Screenshot 1: Login request with JWT token response
- [ ] Screenshot 2: Protected route access with token in header
- [ ] Screenshot 3: Logout process/token invalidation
- [ ] README.md with implementation details
- [ ] Project source code in correct folder structure
- [ ] Application running and accessible
- [ ] Postman collection exported (optional but recommended)

---

## Troubleshooting

**Issue**: Port 8080 already in use
```bash
# Kill the process
lsof -i :8080 | grep LISTEN | awk '{print $2}' | xargs kill -9

# Or restart the application
mvn spring-boot:run
```

**Issue**: Application won't start
- Check Java version: `java -version` (should be 17+)
- Check Maven: `mvn -version`
- Clear cache: `mvn clean install`

**Issue**: Login returns 401
- Verify credentials: Check `DataInitializationConfig.java`
- Check database: Access H2 console at http://localhost:8080/h2-console
- Check logs for errors

**Issue**: Token validation fails
- Ensure token is in format: `Bearer {token}` (with space)
- Check token expiration (1 hour from login)
- Verify Authorization header is correctly set

---

## API Endpoints Reference

| Endpoint | Method | Auth | Description |
|----------|--------|------|-------------|
| `/api/auth/login` | POST | ❌ | Login with username/password, get JWT token |
| `/api/auth/register` | POST | ❌ | Register new user |
| `/api/auth/logout` | POST | ✅ | Logout and invalidate token |
| `/api/profile` | GET | ✅ | Get authenticated user's profile |
| `/api/data` | GET | ✅ | Access protected data |
| `/api/dashboard` | GET | ✅ | Access dashboard (requires auth) |
| `/h2-console` | GET | ❌ | H2 Database web console |

✅ = Requires JWT Token
❌ = No authentication required

---

Generated: March 23, 2026
JWT Authentication System - Spring Boot Implementation
