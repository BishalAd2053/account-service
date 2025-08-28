package com.marketplace.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProviderRequest {
    @NotBlank
    private String name;

    @NotEmpty
    private List<String> services;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;
}
