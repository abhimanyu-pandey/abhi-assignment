# Product Order Management System

Spring Boot REST API for managing products and orders with JWT authentication.

## Tech Stack

- Java 17
- Spring Boot 3.2.1
- Spring Security (JWT)
- Spring Data JPA
- H2 Database
- Maven

## Features

- User authentication with JWT tokens
- Role-based access control (USER, PREMIUM_USER, ADMIN)
- Product management with inventory tracking
- Order processing with discount calculations
- RESTful API design

## Running the Application

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Swagger UI available at: `http://localhost:8080/swagger-ui.html`

## Database

H2 in-memory database. Console available at `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:productorderdb`
- Username: `sa`
- Password: (empty)

## Test Users

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| premium_user | premium123 | PREMIUM_USER |
| john_doe | password123 | USER |

## 👤 Author

**Abhimanyu Pandey** | [@pabhimanyu24](https://github.com/pabhimanyu24)
