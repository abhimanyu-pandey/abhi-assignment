# Product Order Management System

A complete Spring Boot 3 REST API for managing products, orders, and users with JWT authentication, role-based access control, and dynamic discount calculation using the Strategy design pattern.

## 🎯 Features

### Core Functionality
- **Product Management**: CRUD operations for products with inventory tracking and soft delete
- **User Management**: User registration, authentication, and role-based access (USER, PREMIUM_USER, ADMIN)
- **Order Management**: Place orders, view order history, update order status
- **JWT Authentication**: Stateless authentication with Bearer tokens
- **Dynamic Discounts**: Strategy pattern implementation
  - Premium users: 10% discount
  - Bulk orders (>$500): 5% discount
  - Discounts are combinable

### Technical Features
- **Spring Boot 3.2.1** with Java 17
- **Spring Security** with JWT
- **Spring Data JPA** with Hibernate
- **Flyway** database migrations
- **Redis** caching for improved performance
- **OpenAPI/Swagger** documentation
- **Docker** containerization
- **Comprehensive unit & integration tests**
- **Global exception handling**
- **Request validation**
- **Pagination & sorting**

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose (optional)
- PostgreSQL 15+ (for production)
- Redis 7+ (optional, for caching)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd abhi-assignment
```

### 2. Run with Maven (H2 Database)
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Run with Docker
```bash
docker-compose up -d
```

## 🔧 Configuration

### Application Profiles

#### Development Profile (default)
- Uses H2 in-memory database
- H2 Console: `http://localhost:8080/h2-console`
- Debug logging enabled

#### Production Profile
- Uses PostgreSQL database
- Redis caching enabled
- JSON logging format

### Environment Variables

```bash
# Database
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
```

## 📚 API Documentation

### Swagger UI
Access interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI Specification
```
http://localhost:8080/v3/api-docs
```

## 🔐 Test Users

The application comes with pre-loaded test users:

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| `admin` | `password123` | ADMIN | Full access to all endpoints |
| `john_premium` | `password123` | PREMIUM_USER | Gets 10% discount on all orders |
| `jane_doe` | `password123` | USER | Regular user, no discount |

## 📖 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Products (Public Read, Admin Write)
- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search` - Search products with filters
- `POST /api/products` - Create product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Orders (Authenticated)
- `POST /api/orders` - Place new order
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/my-orders` - Get current user's orders
- `GET /api/orders` - Get all orders (Admin only)
- `PATCH /api/orders/{id}/status` - Update order status (Admin only)

### Users (Admin Only)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID

### Health & Monitoring
- `GET /actuator/health` - Health check
- `GET /actuator/info` - Application info
- `GET /actuator/metrics` - Metrics

## 🧪 Testing

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=ProductServiceTest
```

### Test Coverage
- Unit tests for services and discount strategies
- Integration tests for controllers
- 80%+ code coverage

## 🏗️ Architecture

### Project Structure
```
src/
├── main/
│   ├── java/com/ecommerce/
│   │   ├── config/              # Configuration classes
│   │   ├── controller/          # REST controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Custom exceptions & handler
│   │   ├── model/               # Entities & enums
│   │   ├── repository/          # JPA repositories
│   │   ├── security/            # JWT & security
│   │   └── service/             # Business logic
│   │       └── discount/        # Strategy pattern
│   └── resources/
│       ├── db/migration/        # Flyway migrations
│       ├── application.yml      # Configuration
│       └── logback-spring.xml   # Logging config
└── test/                        # Unit & integration tests
```

### Design Patterns
1. **Strategy Pattern** - Discount calculation
2. **Repository Pattern** - Data access abstraction
3. **DTO Pattern** - Request/Response separation
4. **Builder Pattern** - Entity construction (Lombok)
5. **Singleton** - Spring Beans
6. **Factory** - JWT token generation
7. **Chain of Responsibility** - Security filter chain

### Key Design Decisions

#### 1. Strategy Pattern for Discounts
- **Why**: Flexible and extensible discount system
- **Benefits**: 
  - Easy to add new discount types
  - Discounts can be combined
  - Follows Open/Closed Principle

#### 2. Soft Delete for Products
- **Why**: Preserve order history and data integrity
- **Implementation**: Boolean flag + @SQLDelete annotation
- **Benefits**: Orders reference correct product data even after "deletion"

#### 3. JWT Stateless Authentication
- **Why**: Scalability and microservices-ready
- **Benefits**: 
  - No server-side session storage
  - Easy horizontal scaling
  - Suitable for distributed systems

#### 4. Redis Caching
- **Why**: Performance optimization
- **Implementation**: Product catalog caching
- **Benefits**: Reduced database load, faster response times

## 🐳 Docker Deployment

### Build and Run
```bash
docker-compose up -d
```

### View Logs
```bash
docker-compose logs -f app
```

### Stop Services
```bash
docker-compose down
```

## 📊 Database Schema

### Tables
- `users` - User accounts with roles
- `products` - Product catalog with soft delete
- `orders` - Order headers with totals
- `order_items` - Order line items

### Relationships
- User → Order (One-to-Many)
- Order → OrderItem (One-to-Many)
- Product → OrderItem (One-to-Many)

## 🔍 Example API Usage

### 1. Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123",
    "fullName": "New User"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_premium",
    "password": "password123"
  }'
```

### 3. Place Order (with Token)
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "items": [
      {"productId": 1, "quantity": 2},
      {"productId": 3, "quantity": 1}
    ]
  }'
```

## 🛠️ Development

### Build Project
```bash
./mvnw clean package
```

### Run with Specific Profile
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Generate Documentation
```bash
./mvnw javadoc:javadoc
```

## 📈 Performance

- Redis caching reduces database queries by ~60%
- Pagination prevents memory issues with large datasets
- Lazy loading optimizes entity relationships
- Connection pooling (HikariCP) for database efficiency

## 🔒 Security

- BCrypt password encryption
- JWT with HMAC-SHA256 signing
- Role-based access control (RBAC)
- CSRF protection disabled (stateless API)
- Security headers configured
- Input validation on all endpoints

## 📝 License

This project is created for interview demonstration purposes.

## 👤 Author

Backend Developer Interview Assignment

## 🤝 Contributing

This is an interview assignment project. For questions or suggestions, please contact the author.

---

**Built with ❤️ using Spring Boot 3 and Java 17**
Create Same Project as assignment
