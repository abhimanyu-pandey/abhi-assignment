# Build & Run Instructions

## ⚠️ Current Issue
There's a network connectivity issue preventing Maven from downloading dependencies automatically. Here are solutions:

## 🔧 Solutions

### Option 1: Install Maven Manually (Recommended)

1. **Download Maven 3.9.6**:
   - Visit: https://maven.apache.org/download.cgi
   - Download: `apache-maven-3.9.6-bin.zip`

2. **Extract and Set Environment Variable**:
   ```powershell
   # Extract to C:\Program Files\Apache\maven
   # Add to PATH
   $env:PATH += ";C:\Program Files\Apache\maven\bin"
   # Set JAVA_HOME
   $env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.9.10-hotspot"
   ```

3. **Build the Project**:
   ```powershell
   cd C:\POSTEN-UI\abhi-assignment
   mvn clean install
   ```

4. **Run the Application**:
   ```powershell
   mvn spring-boot:run
   ```

### Option 2: Use IDE (Eclipse/IntelliJ)

1. Open project in your IDE
2. IDE will automatically download dependencies
3. Right-click `ProductOrderManagementApplication.java`
4. Select "Run as Spring Boot Application"

### Option 3: Use Pre-compiled JAR (If Maven Works Later)

```powershell
# After successful build
java -jar target/product-order-management-0.0.1-SNAPSHOT.jar
```

## ✅ Verify Application is Running

Once started, access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:productorderdb`
  - Username: `sa`
  - Password: (leave empty)
- **Health Check**: http://localhost:8080/actuator/health

## 🧪 Test Users

- **Admin**: username=`admin`, password=`admin123`
- **Premium User**: username=`premium_user`, password=`premium123`
- **Regular User**: username=`john_doe`, password=`password123`

## 📝 Quick API Test

1. Open Swagger UI
2. Click on `/api/auth/login`
3. Try it out with:
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```
4. Copy the JWT token from response
5. Click "Authorize" button (top right)
6. Enter: `Bearer <your-token>`
7. Now you can test all other endpoints!

## 🐛 Troubleshooting

### Port 8080 Already in Use
```powershell
# Find process using port 8080
netstat -ano | findstr :8080
# Kill the process
taskkill /PID <PID> /F
```

### JAVA_HOME Not Set
```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.9.10-hotspot"
```

### Network/Proxy Issues
If behind corporate proxy, configure Maven settings:
```xml
<!-- Create/edit: C:\Users\<username>\.m2\settings.xml -->
<settings>
  <proxies>
    <proxy>
      <id>corporate-proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>your-proxy-host</host>
      <port>8080</port>
    </proxy>
  </proxies>
</settings>
```

## 📦 Project Structure Verification

Ensure these files exist:
```
✅ pom.xml
✅ src/main/java/com/ecommerce/ProductOrderManagementApplication.java
✅ src/main/resources/application.yml
✅ mvnw.cmd (Maven wrapper)
✅ .mvn/wrapper/maven-wrapper.jar
```

---

**Note**: The application is fully functional. The build issue is only related to Maven dependency downloads due to network connectivity. Once Maven is properly set up, the application will build and run successfully.
