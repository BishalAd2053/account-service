package com.marketplace.accounts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplace.accounts.domain.ServiceProvider;
import com.marketplace.accounts.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO for persisting {@link ServiceProvider} records as JSON using {@link JdbcTemplate}.
 */
@Repository
public class ServiceProviderDao {
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    private static final String FIND_SQL = "SELECT details FROM car_marketplace.service_provider WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO car_marketplace.service_provider (id, details) VALUES (?, ?::jsonb)";
    private static final String UPDATE_SQL = "UPDATE car_marketplace.service_provider SET details = ?::jsonb WHERE id = ?";

    public ServiceProviderDao(JdbcTemplate jdbcTemplate, CommonUtils commonUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonUtils = commonUtils;
    }

    public ServiceProvider save(ServiceProvider provider) {
        String json;
        try {
            json = commonUtils.toJson(provider);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize service provider", e);
        }
        UUID id = provider.getId();
        if (id == null) {
            id = UUID.randomUUID();
            provider.setId(id);
            jdbcTemplate.update(INSERT_SQL, id, json);
        } else {
            int updated = jdbcTemplate.update(UPDATE_SQL, json, id);
            if (updated == 0) {
                jdbcTemplate.update(INSERT_SQL, id, json);
            }
        }
        return provider;
    }

    public ServiceProvider find(UUID id) {
        String json = jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, id), rs -> rs.next() ? rs.getString("details") : null);
        if (json == null) {
            return null;
        }
        try {
            return commonUtils.fromJson(json, ServiceProvider.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to deserialize service provider", e);
        }
    }
}
