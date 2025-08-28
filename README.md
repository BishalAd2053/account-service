# Accounts Service

Spring Boot microservice that manages providers for the Car Services Marketplace.

## Features

- Register and update providers
- Fetch provider by id
- Nearby provider search using PostGIS
- OpenAPI 3.1 contract (`src/main/resources/openapi.yaml`)

## Tech Stack

- Java 17
- Spring Boot 3
- Maven build
- PostgreSQL + PostGIS
- Kafka (events, not fully implemented)

## Building & Testing

```bash
mvn test
```

## Running Locally

The application expects a PostgreSQL database. Configure credentials in
`src/main/resources/application.properties` or via environment variables.

Start the app:

```bash
mvn spring-boot:run
```

## API Endpoints

- `POST /api/accounts/providers` – register a provider
- `PUT /api/accounts/providers/{id}` – update a provider
- `GET /api/accounts/providers/{id}` – fetch provider by id
- `GET /api/accounts/providers/nearby` – search providers near a location

See `openapi.yaml` for full request/response models.
