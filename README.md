# Finance Backend API

A backend system for a finance dashboard built with Spring Boot and MySQL.
Supports user management, financial records, dashboard analytics, and role-based access control.

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

## Setup Instructions

1. Clone the repository
2. Create a MySQL database:
```sql
CREATE DATABASE financedb;
```

3. Copy the example properties file and update your credentials:
```
cp application.properties.example src/main/resources/application.properties
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

5. API will be available at `http://localhost:8081`

---

## Roles & Access Control

| Role | Permissions |
|---|---|
| VIEWER | View financial records only |
| ANALYST | View records + access dashboard |
| ADMIN | Full access |

Authentication is via HTTP Basic Auth (email + password).

---

## API Endpoints

### Users
| Method | Endpoint | Access |
|---|---|---|
| POST | /api/users | Public |
| GET | /api/users | ADMIN |
| PUT | /api/users/{id}/role | ADMIN |
| PUT | /api/users/{id}/toggle-status | ADMIN |

### Financial Records
| Method | Endpoint | Access |
|---|---|---|
| POST | /api/records | ADMIN, ANALYST |
| GET | /api/records | ALL |
| GET | /api/records/{id} | ALL |
| PUT | /api/records/{id} | ADMIN |
| DELETE | /api/records/{id} | ADMIN |
| GET | /api/records/filter | ALL |

### Filter Parameters
| Parameter | Example |
|---|---|
| type | ?type=INCOME |
| category | ?category=Food |
| startDate | ?startDate=2026-04-01 |
| endDate | ?endDate=2026-04-30 |

### Dashboard
| Method | Endpoint | Access |
|---|---|---|
| GET | /api/dashboard/summary | ADMIN, ANALYST |
| GET | /api/dashboard/category-totals | ADMIN, ANALYST |
| GET | /api/dashboard/monthly-trends | ADMIN, ANALYST |
| GET | /api/dashboard/recent-activity | ADMIN, ANALYST |

---

## Assumptions

- Passwords are encrypted using BCrypt
- New users are active by default
- Record types are INCOME or EXPENSE
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