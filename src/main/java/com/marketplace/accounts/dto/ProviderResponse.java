package com.marketplace.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ProviderResponse {
    private UUID id;
    private String name;
    private List<String> services;
    private BigDecimal rating;
    private Double lat;
    private Double lon;
    private Double distance; // miles
}
