package com.marketplace.accounts.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marketplace.accounts.domain.MarketplaceCompliance;
import com.marketplace.accounts.utils.CommonUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * DAO for {@link MarketplaceCompliance} stored as JSON.
 */
@Repository
public class MarketplaceComplianceDao {
    private final JdbcTemplate jdbcTemplate;
    private final CommonUtils commonUtils;

    private static final String SEQ_SQL = "SELECT nextval('car_marketplace.marketplace_compliance_seq')";
    private static final String FIND_SQL = "SELECT id FROM car_marketplace.marketplace_compliance WHERE service_provider_id = ?";
    private static final String INSERT_SQL = "INSERT INTO car_marketplace.marketplace_compliance (id, service_provider_id, details) VALUES (?, ?, ?::jsonb)";

    public MarketplaceComplianceDao(JdbcTemplate jdbcTemplate, CommonUtils commonUtils) {
        this.jdbcTemplate = jdbcTemplate;
        this.commonUtils = commonUtils;
    }

    public String save(UUID providerId, MarketplaceCompliance compliance) {
        String existingId = jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        if (existingId != null) {
            return existingId;
        }
        String json;
        try {
            json = commonUtils.toJson(compliance);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize marketplace compliance", e);
        }
        Long seq = jdbcTemplate.queryForObject(SEQ_SQL, Long.class);
        String id = String.format("MC-%06d", seq);
        try {
            jdbcTemplate.update(INSERT_SQL, id, providerId, json);
        } catch (DataIntegrityViolationException e) {
            return jdbcTemplate.query(FIND_SQL, ps -> ps.setObject(1, providerId), rs -> rs.next() ? rs.getString("id") : null);
        }
        return id;
    }
}
