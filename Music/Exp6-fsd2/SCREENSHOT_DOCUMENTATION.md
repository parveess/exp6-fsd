# JWT Authentication - Screenshot Documentation

This document provides detailed descriptions of the three required screenshots for the JWT Authentication system implemented in Spring Boot.

---

## Screenshot 1: Login Request and JWT Token Reception

**File Name**: `Screenshot_1_Login_Request_JWT_Token.png`

### What This Screenshot Demonstrates:
- Successful POST request to `/api/auth/login` endpoint
- Request body containing user credentials (username and password)
- Successful 200 OK response from the server
- JWT token received in the response
- Expiration time information

### How to Create This Screenshot:

1. Open Postman
2. Create a new POST request
3. URL: `http://localhost:8080/api/auth/login`
4. Headers tab: Set `Content-Type: application/json`
5. Body tab (raw):
```json
{
  "username": "user123",
  "password": "password123"
}
```
6. Click "Send" button
7. Wait for response
8. Take screenshot showing:
   - Request method: POST
   - Request URL
   - Headers section with Content-Type
   - Body section with credentials
   - Response status: 200 OK
   - Response body with JWT token
   - Token format (JWT)
   - Expiration time

### Expected Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzc0MjU3NjUzLCJleHAiOjE3NzQyNjEyNTN9.iAceNdp9hDuaRZBkxTTZzrQKt-cY4SoDOGMmdRxwI-0FqpjytmNL7TrowFVVtyG73pZ5O5rlIZu7FCvmZEFC8A",
  "username": "user123",
  "email": "user123@test.com",
  "expiresIn": 3600000,
  "message": "Login successful"
}
```

### Key Points to Highlight:
✓ Successful authentication
✓ JWT token generation
✓ Token contains username in claims
✓ Token expiration time (1 hour)
✓ Token signature algorithm (HS512)

---

## Screenshot 2: Protected Route Access with JWT Token

**File Name**: `Screenshot_2_Protected_Route_JWT_Header.png`

### What This Screenshot Demonstrates:
- GET request to protected endpoint `/api/profile`
- JWT token included in Authorization header
- Authorization header with "Bearer" prefix
- Successful 200 OK response
- User profile data retrieved securely
- Proof that token-based authentication works

### How to Create This Screenshot:

1. Open a new Postman tab (or same one as request 1)
2. Create a new GET request
3. URL: `http://localhost:8080/api/profile`
4. Headers tab: Add new header:
   - Key: `Authorization`
   - Value: `Bearer {copy_token_from_screenshot_1}`
   
   Example full header value:
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNzc0MjU3NjUzLCJleHAiOjE3NzQyNjEyNTN9.iAceNdp9hDuaRZBkxTTZzrQKt-cY4SoDOGMmdRxwI-0FqpjytmNL7TrowFVVtyG73pZ5O5rlIZu7FCvmZEFC8A
   ```

5. Do NOT set request body (GET request)
6. Click "Send" button
7. Wait for response
8. Take screenshot showing:
   - Request method: GET
   - Request URL: `/api/profile`
   - Headers section showing Authorization header with Bearer token
   - Response status: 200 OK
   - Response body with user profile data
   - User information (id, username, email, fullName, isActive, createdAt)
   - Success status in response

### Expected Response:
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

### Key Points to Highlight:
✓ Authorization header contains Bearer token
✓ Protected route accessed successfully
✓ Authentication validation passed
✓ User data returned securely
✓ Response indicates success status
✓ Demonstrates token-based access control

---

## Screenshot 3: Logout Process and Token Invalidation

**File Name**: `Screenshot_3_Logout_Token_Invalidation.png`

### What This Screenshot Demonstrates:
- POST request to `/api/auth/logout` endpoint
- JWT token included in Authorization header
- Successful 200 OK response from logout
- Token invalidation message
- Session termination confirmation

### How to Create This Screenshot:

1. Open a new Postman tab
2. Create a new POST request
3. URL: `http://localhost:8080/api/auth/logout`
4. Headers tab: Add Authorization header:
   - Key: `Authorization`
   - Value: `Bearer {same_token_from_screenshot_1}`

5. Do NOT set request body
6. Click "Send" button
7. Wait for response
8. Take screenshot showing:
   - Request method: POST
   - Request URL: `/api/auth/logout`
   - Headers section showing Authorization header
   - Response status: 200 OK
   - Response body showing logout success message
   - Timestamp of logout
   - Message confirming token invalidation

### Expected Response:
```json
{
  "success": true,
  "message": "Logout successful. Token invalidated.",
  "timestamp": "2026-03-23T14:52:00.000+05:30"
}
```

### Key Points to Highlight:
✓ Logout endpoint accessed with valid token
✓ Token invalidation confirmed
✓ Session terminated successfully
✓ Logout timestamp recorded
✓ Success status in response

---

## Optional: Test Invalid Token Scenario

### What to Show (Optional):
- Request to protected endpoint without token
- Error 401 Unauthorized response
- Error message: "Unauthorized - Invalid or missing JWT token"

### Screenshot:
1. Try GET to `/api/profile` WITHOUT Authorization header
2. Show 401 response
3. Shows system correctly rejects requests without valid token

---

## Submission Screenshots Checklist

### Required Screenshots (Minimum 3):

- [ ] **Screenshot 1**: Login Request
  - Request: POST /api/auth/login
  - Body: username and password
  - Response: 200 OK with JWT token
  - Show: Token, expiration time, user details

- [ ] **Screenshot 2**: Protected Route Access
  - Request: GET /api/profile
  - Headers: Authorization: Bearer {token}
  - Response: 200 OK with user profile
  - Show: Successful authentication with token

- [ ] **Screenshot 3**: Logout Process
  - Request: POST /api/auth/logout
  - Headers: Authorization: Bearer {token}
  - Response: 200 OK with logout confirmation
  - Show: Token invalidation success

### Additional Files to Include:

- [ ] README.md (implementation documentation)
- [ ] Source code in correct folder structure
- [ ] POSTMAN_TESTING_GUIDE.md (this file)
- [ ] JWT_Auth_Collection.postman_collection.json (Postman collection)

---

## Tips for Professional Screenshots

1. **Clarity**: Make sure text is readable and not blurry
2. **Complete View**: Show entire request/response panes
3. **Status Codes**: Ensure status codes are visible (200 OK, 401, etc.)
4. **Headers**: Show relevant headers clearly
5. **Token Format**: Show full token values (or at least beginning and end)
6. **Annotations**: Consider adding arrows or boxes highlighting key areas
7. **File Format**: Save as PNG or JPG (PNG preferred for clarity)
8. **Resolution**: Use high resolution (at least 1920x1080)

---

## Testing the System

### Command to Start Application:
```bash
cd /home/parv/Music/Exp6-fsd2
mvn spring-boot:run
```

### Verify Application is Running:
```bash
curl http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user123","password":"password123"}'
```

### Stop Application:
```bash
# Press Ctrl+C in the terminal running mvn spring-boot:run
# Or kill the process:
pkill -f "mvn spring-boot:run"
```

---

## Test Credentials

| Username | Password | Email | Role |
|----------|----------|-------|------|
| user123 | password123 | user123@test.com | User |
| admin | admin123 | admin@test.com | Admin |
| demo | demo123 | demo@test.com | Demo User |

---

## Common Issues and Solutions

### Issue: Port 8080 Already in Use
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### Issue: Maven Not Found
```bash
# Install Maven
apt-get install maven  # Ubuntu/Debian
brew install maven     # macOS
```

### Issue: Java Version Issue
```bash
# Check Java version
java -version

# Should show Java 17 or higher
# If not, install:
apt-get install openjdk-17-jdk  # Ubuntu/Debian
```

### Issue: Token Expired
- JWT tokens expire after 1 hour (3600000 ms)
- Create a new login request to get a fresh token
- Use the new token in protected route requests

### Issue: Invalid Credentials
- Verify you're using correct username and password
- Check the README.md for test credentials
- Database is auto-populated on startup

---

## Submission Instructions

1. **Create Screenshots**: Follow the instructions above to create 3 screenshots
2. **Save Screenshots**: Store in `/screenshots/` folder with names:
   - Screenshot_1_Login_Request_JWT_Token.png
   - Screenshot_2_Protected_Route_JWT_Header.png
   - Screenshot_3_Logout_Token_Invalidation.png

3. **Create Documentation**: Include this screenshot documentation

4. **Push to GitHub**:
```bash
cd /home/parv/Music/Exp6-fsd2
git add .
git commit -m "JWT Authentication Implementation with Screenshots"
git push origin main
```

5. **Project Structure**:
```
Exp6-fsd2/
├── src/
│   ├── main/
│   │   ├── java/com/auth/
│   │   │   ├── JwtAuthenticationApplication.java
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── security/
│   │   │   ├── config/
│   │   │   ├── dto/
│   │   │   └── utils/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── README.md
├── POSTMAN_TESTING_GUIDE.md
├── JWT_Auth_Collection.postman_collection.json
├── SCREENSHOT_DOCUMENTATION.md
└── screenshots/
    ├── Screenshot_1_Login_Request_JWT_Token.png
    ├── Screenshot_2_Protected_Route_JWT_Header.png
    └── Screenshot_3_Logout_Token_Invalidation.png
```

---

## Evaluation Criteria

Your submission will be evaluated based on:

1. ✓ **Functionality**: All API endpoints work correctly
2. ✓ **JWT Implementation**: Tokens generated and validated properly
3. ✓ **Screenshots**: Clear, complete, showing all required functionality
4. ✓ **Documentation**: README explains implementation details
5. ✓ **Code Quality**: Well-organized, commented, follows best practices
6. ✓ **Security**: Passwords hashed, tokens secured, proper validation
7. ✓ **Project Structure**: Follows recommended folder structure
8. ✓ **Git History**: Proper commits and documentation

---

**Generated**: March 23, 2026
**Project**: JWT Authentication System - Spring Boot
**Status**: Ready for Postman Testing and Screenshots
