# Product Order Management System

A Spring Boot 3.2.1 e-commerce backend system with JWT authentication, order management, and dynamic discount strategies.

## 🚀 Tech Stack

- **Java 17** | **Spring Boot 3.2.1** | **Maven**
- **Spring Security** (JWT) | **Spring Data JPA** | **Hibernate**
- **H2** (dev) / **PostgreSQL** (prod) | **Flyway**
- **Redis** (caching) | **Docker** | **JUnit 5**

## 📋 Key Features

- JWT authentication with role-based access (USER, PREMIUM_USER, ADMIN)
- Product catalog with soft delete and stock management
- Order processing with inventory validation
- **Strategy Pattern** for discount calculation (Premium: 10%, Bulk: 5% >$500)
- RESTful API with OpenAPI documentation

## 🛠️ Quick Start

### Run Locally (H2)
```bash
mvn clean install
mvn spring-boot:run
```

### Run with Docker (PostgreSQL + Redis)
```bash
docker-compose up -d
```

**Access**:
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC: `jdbc:h2:mem:productorderdb`, User: `sa`)

## 📡 API Endpoints

**Auth**: `POST /api/auth/register`, `POST /api/auth/login`

**Products**: `GET /api/products`, `POST /api/products` (Admin), `PUT /api/products/{id}` (Admin)

**Orders**: `POST /api/orders`, `GET /api/orders`, `GET /api/orders/{id}`

**Users**: `GET /api/users` (Admin), `PUT /api/users/{id}/role` (Admin)

## 🧪 Sample Users

- **Admin**: `admin` / `admin123`
- **Premium**: `premium_user` / `premium123`
- **User**: `john_doe` / `password123`

## 🧪 Testing

```bash
mvn test
```

Import `Product-Order-Management-API.postman_collection.json` for API testing.

## 📁 Project Structure

```
src/main/java/com/ecommerce/
├── config/          # Security, OpenAPI, Cache
├── controller/      # REST endpoints
├── dto/             # Request/Response objects
├── exception/       # Custom exceptions + handler
├── model/           # Entities & enums
├── repository/      # JPA repositories
├── security/        # JWT provider & filter
└── service/         # Business logic + discount strategies
```

## 🔧 Configuration

**Profiles**: `dev` (H2) | `prod` (PostgreSQL + Redis)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 👤 Author

**Abhimanyu Pandey** | [@pabhimanyu24](https://github.com/pabhimanyu24)
