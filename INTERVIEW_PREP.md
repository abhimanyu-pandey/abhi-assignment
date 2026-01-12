# Interview Questions & Answers

## 🎯 Project Overview Questions

**Q: Walk me through your project architecture.**
> This is a Spring Boot 3.2.1 e-commerce backend with layered architecture:
> - **Controller Layer**: REST endpoints for auth, products, orders, and users
> - **Service Layer**: Business logic including order processing and discount calculations
> - **Repository Layer**: Spring Data JPA for database operations
> - **Security Layer**: JWT-based authentication with role-based access control
> 
> Key features include product management with soft delete, order processing with inventory validation, and a Strategy pattern for dynamic discount calculation.

**Q: Why did you choose this tech stack?**
> - **Spring Boot 3.2.1**: Latest stable version with improved performance and security
> - **Java 17**: LTS version with modern language features (records, pattern matching)
> - **Spring Security + JWT**: Industry-standard for stateless authentication in REST APIs
> - **H2/PostgreSQL**: H2 for quick development, PostgreSQL for production scalability
> - **Redis**: For caching frequently accessed product data to reduce database load
> - **Flyway**: Database version control ensures consistent schema across environments

---

## 🏗️ Design Pattern Questions

**Q: Explain the Strategy Pattern implementation in your project.**
> I used the Strategy pattern for discount calculation in the order service:
> 
> **Interface**: `DiscountStrategy` with `calculateDiscount(amount)` method
> 
> **Concrete Strategies**:
> - `NoDiscountStrategy`: Default for regular users
> - `PremiumUserDiscountStrategy`: 10% discount for premium users
> - `BulkOrderDiscountStrategy`: 5% discount for orders over $500
> 
> **Benefits**:
> - Easy to add new discount types without modifying existing code (Open/Closed Principle)
> - Each strategy is independently testable
> - Multiple discounts can be combined
> - Business rules are encapsulated in dedicated classes

**Q: What other design patterns did you use?**
> - **Repository Pattern**: Abstracts data access through Spring Data JPA interfaces
> - **DTO Pattern**: Separates API contracts from domain entities for security and flexibility
> - **Factory Pattern**: Discount calculator service creates appropriate strategies
> - **Singleton Pattern**: Spring beans are singletons by default (services, repositories)
> - **Builder Pattern**: Used in test data creation and complex object construction

---

## 🔐 Security Questions

**Q: How does JWT authentication work in your application?**
> 1. User logs in with username/password
> 2. `AuthService` validates credentials against BCrypt-hashed password
> 3. `JwtTokenProvider` generates a signed JWT token (24-hour expiration)
> 4. Client includes token in `Authorization: Bearer <token>` header
> 5. `JwtAuthenticationFilter` intercepts requests, validates token, and sets SecurityContext
> 6. Controllers use `@PreAuthorize` or check authenticated user for authorization
> 
> **Advantages**: Stateless (scalable), works across services, mobile-friendly

**Q: How do you prevent common security vulnerabilities?**
> - **SQL Injection**: JPA uses parameterized queries automatically
> - **XSS**: Spring Security escapes output by default
> - **Password Storage**: BCrypt hashing with salt (never store plain text)
> - **CSRF**: Disabled for stateless REST API (JWT-based)
> - **Authorization**: Role-based access control on sensitive endpoints
> - **Input Validation**: Bean Validation annotations (@NotNull, @Min, @Email)

**Q: What are the different user roles and their permissions?**
> - **USER**: Can view products, place orders, view own orders
> - **PREMIUM_USER**: Same as USER + gets 10% discount
> - **ADMIN**: Full access - manage products, view all users/orders, update order status

---

## 💾 Database Questions

**Q: Explain your database schema and relationships.**
> **Users** (1) ← (M) **Orders** (1) ← (M) **Order_Items** (M) → (1) **Products**
> 
> - One user can have multiple orders
> - One order contains multiple order items
> - Each order item references one product
> - Foreign keys ensure referential integrity
> - Indexes on foreign keys and frequently queried columns

**Q: Why use soft delete for products?**
> Hard delete would break referential integrity for existing orders. With soft delete:
> - Products are marked as `deleted = true` instead of being removed
> - Order history remains intact (shows what customer ordered)
> - Can restore products if needed
> - Audit trail for compliance/reporting
> - Repository queries filter out deleted products automatically

**Q: What is Flyway and why use it?**
> Flyway is a database migration tool that manages schema versions:
> - **V1__Create_Initial_Schema.sql**: Creates tables with constraints
> - **V2__Insert_Sample_Data.sql**: Loads test users and products
> 
> **Benefits**:
> - Version control for database changes
> - Repeatable deployments across environments
> - Team collaboration without conflicts
> - Automatic execution on application startup

---

## 🚀 Performance & Scalability Questions

**Q: How would you handle high traffic on product queries?**
> 1. **Caching** (already implemented): Redis caches product list/details
> 2. **Pagination**: Return data in pages to reduce payload size
> 3. **Database Indexing**: Index on frequently queried columns
> 4. **Read Replicas**: Separate read/write database instances
> 5. **CDN**: Cache product images on edge servers
> 6. **API Rate Limiting**: Prevent abuse with Bucket4j or similar

**Q: How would you handle concurrent orders for the same product?**
> Current implementation uses database transactions, but for high concurrency:
> 
> **Option 1 - Optimistic Locking**: Add `@Version` to Product entity
> ```java
> @Version
> private Long version;
> ```
> JPA checks version before update, retries if conflict
> 
> **Option 2 - Pessimistic Locking**: Lock rows during transaction
> ```java
> @Lock(LockModeType.PESSIMISTIC_WRITE)
> Product findById(Long id);
> ```
> 
> **Option 3 - Message Queue**: Queue orders, process sequentially for eventual consistency

**Q: How would you scale this application to millions of users?**
> - **Horizontal Scaling**: Deploy multiple instances behind load balancer
> - **Database Optimization**: Read replicas, connection pooling, query optimization
> - **Caching Layer**: Redis cluster for distributed caching
> - **Async Processing**: Use message queues (RabbitMQ/Kafka) for order processing
> - **Microservices**: Split into product-service, order-service, user-service
> - **API Gateway**: Rate limiting, authentication, routing
> - **Monitoring**: Prometheus + Grafana for metrics and alerts

---

## 🧪 Testing Questions

**Q: What testing strategies did you implement?**
> - **Unit Tests**: Service layer logic with Mockito (e.g., `DiscountCalculatorServiceTest`)
> - **Integration Tests**: Controller tests with MockMvc (e.g., `AuthControllerTest`)
> - **Repository Tests**: JPA query methods with test database
> - **Coverage**: 80%+ code coverage focusing on critical business logic
> 
> I prioritize testing business logic (discount calculations, order processing) over simple CRUD operations.

**Q: How do you test the Strategy pattern?**
> Each strategy is tested independently:
> ```java
> @Test
> void premiumUserDiscount_shouldApply10Percent() {
>     BigDecimal amount = new BigDecimal("100.00");
>     BigDecimal discount = strategy.calculateDiscount(amount);
>     assertThat(discount).isEqualByComparingTo("10.00");
> }
> ```
> Then test the calculator service with multiple strategies combined.

---

## 🛠️ Implementation Details

**Q: Walk me through the order creation flow.**
> 1. Client sends POST `/api/orders` with product IDs and quantities
> 2. `OrderController` validates request and extracts authenticated user
> 3. `OrderService.createOrder()` starts database transaction:
>    - Validates all products exist and aren't deleted
>    - Checks stock availability for each product
>    - Calculates subtotals and total amount
>    - Applies applicable discount strategies based on user role and total
>    - Creates Order and OrderItem entities
>    - Updates product stock quantities
>    - Saves order to database
> 4. Returns OrderResponse DTO with order details and applied discounts
> 5. On error (insufficient stock), rolls back transaction and returns 400 error

**Q: How do you handle errors in your API?**
> Global exception handler using `@ControllerAdvice`:
> - `ResourceNotFoundException` → 404 with custom message
> - `InsufficientStockException` → 400 with stock details
> - `DuplicateResourceException` → 409 conflict
> - `InvalidOperationException` → 400 bad request
> - Validation errors → 400 with field-level errors
> - All exceptions return consistent JSON structure with timestamp, message, and details

**Q: Why use DTOs instead of returning entities directly?**
> - **Security**: Prevents exposing sensitive fields (e.g., password hash)
> - **API Stability**: Entity changes don't break API contracts
> - **Performance**: Return only necessary fields, avoid lazy-loading issues
> - **Flexibility**: Can combine data from multiple entities
> - **Validation**: Separate validation rules for requests vs entities

---

## 🔄 CI/CD & DevOps Questions

**Q: How do you containerize this application?**
> **Dockerfile** uses multi-stage build:
> 1. **Build stage**: Maven builds JAR in container
> 2. **Runtime stage**: Copies JAR to slim OpenJDK 17 image
> 
> **docker-compose.yml** orchestrates:
> - PostgreSQL database (port 5432)
> - Redis cache (port 6379)
> - Spring Boot app (port 8080)
> 
> Benefits: Consistent environment, easy deployment, isolated services

**Q: How do you handle different environments (dev/prod)?**
> Spring profiles in `application.yml`:
> - **dev**: H2 in-memory, verbose logging, Swagger enabled
> - **prod**: PostgreSQL, Redis, optimized logging, security hardened
> 
> Activate with: `spring.profiles.active=prod` or `mvn spring-boot:run -Dspring-boot.run.profiles=prod`

---

## 🎯 Behavioral & Problem-Solving

**Q: What challenges did you face and how did you solve them?**
> **Challenge**: Combining multiple discounts without duplication
> **Solution**: Created `DiscountCalculatorService` that evaluates all applicable strategies and sums discounts, ensuring each type applies only once
> 
> **Challenge**: Maintaining order integrity with concurrent stock updates
> **Solution**: Wrapped order creation in `@Transactional` to ensure atomic operations - if stock update fails, entire order rolls back

**Q: What would you improve if you had more time?**
> - Add comprehensive search/filtering with Elasticsearch
> - Implement payment gateway integration (Stripe)
> - Add email notifications for order confirmations
> - Create admin dashboard with React
> - Implement API versioning (/api/v1, /api/v2)
> - Add circuit breaker pattern for external services
> - Implement event sourcing for order state changes
> - Add GraphQL API alongside REST

**Q: How do you ensure code quality?**
> - Follow SOLID principles and clean code practices
> - Meaningful variable/method names
> - Single Responsibility: Each class has one purpose
> - DRY: Extract common logic to utilities
> - Dependency Injection: Loose coupling between layers
> - Comprehensive error handling
> - Code documentation for complex logic
> - Unit tests for business logic
> - Code reviews (in team environment)

---

## 📊 Metrics & Monitoring

**Q: How do you monitor application health?**
> Spring Boot Actuator endpoints:
> - `/actuator/health`: Application health status
> - `/actuator/metrics`: JVM memory, HTTP requests, database connections
> - `/actuator/info`: Application version and build info
> 
> In production, integrate with Prometheus (metrics collection) + Grafana (visualization)

**Q: How would you debug performance issues?**
> 1. **Application Logs**: Check for slow queries or exceptions
> 2. **Actuator Metrics**: Memory usage, garbage collection, thread pools
> 3. **Database Profiling**: Identify slow queries, missing indexes
> 4. **APM Tools**: New Relic or Datadog for distributed tracing
> 5. **Load Testing**: JMeter or Gatling to identify bottlenecks
> 6. **Caching**: Verify Redis hit rates and cache effectiveness

---

## 🎤 Demo Script

**Show Swagger UI** → **Login** → **Get JWT Token** → **Create Product** (Admin) → **Place Order** → **Show Discount Applied** → **Demonstrate Stock Validation Error** → **Run Tests**

**Key Points to Emphasize**:
- Clean architecture with separation of concerns
- Strategy pattern flexibility
- Comprehensive error handling
- Production-ready features (security, caching, testing, containerization)
- Scalability considerations

---

**Good luck! Be confident, explain your design decisions, and show you understand trade-offs between different approaches.** 🚀
