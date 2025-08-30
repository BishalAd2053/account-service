-- 1. Create schema if not exists
CREATE SCHEMA IF NOT EXISTS car_marketplace;
-- Enable required extensions (safe to run once)
CREATE EXTENSION IF NOT EXISTS pgcrypto;   -- for gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS citext;     -- for case-insensitive email

-- Core table
CREATE TABLE IF NOT EXISTS car_marketplace.service_provider (
  id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),

  -- Basic profile
  full_name               VARCHAR(200)        NOT NULL,
  business_name           VARCHAR(200),
  phone                   VARCHAR(30),
  email                   CITEXT UNIQUE,
  address_line_1          VARCHAR(200),
  address_line_2          VARCHAR(200),
  city                    VARCHAR(120),
  state                   VARCHAR(120),
  postal_code             VARCHAR(20),
  country                 VARCHAR(120) DEFAULT 'USA',
  profile_picture_url     TEXT,
  bio                     TEXT,

  -- Ops details
  location_type           VARCHAR(20)         NOT NULL DEFAULT 'WORKSHOP', -- WORKSHOP | MOBILE | BOTH
  operating_hours         JSONB,              -- e.g. {"mon":"09:00-18:00",...}
  coverage_area_zipcodes  JSONB,              -- e.g. ["22312","22003"]

  -- Compliance flags
  is_identity_verified    BOOLEAN NOT NULL DEFAULT FALSE,
  is_agreed_terms         BOOLEAN NOT NULL DEFAULT FALSE,
  is_active               BOOLEAN NOT NULL DEFAULT TRUE,
  is_deleted              BOOLEAN NOT NULL DEFAULT FALSE,

  -- Search
  search_vector           tsvector,

  -- Audit
  created_at              TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at              TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Trigger to keep search_vector updated
CREATE OR REPLACE FUNCTION car_marketplace.service_provider_tsvector_trigger()
RETURNS trigger AS $$
BEGIN
  NEW.search_vector :=
    setweight(to_tsvector('simple', coalesce(NEW.full_name,'')), 'A') ||
    setweight(to_tsvector('simple', coalesce(NEW.business_name,'')), 'A') ||
    setweight(to_tsvector('simple', coalesce(NEW.bio,'')), 'B');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS service_provider_tsv_update ON car_marketplace.service_provider;
CREATE TRIGGER service_provider_tsv_update
BEFORE INSERT OR UPDATE ON service_provider
FOR EACH ROW EXECUTE FUNCTION service_provider_tsvector_trigger();

-- Trigger to bump updated_at on updates
CREATE OR REPLACE FUNCTION set_updated_at_timestamp()
RETURNS trigger AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS service_provider_touch_updated_at ON car_marketplace.service_provider;
CREATE TRIGGER service_provider_touch_updated_at
BEFORE UPDATE ON service_provider
FOR EACH ROW EXECUTE FUNCTION set_updated_at_timestamp();

-- Constraints
DO $$ BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint
    WHERE conname = 'chk_service_provider_location_type'
  ) THEN
    ALTER TABLE service_provider
      ADD CONSTRAINT chk_service_provider_location_type
      CHECK (location_type IN ('WORKSHOP','MOBILE','BOTH'));
  END IF;
END $$;

-- Indexes
CREATE INDEX IF NOT EXISTS idx_service_provider_search
  ON car_marketplace.service_provider USING GIN (search_vector);

CREATE INDEX IF NOT EXISTS idx_service_provider_phone
  ON car_marketplace.service_provider (phone);

CREATE INDEX IF NOT EXISTS idx_service_provider_active
  ON car_marketplace.service_provider (is_active) WHERE is_active = TRUE;

CREATE INDEX IF NOT EXISTS idx_service_provider_zipcodes
  ON car_marketplace.service_provider USING GIN (coverage_area_zipcodes jsonb_path_ops);
