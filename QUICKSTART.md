# Quick Start Guide - 5 Minutes

Get the Product Order Management API running in 5 minutes!

## Step 1: Start the Application (1 minute)

```bash
cd abhi-assignment
./mvnw spring-boot:run
```

Wait for: `Started ProductOrderManagementApplication`

## Step 2: Access Swagger UI (30 seconds)

Open your browser:
```
http://localhost:8080/swagger-ui.html
```

## Step 3: Login to Get Token (1 minute)

1. Find **"Authentication"** section in Swagger
2. Click on **POST /api/auth/login**
3. Click **"Try it out"**
4. Use these credentials:
```json
{
  "username": "john_premium",
  "password": "password123"
}
```
5. Click **Execute**
6. **Copy the token** from the response

## Step 4: Authorize in Swagger (30 seconds)

1. Click the **"Authorize"** button (top right)
2. Paste the token
3. Click **"Authorize"**
4. Close the dialog

## Step 5: Test the API (2 minutes)

### View Products
- Go to **GET /api/products**
- Click "Try it out" → "Execute"
- See all available products

### Place an Order
- Go to **POST /api/orders**
- Click "Try it out"
- Use this request:
```json
{
  "items": [
    {"productId": 1, "quantity": 1},
    {"productId": 2, "quantity": 3}
  ]
}
```
- Click "Execute"
- Notice the **10% premium user discount** applied!

### Check Your Orders
- Go to **GET /api/orders/my-orders**
- Click "Try it out" → "Execute"
- See your order with discount details

## Understanding Discounts

### Test User: `john_premium` (PREMIUM_USER)
- **Order < $500**: Gets 10% premium discount
- **Order > $500**: Gets 10% premium + 5% bulk = 15% total

### Test with Bulk Order
Place order with high quantity:
```json
{
  "items": [
    {"productId": 1, "quantity": 1},
    {"productId": 8, "quantity": 2}
  ]
}
```
Product 8 (Monitor) costs $399.99, so 2 units = ~$800
- Subtotal: $1,099.97
- Premium discount (10%): $109.99
- Bulk discount (5%): $54.99
- **Total discount: $164.98**

## Test Users Available

| Username | Password | Role | Discount |
|----------|----------|------|----------|
| `admin` | `password123` | ADMIN | None |
| `john_premium` | `password123` | PREMIUM_USER | 10% |
| `jane_doe` | `password123` | USER | None |

## Admin Features

Login as `admin` to:
- Create/Update/Delete products
- View all orders
- Manage users
- Update order status

## H2 Database Console

View database directly:
```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Quick Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john_premium","password":"password123"}'
```

### Get Products
```bash
curl http://localhost:8080/api/products
```

### Place Order (replace TOKEN)
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"items":[{"productId":1,"quantity":2}]}'
```

## Next Steps

1. ✅ Review the code structure in `src/main/java/com/ecommerce/`
2. ✅ Check the **Strategy Pattern** in `service/discount/`
3. ✅ Run tests: `./mvnw test`
4. ✅ Read `INTERVIEW_GUIDE.md` for presentation tips

## Troubleshooting

**Port 8080 already in use?**
```bash
# Add to application.yml
server:
  port: 8081
```

**Redis errors?**
- Don't worry! Application works without Redis
- Or start Redis: `docker run -d -p 6379:6379 redis:7-alpine`

**Need to reset data?**
- Just restart the application (H2 is in-memory)

---

**You're ready! Start exploring the API! 🚀**
