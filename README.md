# Car Collection Manager

[![CI](https://github.com/riic10/CarManager/actions/workflows/ci.yml/badge.svg)](https://github.com/riic10/CarManager/actions/workflows/ci.yml)

A REST API for managing a car collection, built with Spring Boot and PostgreSQL.

**🔗 Live demo:** [car-manager-umber.vercel.app](https://car-manager-umber.vercel.app) — a React frontend (Vercel) backed by this API (Render) and PostgreSQL (Neon). On the free tiers, the first request after the services have been idle can take ~30-60s while they wake.

This project began as a Java Swing desktop app (a university OOP term project) and was
incrementally modernized into a containerized, database-backed REST service — keeping the
original domain model while replacing the desktop UI and flat-file JSON storage with a
proper web API and relational database.

## Tech Stack

- **Java 17**, **Spring Boot 3.3**
- **Spring Web** — REST controllers
- **Spring Data JPA / Hibernate** — persistence
- **PostgreSQL 16** — database (run via Docker)
- **Flyway** — versioned, hand-written schema migrations
- **Bean Validation** (Hibernate Validator) — request validation
- **JUnit 5, MockMvc, Testcontainers** — unit + integration tests
- **Maven** — build

## Architecture

A conventional layered design, with DTOs at the edge so the public API contract is
decoupled from the JPA entity and database schema:

```
HTTP ─▶ CarController (web)  ─▶  CarService (business logic)  ─▶  CarRepository (Spring Data JPA)  ─▶  PostgreSQL
            │                                                          
            └─ maps CarRequest / CarResponse DTOs ◀─ Car entity
```

- `app.web` — `CarController`, plus `dto/` (request/response records) and `error/`
  (`@RestControllerAdvice` for uniform error responses)
- `app.service` — `CarService`, `CarNotFoundException`
- `app.repository` — `CarRepository extends JpaRepository<Car, Long>`
- `app.model` — `Car` entity, `Category` enum

The database owns identity (`BIGINT GENERATED ALWAYS AS IDENTITY`), and the schema is
created and versioned by Flyway (`src/main/resources/db/migration`) — Hibernate runs in
`validate` mode, so it checks the entity against the schema but never alters it.

## API

Base URL: `http://localhost:8080`

| Method | Path | Body | Success | Notes |
|---|---|---|---|---|
| `GET` | `/cars` | — | `200` + list | optional filters: `?forSale=true`, `?category=SUPERCAR` (combinable) |
| `GET` | `/cars/{id}` | — | `200` | `404` if not found |
| `POST` | `/cars` | `CarRequest` | `201` + `Location` header | `400` on validation failure |
| `DELETE` | `/cars/{id}` | — | `204` | `404` if not found |

**`Category`** is one of: `RACECAR`, `SUPERCAR`, `SPORTSCAR`, `LUXURY`, `MUSCLE`,
`VINTAGE`, `ECONOMY`, `OTHER`.

### Request body (`POST /cars`)

```json
{
  "year": 2026,
  "make": "Porsche",
  "model": "911 GT3",
  "category": "SPORTSCAR",
  "forSale": false
}
```

Validation: `year` 1885–2100, `make`/`model` non-blank, `category` required.

### Response body

```json
{
  "id": 1,
  "year": 2026,
  "make": "Porsche",
  "model": "911 GT3",
  "category": "SPORTSCAR",
  "forSale": false
}
```

### Error body

All errors return a consistent shape:

```json
{
  "timestamp": "2026-06-20T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": ["make:must not be blank"]
}
```

## Running Locally

**Prerequisites:** JDK 17, Maven, and Docker (for PostgreSQL).

1. **Start PostgreSQL** (creates the `carmanager` database on port 5432):

   ```bash
   docker compose up -d
   ```

2. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

   Flyway applies the schema migrations on startup, and the API is available at
   `http://localhost:8080`.

3. **Try it:**

   ```bash
   curl -X POST http://localhost:8080/cars \
     -H "Content-Type: application/json" \
     -d '{"year":2026,"make":"Porsche","model":"911 GT3","category":"SPORTSCAR","forSale":false}'

   curl http://localhost:8080/cars
   curl "http://localhost:8080/cars?category=SPORTSCAR&forSale=false"
   ```

## Testing

```bash
mvn test
```

- **`CarControllerTest`** — a `@WebMvcTest` slice test of the web layer with the service
  mocked (no database).
- **`CarManagerIntegrationTest`** — a `@SpringBootTest` integration test that runs a full
  round-trip against a real PostgreSQL container via **Testcontainers**. It is annotated
  `@Testcontainers(disabledWithoutDocker = true)`, so it runs wherever Docker is available
  (e.g. CI) and is skipped otherwise.

## Configuration

Datasource and JPA/Flyway settings live in `src/main/resources/application.properties`.
The defaults match the `docker-compose.yml` PostgreSQL service
(`jdbc:postgresql://localhost:5432/carmanager`, user/password `carmanager`).
