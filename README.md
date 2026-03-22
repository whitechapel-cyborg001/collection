# Comic Collection API

REST API for managing a personal comic book collection, built with Spring Boot.

Allows users to register, authenticate with JWT, and manage comics, authors, series and publishers. Each user can maintain their own collection marking comics as **owned** or **wanted**.

---

## Tech Stack

- **Java 17**
- **Spring Boot 4.0.3**
- **Spring Security + JWT** (jjwt 0.11.5)
- **Spring Data JPA + Hibernate**
- **MySQL**
- **MapStruct** — DTO mapping
- **SpringDoc OpenAPI 3 / Swagger UI**
- **Maven**

---

## Features

- JWT authentication (register + login)
- Full CRUD for Comics, Authors, Series and Publishers
- Pagination on all list endpoints
- Comic search filters: by title, author, serie, publisher
- Per-user collection management (OWNED / WANTED)
- Input validation with descriptive error messages
- Global exception handling with consistent error format

---

## Project Structure

```
src/main/java/net/vys/collection/
├── controllers/        # REST endpoints
├── dto/                # Request and response objects
├── entities/           # JPA entities
├── exceptions/         # Custom exceptions + GlobalExceptionHandler
├── mapper/             # MapStruct mappers
├── repositories/       # Spring Data JPA repositories
├── security/           # JWT filter, config, auth controller
├── services/           # Business logic
└── specifications/     # Dynamic query filters (Specification API)
```

---

## Setup

### Requirements

- Java 17+
- Maven 3.9+
- MySQL 8+

### 1. Clone the repository

```bash
git clone https://github.com/vyserad/collection.git
cd collection
```

### 2. Create the database

```sql
CREATE DATABASE collection;
CREATE USER 'comic_collection'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON collection.* TO 'comic_collection'@'localhost';
```

### 3. Configure local environment

Create `src/main/resources/application-local.properties` (this file is git-ignored):

```properties
DB_URL=jdbc:mysql://localhost:3306/collection
DB_USERNAME=comic_collection
DB_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_min_32_chars
```

The JWT secret must be a Base64-encoded string of at least 256 bits. You can generate one with:

```bash
openssl rand -base64 32
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

No authentication required to browse the docs.

---

## Authentication

### Register

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "vyserad",
  "password": "yourpassword"
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "vyserad",
  "password": "yourpassword"
}
```

Both endpoints return a JWT token:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Include the token in all subsequent requests:

```
Authorization: Bearer <token>
```

---

## Main Endpoints

### Comics

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/comics` | List all comics (paginated, filterable) |
| GET | `/api/comics/{id}` | Get comic by ID |
| POST | `/api/comics` | Create comic |
| PUT | `/api/comics/{id}` | Update comic |
| DELETE | `/api/comics/{id}` | Delete comic |

**Filter parameters** (all optional):

```
GET /api/comics?title=batman&authorId=1&serieId=2&publisherId=1&page=0&size=20
```

### Authors, Series, Publishers

Standard CRUD with pagination — same structure as Comics.

```
GET/POST    /api/authors
GET/PUT/DELETE /api/authors/{id}

GET/POST    /api/series
GET/PUT/DELETE /api/series/{id}

GET/POST    /api/publishers
GET/PUT/DELETE /api/publishers/{id}
```

### User Collection

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/collection` | Get your collection |
| POST | `/api/collection/{comicId}` | Add comic to collection |
| DELETE | `/api/collection/{comicId}` | Remove comic from collection |

**Add to collection:**

```http
POST /api/collection/5
Content-Type: application/json
Authorization: Bearer <token>

{
  "status": "OWNED"
}
```

Status values: `OWNED` or `WANTED`.

---

## Error Format

All errors follow a consistent structure:

```json
{
  "timestamp": "2026-03-22T10:00:00Z",
  "status": 404,
  "errorCode": "COMIC_NOT_FOUND",
  "message": "Comic con id '99' no encontrado",
  "path": "/api/comics/99"
}
```

Validation errors include a `fieldErrors` array:

```json
{
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "Los datos enviados no son válidos",
  "fieldErrors": [
    { "field": "title", "message": "Comic name cannot be empty" }
  ]
}
```

---

## Example: Create a Comic

First create an author, serie and publisher, then reference their IDs:

```http
POST /api/comics
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "The Dark Knight Returns",
  "authors": [1],
  "publisher": 1,
  "serie": 1,
  "issue": 1,
  "publicationYear": 1986,
  "notes": "Frank Miller's iconic run"
}
```

---

## License

Personal portfolio project — not licensed for commercial use.
