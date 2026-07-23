# Renov Estimator

Connects clients with renovation contractors. Clients post project bids; contractors respond with service offers priced by materials and labour line items.

**Stack:** Spring Boot 4.1.0 / Java 21 / MySQL 8.4 / Angular 21 / Docker Compose.

## Running

```bash
docker compose up --build              # full stack; frontend :4200, API :8080
cd backend && ./mvnw spring-boot:run   # backend only (needs local MySQL)
cd frontend && ng serve                # frontend only
```

MySQL listens on host port `3326` (mapped to the container's `3306`) when run via Docker Compose.

No linting configured on either side.

## Domain model

User hierarchy uses **JPA JOINED inheritance** — one table per subclass:

```
User (app_user)
  Client (client)               — lastName, firstName, email, phone
  Contractor (contractor, abstract) — rating, yearsOfExperience, specialty
    Individual (individual)     — lastName, firstName, certification
    Company (company)           — name, contactPerson
```

Bidding flow: `Client → ProjectBid → ServiceOffer → BidMaterial → Material`
                                                  `→ BidLabor    → Labor`

`UserAddress`, `BidMaterial`, `BidLabor` are composite-key join tables (`@EmbeddedId`); `BidMaterial`/`BidLabor` carry their own `quantity` and `unitPrice` override fields.

## API shape

Standard CRUD on every entity: `GET /all`, `GET /find/{id}`, `POST /add`, `PUT /update`, `DELETE /delete/{id}`.

Non-obvious extras:
- `PUT /client/addProjectBid/{clientId}` and `PUT /contractor/addServiceOffer/{contractorId}/{bidId}` — the primary way to associate entities
- Individual and Company each have their own `/individual/add` and `/company/add` endpoints, not `/contractor/add`
- `UserController.update()` uses `@PostMapping` by mistake — the only controller that does this

CORS allows `http://localhost:4200` with credentials.

## Authentication

JWT-based (Spring Security on the backend, route guards + an HTTP interceptor on the frontend). Passwords are hashed with BCrypt.

## Gotchas

- `ddl-auto = update` in the running app — schema persists across restarts, it is not rebuilt (the `test` profile uses `create-drop` instead). There's no automatic seeding: sample data lives in `renovdb.sql` at the repo root and must be imported manually.
- DB connection overridable via `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` env vars (what Docker Compose uses to point at the `db` service).
- No input validation.
- `ContractorFormComponent` and `ContractorDetailComponent` are empty placeholders.
