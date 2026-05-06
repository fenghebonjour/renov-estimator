# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

EstimateurReno is a Java Spring Boot REST API (v2.0) that connects clients with contractors for real estate renovation projects. An Angular frontend at `http://localhost:4200` consumes this API.

## Commands

All commands run from `backend/`:

```bash
# Run the application
./mvnw spring-boot:run

# Build
./mvnw clean package

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=RenovApplicationTests
```

No linting tool is configured.

## Architecture

### Layered structure
`model/` → `repo/` → `service/` → `controlleur/` — standard Spring Boot layers. Custom exceptions in `exception/`.

### Domain model

**User hierarchy** (JPA `JOINED` inheritance):
- `Utilisateur` (base): username, password, registration date, user type
  - `Client`: has `AppelDOffre` (renovation bids/calls-for-proposals)
  - `Contracteur` (abstract): rating, experience, activity
    - `Individus` (individual contractor)
    - `Entreprise` (enterprise contractor)
    - Both have `OffreService` (service offers)

**Bidding flow:**
1. A `Client` creates an `AppelDOffre` (call for proposals) with a category, deadline, and completion date
2. A `Contracteur` responds with an `OffreService` linked to that appeal
3. Each `OffreService` is composed of `OdsMateriaux` (materials line items) and `OdsMainOeuvre` (labour line items)
4. `Materiaux` and `MainOeuvre` are catalog entities referenced via composite-key join tables

**Address management:** `AdresseUtilisateur` is a join table between `Utilisateur` and `Adresse` with a relationship-type field and composite key `AdresseUtilisateurId`.

### Key conventions

- **French naming throughout**: all classes, fields, and endpoints use French (e.g., `Contracteur`, `AppelDOffre`, `OffreService`, `controlleur/`)
- **Composite keys via `@EmbeddedId`** for many-to-many join tables (`AdresseUtilisateurId`, `OdsMateriauxId`, `OdsMainOeuvreId`)
- **Bidirectional JSON**: `@JsonManagedReference` / `@JsonBackReference` on all bidirectional relationships to prevent infinite serialization loops
- **Lazy loading**: `OffreService` relationships use `FetchType.LAZY`
- **Cascade**: `CascadeType.ALL` on contractor→offers; `CascadeType.PERSIST, REMOVE` on user→addresses

### Database

MySQL on `localhost:3306/renovdb` (user: `root`, password: `root`). `ddl-auto = create-drop` — **the schema is dropped and recreated on every startup**. `RenovApplication.java` contains a `CommandLineRunner` that seeds sample data (clients, contractors, offers) at startup.

### REST API pattern

Controllers follow this naming pattern:
```
GET    /{entity}/all
GET    /{entity}/find/{id}
POST   /{entity}/add
PUT    /{entity}/update
DELETE /{entity}/delete/{id}
```

CORS is configured for `http://localhost:4200` with credentials allowed.
