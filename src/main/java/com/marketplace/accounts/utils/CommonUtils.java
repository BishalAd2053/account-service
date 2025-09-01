package com.marketplace.accounts.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Utility component containing helpers used across the service.
 */
@Component
public class CommonUtils {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts any POJO into its JSON representation.
     *
     * @param value object to convert
     * @return JSON string representation
     * @throws JsonProcessingException if serialization fails
     */
    public String toJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    /**
     * Converts JSON into the specified type.
     *
     * @param json JSON string
     * @param type class of T
     * @return deserialized object
     * @throws JsonProcessingException if deserialization fails
     */
    public <T> T fromJson(String json, Class<T> type) throws JsonProcessingException {
        return mapper.readValue(json, type);
    }
}
