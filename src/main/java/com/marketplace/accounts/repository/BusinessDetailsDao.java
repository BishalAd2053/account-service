package com.marketplace.accounts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplace.accounts.domain.BusinessDetails;
import com.marketplace.accounts.utils.CommonUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO for persisting {@link BusinessDetails} using {@link JdbcTemplate}.
 * Details are stored as JSON in the database and identified by an ID with
 * the pattern BD-XXXXXX.
 */
@Repository
public class BusinessDetailsDao {

    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    private static final String SEQ_SQL = "SELECT nextval('car_marketplace.business_details_seq')";
    private static final String FIND_SQL = "SELECT id FROM car_marketplace.business_details WHERE service_provider_id = ?";
    private static final String INSERT_SQL = "INSERT INTO car_marketplace.business_details (id, service_provider_id, details) VALUES (?, ?, ?::jsonb)";

    public BusinessDetailsDao(JdbcTemplate jdbcTemplate, CommonUtils commonUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonUtils = commonUtils;
    }

    /**
     * Persists the provided {@link BusinessDetails}. If a record already exists
     * for the given service provider, the existing ID is returned and no
     * changes are made.
     *
     * @param providerId the owning service provider ID
     * @param details    business details to persist
     * @return the ID of the persisted record
     */
    public String save(UUID providerId, BusinessDetails details) {
        String existingId = jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        if (existingId != null) {
            return existingId;
        }

        String json;
        try {
            json = commonUtils.toJson(details);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize business details", e);
        }

        Long seq = jdbcTemplate.queryForObject(SEQ_SQL, Long.class);
        String id = String.format("BD-%06d", seq);

        try {
            jdbcTemplate.update(INSERT_SQL, id, providerId, json);
        } catch (DataIntegrityViolationException ex) {
            // Handles concurrent inserts by returning existing record
            return jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        }
        return id;
    }
}
