# 🏦 Banking Application REST API

A comprehensive and secure Banking Application REST API built with Spring Boot 3.4.3, providing complete banking operations including user management, account handling, transactions, and net banking capabilities.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Testing with Postman](#testing-with-postman)
- [Project Structure](#project-structure)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based Authentication** - Secure token-based authentication system
- **Spring Security Integration** - Role-based access control
- **Custom User Details Service** - Personalized user authentication

### 👤 User Management
- User registration and login
- Secure password encryption
- User profile management
- Admin user search functionality

### 💳 Account Management
- Create and manage bank accounts
- Link users to accounts
- View account details
- Account balance tracking

### 💰 Transaction Operations
- **Deposit** - Add money to accounts
- **Withdrawal** - Withdraw money from accounts
- **Net Banking** - Transfer money between accounts
- **Balance Inquiry** - Check current account balance
- Real-time transaction processing

### 📖 Passbook Management
- View transaction history
- Complete passbook details
- Account-wise transaction records
- Sender and receiver details tracking

### 🛡️ Security Features
- JWT token validation
- Custom exception handling
- Data validation
- SQL injection prevention
- Secure API endpoints

### 📊 Admin Features
- Search and manage users
- User registration
- System-wide user overview

## 🛠️ Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security
- Spring Validation

**Database:**
- PostgreSQL

**Security:**
- JWT (JSON Web Tokens) - io.jsonwebtoken 0.12.5
- BCrypt Password Encoder

**Tools & Libraries:**
- Lombok - Reduce boilerplate code
- MapStruct 1.5.5 - Object mapping
- Apache Commons Text 1.10.0
- Apache Commons Collections 4.4
- Maven - Dependency management

**Testing:**
- Tested extensively with **Postman**
- All endpoints verified and working

## 📦 Prerequisites

Before running this application, make sure you have:

- **Java Development Kit (JDK) 17** or higher
- **PostgreSQL 12** or higher
- **Maven 3.6+** (or use included Maven wrapper)
- **Postman** (for API testing)
- **Git** (for version control)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Krishal-Modi/Banking-Application-Rest-API.git
cd Banking-Application-Rest-API/BankingApplication
```

### 2. Configure Database

Create a PostgreSQL database:

```sql
CREATE DATABASE BankingApplication;
```

Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/BankingApplication
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project

Using Maven wrapper (recommended):

```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

Or using Maven:

```bash
mvn clean install
```

### 4. Run the Application

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

The application will start on `http://localhost:8086`

## ⚙️ Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Server Port
server.port=8086

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/BankingApplication
spring.datasource.username=postgres
spring.datasource.password=root

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### JWT Configuration

JWT secret key and expiration can be configured in `JwtUtil.java`

## 📡 API Endpoints

### Authentication Endpoints

#### User Login
```http
POST /user/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:** JWT Token

---

### Admin Endpoints

#### Register New User
```http
POST /admin/signUp
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "secure_password",
  "phoneNumber": "1234567890",
  "roles": "USER"
}
```

#### Search Users
```http
GET /admin/search?search=john
Authorization: Bearer {jwt_token}
```

---

### Account Endpoints

#### Create Account
```http
POST /userAccount/addingAccount
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "accountType": "SAVINGS",
  "balance": 1000.00
}
```

#### View Passbook
```http
GET /userAccount/passbook
Authorization: Bearer {jwt_token}
```

#### Net Banking (Money Transfer)
```http
PUT /userAccount/netBanking
Authorization: Bearer {jwt_token}
Content-Type: application/x-www-form-urlencoded

senderAccountNumber=1234567890
receiverAccountNumber=0987654321
amount=500
```

---

### Transaction Endpoints

#### Deposit Money
```http
PUT /transaction/deposit
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "amount": 1000.00
}
```

#### Withdraw Money
```http
PUT /transaction/withdrawal
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "amount": 500.00
}
```

#### Check Balance
```http
GET /transaction/currentBalance/{accountNumber}
Authorization: Bearer {jwt_token}
```

---

## 🧪 Testing with Postman

This API has been **thoroughly tested with Postman** to ensure all endpoints work correctly.

### Getting Started with Postman

1. **Download Postman** from [postman.com](https://www.postman.com/downloads/)

2. **Import Collection** (Optional - Create your own)
   - Create a new collection called "Banking API"
   - Add all endpoints mentioned above

3. **Setup Environment Variables**
   - Create variables:
     - `base_url`: `http://localhost:8086`
     - `jwt_token`: (Will be set after login)

4. **Testing Flow**

   **Step 1: Register a User**
   ```
   POST {{base_url}}/admin/signUp
   ```
   
   **Step 2: Login**
   ```
   POST {{base_url}}/user/login
   ```
   Copy the JWT token from response
   
   **Step 3: Set Authorization**
   - Add header: `Authorization: Bearer {your_jwt_token}`
   
   **Step 4: Create Account**
   ```
   POST {{base_url}}/userAccount/addingAccount
   ```
   
   **Step 5: Test Transactions**
   - Deposit money
   - Withdraw money
   - Check balance
   - Transfer money (net banking)
   
   **Step 6: View Passbook**
   ```
   GET {{base_url}}/userAccount/passbook
   ```

### Postman Testing Tips

- **Use Environment Variables** for base URL and tokens
- **Enable Auto-Refresh Tokens** if tokens expire
- **Save Example Responses** for documentation
- **Use Tests Tab** to add assertions
- **Create Collection Runner** for automated testing

### Common Postman Test Scripts

```javascript
// Save JWT token automatically after login
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

var jsonData = pm.response.text();
pm.environment.set("jwt_token", jsonData);
```

## 📁 Project Structure

```
BankingApplication/
├── src/
│   ├── main/
│   │   ├── java/com/example/BankingApplication/
│   │   │   ├── BankingApplication.java          # Main application class
│   │   │   ├── config/
│   │   │   │   └── SpringSecurity.java          # Security configuration
│   │   │   ├── controller/                      # REST controllers
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── TransactionalController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entity/                          # JPA entities
│   │   │   │   ├── Account.java
│   │   │   │   ├── Passbook.java
│   │   │   │   └── User.java
│   │   │   ├── exceptions/                      # Custom exceptions
│   │   │   │   ├── DataNotFoundException.java
│   │   │   │   ├── DataValidationException.java
│   │   │   │   └── handler/
│   │   │   │       └── GlobalExceptionHandler.java
│   │   │   ├── filter/                          # Security filters
│   │   │   │   └── JwtFilter.java
│   │   │   ├── mapper/                          # MapStruct mappers
│   │   │   │   ├── AccountMapper.java
│   │   │   │   ├── PassbookMapper.java
│   │   │   │   └── UserMapper.java
│   │   │   ├── model/                           # DTOs
│   │   │   │   ├── AccountModel.java
│   │   │   │   ├── LoanModel.java
│   │   │   │   ├── NetBankingModel.java
│   │   │   │   ├── PassbookAccountModel.java
│   │   │   │   ├── PassbookModel.java
│   │   │   │   ├── ReceiverModel.java
│   │   │   │   ├── SenderModel.java
│   │   │   │   ├── TransactionalModel.java
│   │   │   │   ├── UserAccountModel.java
│   │   │   │   ├── UserModel.java
│   │   │   │   ├── UserPassbookModel.java
│   │   │   │   └── error/
│   │   │   │       ├── ErrorResponse.java
│   │   │   │       └── ErrorType.java
│   │   │   ├── repository/                      # JPA repositories
│   │   │   │   ├── AccountRepository.java
│   │   │   │   ├── PassbookRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/                         # Business logic
│   │   │   │   ├── AccountService.java
│   │   │   │   ├── AdminService.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── TransactionalService.java
│   │   │   │   └── UserService.java
│   │   │   └── utils/                           # Utility classes
│   │   │       └── JwtUtil.java
│   │   └── resources/
│   │       └── application.properties           # Application configuration
│   └── test/                                    # Test files
├── mvnw                                         # Maven wrapper (Unix)
├── mvnw.cmd                                     # Maven wrapper (Windows)
└── pom.xml                                      # Maven dependencies
```

## 🔒 Security

### Authentication Flow

1. User sends credentials to `/user/login`
2. Server validates credentials
3. JWT token is generated and returned
4. Client includes token in `Authorization` header for subsequent requests
5. Server validates token using `JwtFilter`

### Security Measures

- **Password Encryption**: BCrypt encoding for password storage
- **JWT Tokens**: Stateless authentication
- **Request Filtering**: JWT validation on protected endpoints
- **Custom Exception Handling**: Secure error messages
- **Input Validation**: Bean validation on all inputs
- **SQL Injection Prevention**: JPA/Hibernate parameterized queries

### Best Practices Implemented

✅ Token-based authentication  
✅ Password hashing  
✅ Role-based access control  
✅ Global exception handling  
✅ Input validation  
✅ Secure headers  
✅ Database connection pooling  

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
5. **Open a Pull Request**

### Contribution Guidelines

- Follow Java coding conventions
- Write meaningful commit messages
- Add comments for complex logic
- Update documentation if needed
- Test your changes thoroughly with Postman

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

**Krishal Modi**

- GitHub: [@Krishal-Modi](https://github.com/Krishal-Modi)
- Project Link: [https://github.com/Krishal-Modi/Banking-Application-Rest-API](https://github.com/Krishal-Modi/Banking-Application-Rest-API)

## 🙏 Acknowledgments

- Spring Boot Documentation
- PostgreSQL Community
- JWT.io for JWT implementation guidance
- Postman for excellent API testing tools
- Open source community

## 📈 Future Enhancements

- [ ] Add interest calculation for savings accounts
- [ ] Implement loan management system
- [ ] Add email notifications for transactions
- [ ] Create admin dashboard
- [ ] Implement transaction limits
- [ ] Add two-factor authentication
- [ ] Create mobile app integration
- [ ] Add support for multiple currencies
- [ ] Implement account statements PDF generation
- [ ] Add GraphQL support

---

<div align="center">
  <p>⭐️ Star this repo if you find it helpful! ⭐️</p>
  <p>Made with ❤️ by Krishal Modi</p>
</div>