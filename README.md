# Finance Backend API

A backend system for a finance dashboard built with Spring Boot and MySQL.
It supports user management, financial records, dashboard analytics, and role-based access control.

---

## Tech Stack

- Java 17
- Spring Boot 3.5.1
- Spring Security (Basic Auth)
- Spring Data JPA
- MySQL
- Lombok
- Maven

---

## Project Structure
```
src/main/java/com/finance/finance_backend/
├── config/         → Security configuration
├── controller/     → API endpoints
├── dto/            → Request data models
├── exception/      → Global error handling
├── model/          → Database entities
├── repository/     → Database queries
└── service/        → Business logic
```

---

## Setup Instructions

### Prerequisites
- Java 17
- MySQL
- Maven

### Steps

1. Clone the repository
2. Create a MySQL database:
```sql
CREATE DATABASE financedb;
```

3. Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

5. API will be available at:
```
http://localhost:8081
```

---

## Roles & Access Control

| Role | Permissions |
|---|---|
| VIEWER | View financial records only |
| ANALYST | View records + access dashboard |
| ADMIN | Full access (create, update, delete) |

Authentication is done via HTTP Basic Auth using email and password.

---

## API Endpoints

### User Management
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | /api/users | Public | Create new user |
| GET | /api/users | ADMIN | Get all users |
| PUT | /api/users/{id}/role | ADMIN | Update user role |
| PUT | /api/users/{id}/toggle-status | ADMIN | Activate/Deactivate user |

### Financial Records
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | /api/records | ADMIN, ANALYST | Create record |
| GET | /api/records | ALL | Get all records |
| GET | /api/records/{id} | ALL | Get single record |
| PUT | /api/records/{id} | ADMIN | Update record |
| DELETE | /api/records/{id} | ADMIN | Delete record |
| GET | /api/records/filter | ALL | Filter records |

### Filter Parameters
| Parameter | Example | Description |
|---|---|---|
| type | ?type=INCOME | Filter by type |
| category | ?category=Food | Filter by category |
| startDate | ?startDate=2026-04-01 | Filter by start date |
| endDate | ?endDate=2026-04-30 | Filter by end date |

### Dashboard
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | /api/dashboard/summary | ADMIN, ANALYST | Total income, expense, balance |
| GET | /api/dashboard/category-totals | ADMIN, ANALYST | Per category totals |
| GET | /api/dashboard/monthly-trends | ADMIN, ANALYST | Monthly totals |
| GET | /api/dashboard/recent-activity | ADMIN, ANALYST | Last 5 records |

---

## Sample Requests

### Create User
```json
POST /api/users
{
    "name": "John Admin",
    "email": "john@gmail.com",
    "password": "123456",
    "role": "ADMIN"
}
```

### Create Financial Record
```json
POST /api/records
Authorization: Basic Auth (john@gmail.com / 123456)
{
    "amount": 5000.00,
    "type": "INCOME",
    "category": "Salary",
    "date": "2026-04-01",
    "notes": "Monthly salary",
    "userId": 1
}
```

### Dashboard Summary Response
```json
{
    "totalIncome": 8000.0,
    "totalExpense": 3500.0,
    "netBalance": 4500.0,
    "totalRecords": 4
}
```

---

## Assumptions

- Passwords are encrypted using BCrypt
- New users are active by default
- Financial record types are INCOME or EXPENSE
- Basic Auth is used for simplicity
- All dates follow YYYY-MM-DD format

---

## Error Handling

All errors return a consistent format:
```json
{
    "timestamp": "2026-04-03T12:00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Record not found"
}
```

Validation errors return field-specific messages:
```json
{
    "timestamp": "2026-04-03T12:00:00",
    "status": 400,
    "error": "Validation Failed",
    "messages": {
        "email": "Email should be valid",
        "password": "Password is required"
    }
}
```