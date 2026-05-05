# ProjectRoom

A full-stack project management web application built in **5 days** as a functional demo. Designed to showcase end-to-end development capabilities — from database design to a working UI — under a tight deadline.

---

## What This Is

ProjectRoom lets teams create and manage projects, assign tasks on a Kanban board, track budgets across quarters, and control member access through a roles and permissions system.

It was built in under a week as a sprint exercise. The goal was to deliver a working, coherent product — not a polished production system — and it does that.

---

## What It Does

- **Project dashboard** with real-time summary of active projects, pending tasks, and overall budget usage
- **Kanban board** per project — tasks move across customizable status columns, with labels, priorities, and member assignment
- **Budget tracking** — distribute a project's budget across quarters and log actual spending; the stats view renders planned vs. actual per period
- **Team & access control** — add members to projects, assign them roles, and attach permissions per role
- **Labels & priorities** — color-coded, reusable across projects
- **Archive system** — archive and restore projects without data loss
- **Session-based authentication** — register, log in, stay logged in across requests

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot 3.5.7 |
| Templating | Thymeleaf |
| Persistence | Spring Data JPA |
| Security | Spring Security |
| Database | MariaDB |
| Build Tool | Maven |

---

## Architecture

The application follows a standard MVC structure with a clear separation between layers:

```
controller/   → HTTP request handling, session validation, model population
service/      → Business logic (user session resolution, auth)
repository/   → Spring Data interfaces, one per entity
model/        → JPA entities with relationships
templates/    → Thymeleaf views, one per feature area
```

The database schema was designed from scratch and normalised to 3NF, with explicit junction tables for N:M relationships (roles↔permissions, members↔labels, projects↔roles).

---

## Project Structure

```
ProjectRoom/
├── src/main/java/basfer/
│   ├── config/          # Spring Security configuration
│   ├── controller/      # One controller per feature domain
│   ├── model/           # JPA entities
│   ├── repository/      # Data access layer
│   └── service/         # UsuarioService (session management)
├── src/main/resources/
│   ├── templates/       # Thymeleaf HTML views
│   ├── static/          # JS, images
│   ├── crearDB.sql      # Full schema — run this first
│   ├── datos.sql        # Sample seed data
│   └── application.properties
└── pom.xml
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MariaDB running on `localhost:3306`

### 1. Set Up the Database

```sql
SOURCE src/main/resources/crearDB.sql;  -- creates DB, user, and schema
SOURCE src/main/resources/datos.sql;    -- loads sample data (optional)
```

### 2. Configure the Connection

Default settings in `application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/ProjectRoom
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Adjust if your environment differs.

### 3. Run

```bash
mvn spring-boot:run
```

App starts at **http://localhost:8080**. Use the sample accounts from `datos.sql` to log in.

---

## Honest Trade-offs

This was built in 5 days. Some decisions were deliberate shortcuts to ship a working demo on time:

- **CSRF disabled** — Spring Security is in place but CSRF protection was turned off to keep form handling simple during the sprint. Re-enabling it is straightforward.
- **Credentials in `application.properties`** — fine for a local demo; environment variable injection would be the next step for any real deployment.
- **No automated tests** — the test suite is empty. Given the timeline, manual testing covered the main flows.
- **No pagination** — project and task lists are loaded in full. This works at demo scale; pagination would be added before any production use.

These aren't unknowns — they're the kind of call you make when the goal is a working demo in under a week.

---

## What I'd Do Next

If this moved beyond demo stage:

- Re-enable CSRF and enforce proper role-based authorization at the Spring Security level
- Add a REST API layer for frontend/backend decoupling
- Write unit and integration tests for the service and controller layers
- Add input validation and centralized error handling
- Dockerize for reproducible deployments
- Introduce pagination and lazy loading for larger datasets

---

## Context

Built in 5 days as a sprint exercise. Demonstrates the ability to design a relational schema, wire up a full Spring Boot application, and deliver a working, navigable product under deadline pressure.
