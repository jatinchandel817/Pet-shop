# Online Shop

A simple e-commerce application built with Java 17, Spring Boot 3, Spring MVC, Thymeleaf, Bootstrap 5, JPA/Hibernate and MySQL.

## Features

- Product listing and search
- Product details
- Shopping cart
- Stock validation
- Checkout
- Transactional order creation
- Order history
- REST APIs
- Automatic database table creation with Hibernate
- Sample product initialization
- Docker support
- Render deployment support
- `/health` endpoint

## Requirements

- Java 17
- Maven (or Maven Wrapper)
- MySQL 8+ / MariaDB
- Docker (for deployment)

## Local database

Start MySQL through XAMPP or another MySQL/MariaDB server, then create only the database:

```sql
CREATE DATABASE Shop;
```

Do not manually create tables. Hibernate creates/updates them automatically.

Default local configuration:

```text
DB_URL=jdbc:mysql://localhost:3306/Shop
DB_USERNAME=root
DB_PASSWORD=
```

The defaults are already in `application.properties`. If your local MySQL root account has a password, set `DB_PASSWORD` as an environment variable.

## Run locally

Windows:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Open:

```text
http://localhost:8080
```

Health:

```text
http://localhost:8080/health
```

## API

```text
GET    /api/products
GET    /api/products/{id}

POST   /api/cart/add
GET    /api/cart
PUT    /api/cart/update/{id}
DELETE /api/cart/{id}

POST   /api/orders
GET    /api/orders
GET    /api/orders/{id}

GET    /health
```

## Render deployment

1. Push this project to GitHub.
2. Create a Render Web Service from the GitHub repository.
3. Select Docker as the runtime if Render asks for a runtime.
4. Add a cloud MySQL/MariaDB database.
5. Add these Render environment variables:

```text
DB_URL=jdbc:mysql://<host>:<port>/<database>
DB_USERNAME=<username>
DB_PASSWORD=<password>
```

6. Set the Render health-check path to:

```text
/health
```

7. Deploy.

Render provides the `PORT` environment variable. The application uses:

```properties
server.port=${PORT:8080}
```

Do not set production `server.port=9090`.

## Important security rule

Never commit real production database credentials to GitHub.

Production credentials belong only in Render environment variables.

## Local vs production

Local development uses XAMPP/local MySQL:

```text
jdbc:mysql://localhost:3306/Shop
```

Render cannot connect to your computer's `localhost`. Production must use a cloud-accessible MySQL/MariaDB database through `DB_URL`.

## Docker

Build:

```bash
docker build -t online-shop .
```

Run:

```bash
docker run -p 8080:8080 ^
  -e DB_URL="jdbc:mysql://host:3306/Shop" ^
  -e DB_USERNAME="username" ^
  -e DB_PASSWORD="password" ^
  online-shop
```

## Project structure

```text
src/main/java/com/onlineshop
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

The application follows:

```text
Controller -> Service -> Repository -> MySQL
```
