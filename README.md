# Banking Application REST API

A secure backend banking system built with Spring Boot and PostgreSQL. It supports user registration and login, account management, deposits, withdrawals, money transfers, and a full passbook of transaction history. Every protected endpoint uses JWT authentication with role-based access control.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Testing with Postman](#testing-with-postman)
- [Security](#security)
- [Project Structure](#project-structure)
- [Limitations](#limitations)
- [Roadmap](#roadmap)
- [License](#license)
- [Contact](#contact)

## Features

**Authentication and authorization**
- JWT-based stateless authentication with token expiry
- Role-based access control using Spring Security
- Custom user details service
- BCrypt password hashing

**User management**
- User registration and login
- User profile management
- Admin user search and onboarding

**Account management**
- Create bank accounts and link them to users
- View account details and balances

**Transactions**
- Deposit and withdrawal
- Net banking transfers between accounts
- Balance inquiry

**Passbook**
- Full transaction history per account
- Sender and receiver details for each transfer

**Reliability**
- Bean validation on request inputs
- Global exception handling with consistent error responses
- Parameterized queries through JPA/Hibernate

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.3, Spring Data JPA, Spring Validation |
| Security | Spring Security, JWT (io.jsonwebtoken 0.12.5), BCrypt |
| Database | PostgreSQL |
| Mapping and utilities | MapStruct 1.5.5, Lombok, Apache Commons Text and Collections |
| Build | Maven (wrapper included) |
| API testing | Postman |

## Architecture

The application follows a layered design:

```
Client -> JwtFilter -> Controller -> Service -> Repository -> PostgreSQL
                          |
                  Mapper (entity <-> DTO)
                          |
              GlobalExceptionHandler (errors)
```

- **Controllers** expose the REST endpoints and accept DTOs.
- **Services** hold the business logic (balance checks, transfers, passbook entries).
- **Repositories** handle persistence through Spring Data JPA.
- **Mappers** convert between entities and DTOs using MapStruct.
- **JwtFilter** validates the token on every protected request.

## Getting Started

### Prerequisites

- JDK 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6+ (or the included Maven wrapper)
- Postman (for API testing)

### 1. Clone the repository

```bash
git clone https://github.com/Krishal-Modi/Banking-Application-Rest-API.git
cd Banking-Application-Rest-API/BankingApplication
```

### 2. Create the database

```sql
CREATE DATABASE BankingApplication;
```

### 3. Set environment variables

Do not commit real credentials. Set them in your environment instead:

```bash
# Linux / macOS
export DB_URL=jdbc:postgresql://localhost:5432/BankingApplication
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=a_long_random_secret_at_least_32_characters

# Windows (PowerShell)
$env:DB_URL="jdbc:postgresql://localhost:5432/BankingApplication"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="a_long_random_secret_at_least_32_characters"
```

### 4. Build and run

```bash
# Linux / macOS
./mvnw clean install
./mvnw spring-boot:run

# Windows
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

The API starts on `http://localhost:8086`.

## Configuration

`src/main/resources/application.properties` reads sensitive values from environment variables:

```properties
server.port=8086

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

jwt.secret=${JWT_SECRET}
```

`ddl-auto=update` is convenient for development. Use migrations (for example Flyway) before deploying anywhere real.

## API Endpoints

All endpoints except login require the header `Authorization: Bearer <jwt_token>`.

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/user/login` | Log in and receive a JWT | Public |
| POST | `/admin/signUp` | Register a new user | Admin |
| GET | `/admin/search?search=john` | Search users | Admin |
| POST | `/userAccount/addingAccount` | Create a bank account | Authenticated |
| GET | `/userAccount/passbook` | View transaction history | Authenticated |
| PUT | `/userAccount/netBanking` | Transfer money between accounts | Authenticated |
| PUT | `/transaction/deposit` | Deposit money | Authenticated |
| PUT | `/transaction/withdrawal` | Withdraw money | Authenticated |
| GET | `/transaction/currentBalance/{accountNumber}` | Check balance | Authenticated |

### Examples

**Login**
```http
POST /user/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "your_password"
}
```
Response: a JWT token.

**Register a user (admin)**
```http
POST /admin/signUp
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "your_password",
  "phoneNumber": "1234567890",
  "roles": "USER"
}
```

**Create an account**
```http
POST /userAccount/addingAccount
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "accountType": "SAVINGS",
  "balance": 1000.00
}
```

**Transfer money**
```http
PUT /userAccount/netBanking
Authorization: Bearer <jwt_token>
Content-Type: application/x-www-form-urlencoded

senderAccountNumber=1234567890&receiverAccountNumber=0987654321&amount=500
```

**Deposit**
```http
PUT /transaction/deposit
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "amount": 1000.00
}
```

**Withdraw**
```http
PUT /transaction/withdrawal
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "accountNumber": 1234567890,
  "amount": 500.00
}
```

## Testing with Postman

All endpoints were tested manually with Postman.

1. Create environment variables: `base_url` = `http://localhost:8086` and `jwt_token`.
2. Log in with `POST {{base_url}}/user/login`.
3. Save the returned token with this script in the **Tests** tab:
   ```javascript
   pm.test("Status code is 200", function () {
       pm.response.to.have.status(200);
   });
   pm.environment.set("jwt_token", pm.response.text());
   ```
4. Set the `Authorization` header to `Bearer {{jwt_token}}` on other requests.
5. Run the flow: register user, create account, deposit, withdraw, transfer, check balance, view passbook.

A ready-to-import collection is in `postman/Banking-API.postman_collection.json`.

## Security

**Authentication flow**
1. The client sends credentials to `/user/login`.
2. The server validates them and returns a signed JWT.
3. The client sends the token in the `Authorization` header.
4. `JwtFilter` validates the token on every protected request.

**Measures in place**
- BCrypt password hashing
- Stateless JWT authentication with expiry
- Role-based access control on admin endpoints
- Bean validation on request inputs
- Parameterized queries through JPA/Hibernate (protects against SQL injection)
- Global exception handling that returns consistent errors without leaking internals
- Secrets read from environment variables, not committed to the repository

## Project Structure

```
BankingApplication/
├── src/main/java/com/example/BankingApplication/
│   ├── BankingApplication.java
│   ├── config/         SpringSecurity.java
│   ├── controller/     Account, Admin, Transactional, User controllers
│   ├── entity/         Account, Passbook, User
│   ├── exceptions/     DataNotFoundException, DataValidationException, handler/GlobalExceptionHandler
│   ├── filter/         JwtFilter.java
│   ├── mapper/         Account, Passbook, User mappers (MapStruct)
│   ├── model/          DTOs and error response models
│   ├── repository/     Account, Passbook, User repositories
│   ├── service/        Account, Admin, CustomUserDetails, Transactional, User services
│   └── utils/          JwtUtil.java
├── src/main/resources/application.properties
├── src/test/
├── mvnw, mvnw.cmd
└── pom.xml
```

## Limitations

- Testing so far is manual (Postman). Automated unit and integration tests are planned.
- Loan management is not implemented. A loan DTO exists, but no endpoint uses it yet.
- No rate limiting on the login endpoint.
- Database schema is managed by Hibernate `ddl-auto`, not versioned migrations.

## Roadmap

- [ ] Unit and integration tests for the transfer logic (JUnit, Mockito, Testcontainers)
- [ ] Swagger/OpenAPI documentation
- [ ] Docker Compose setup (API and PostgreSQL)
- [ ] GitHub Actions workflow to build and test on every push
- [ ] Transaction limits and audit logging
- [ ] Idempotency keys for transfers
- [ ] Email notifications for transactions
- [ ] Two-factor authentication
- [ ] Loan management
- [ ] Account statement PDF export

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
