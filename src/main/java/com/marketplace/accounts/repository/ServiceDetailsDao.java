package com.marketplace.accounts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplace.accounts.domain.ServiceDetails;
import com.marketplace.accounts.utils.CommonUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO for {@link ServiceDetails} stored as JSON.
 */
@Repository
public class ServiceDetailsDao {
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    private static final String SEQ_SQL = "SELECT nextval('car_marketplace.service_details_seq')";
    private static final String FIND_SQL = "SELECT id FROM car_marketplace.service_details WHERE service_provider_id = ?";
    private static final String INSERT_SQL = "INSERT INTO car_marketplace.service_details (id, service_provider_id, details) VALUES (?, ?, ?::jsonb)";

    public ServiceDetailsDao(JdbcTemplate jdbcTemplate, CommonUtils commonUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonUtils = commonUtils;
    }

    public String save(UUID providerId, ServiceDetails details) {
        String existingId = jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        if (existingId != null) {
            return existingId;
        }
        String json;
        try {
            json = commonUtils.toJson(details);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize service details", e);
        }
        Long seq = jdbcTemplate.queryForObject(SEQ_SQL, Long.class);
        String id = String.format("SD-%06d", seq);
        try {
            jdbcTemplate.update(INSERT_SQL, id, providerId, json);
        } catch (DataIntegrityViolationException e) {
            return jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        }
        return id;
    }
}
