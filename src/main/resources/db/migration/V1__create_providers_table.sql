CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE providers (
    id UUID PRIMARY KEY,
    business_name VARCHAR(255) NOT NULL,
    services VARCHAR(255) NOT NULL,
    rating NUMERIC,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip VARCHAR(20),
    location geometry(Point,4326)
);
