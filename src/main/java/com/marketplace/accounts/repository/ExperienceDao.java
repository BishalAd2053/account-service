package com.marketplace.accounts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplace.accounts.domain.Experience;
import com.marketplace.accounts.utils.CommonUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO for {@link Experience} records stored as JSON.
 */
@Repository
public class ExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    private static final String SEQ_SQL = "SELECT nextval('car_marketplace.experience_seq')";
    private static final String FIND_SQL = "SELECT id FROM car_marketplace.experience WHERE service_provider_id = ?";
    private static final String INSERT_SQL = "INSERT INTO car_marketplace.experience (id, service_provider_id, details) VALUES (?, ?, ?::jsonb)";

    public ExperienceDao(JdbcTemplate jdbcTemplate, CommonUtils commonUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonUtils = commonUtils;
    }

    public String save(UUID providerId, Experience experience) {
        String existingId = jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        if (existingId != null) {
            return existingId;
        }
        String json;
        try {
            json = commonUtils.toJson(experience);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize experience", e);
        }
        Long seq = jdbcTemplate.queryForObject(SEQ_SQL, Long.class);
        String id = String.format("EX-%06d", seq);
        try {
            jdbcTemplate.update(INSERT_SQL, id, providerId, json);
        } catch (DataIntegrityViolationException e) {
            return jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        }
        return id;
    }
}
