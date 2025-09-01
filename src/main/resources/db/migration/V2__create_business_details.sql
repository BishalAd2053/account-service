CREATE SEQUENCE IF NOT EXISTS car_marketplace.business_details_seq START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS car_marketplace.business_details (
    id VARCHAR(9) PRIMARY KEY,
    service_provider_id UUID NOT NULL UNIQUE REFERENCES car_marketplace.service_provider(id),
    details JSONB NOT NULL,
    created_timestamp TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_timestamp TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER business_details_touch_updated_at
BEFORE UPDATE ON car_marketplace.business_details
FOR EACH ROW EXECUTE FUNCTION set_updated_at_timestamp();
