# Accounts Service

Service for managing providers and customers in the Car Services Marketplace.

## Features

- Provider registration and updates
- Geo-based nearby provider search
- Provider event publishing (Kafka)
- OpenAPI 3.1 documentation
- Role-based access control
- Observability with OpenTelemetry

## Tech Stack

- Java 21 + Spring Boot 3
- PostgreSQL + PostGIS (geo queries)
- Kafka (event publishing)
- OpenAPI 3.1
- Keycloak (auth)
- OpenTelemetry

## Dependencies

- `common-domain`: Core domain primitives
- `common-messaging`: Event schemas and serialization

## Build & Run

### Prerequisites

- Java 21
- Docker & Docker Compose
- PostgreSQL with PostGIS
- Kafka (or Redpanda)
- Keycloak

### Local Development

1. Start dependencies:
   ```bash
   docker-compose up -d
   ```

2. Build:
   ```bash
   ./mvnw clean install
   ```

3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```

### API Documentation

OpenAPI UI available at: http://localhost:8081/api/accounts/swagger-ui.html

### Main Endpoints

- `POST /api/accounts/providers` - Register/update provider
- `GET /api/accounts/providers/{id}` - Get provider by ID
- `GET /api/accounts/providers/nearby` - Find nearby providers

### Configuration

Key properties in `application.yml`:
- Database connection
- Kafka settings
- Security (JWT)
- Observability

## Testing

Run tests:
```bash
./mvnw test
```

Integration tests use Testcontainers for PostgreSQL and Kafka.

## Design

- Hexagonal Architecture
- Domain-Driven Design
- Event-Driven (outbox pattern)
- CQRS (separate read/write models)

## Event Publishing

Events published to Kafka:
- `ProviderRegistered`
- `ProviderUpdated`

Uses outbox pattern for reliability.
