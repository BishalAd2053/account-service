package com.marketplace.accounts.controller;

import com.marketplace.accounts.domain.Provider;
import com.marketplace.accounts.dto.ProviderRequest;
import com.marketplace.accounts.dto.ProviderResponse;
import com.marketplace.accounts.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts/providers")
public class ProviderController {

    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderResponse register(@Validated @RequestBody ProviderRequest request) {
        Provider provider = service.register(
                request.getName(),
                request.getServices(),
                request.getLat(),
                request.getLon());
        return toResponse(provider, null);
    }

    @PutMapping("/{id}")
    public ProviderResponse update(@PathVariable UUID id, @Validated @RequestBody ProviderRequest request) {
        Provider provider = service.update(id, request.getName(), request.getServices(), request.getLat(), request.getLon());
        return toResponse(provider, null);
    }

    @GetMapping("/{id}")
    public ProviderResponse byId(@PathVariable UUID id) {
        Provider provider = service.get(id);
        if (provider == null) {
            throw new ProviderNotFoundException(id);
        }
        return toResponse(provider, null);
    }

    @GetMapping("/nearby")
    public List<ProviderResponse> nearby(@RequestParam double lat,
                                         @RequestParam double lon,
                                         @RequestParam(defaultValue = "10") double radiusMi,
                                         @RequestParam(defaultValue = "50") int limit) {
        List<Provider> providers = service.nearby(lat, lon, radiusMi, limit);
        return providers.stream()
                .map(p -> toResponse(p, null))
                .collect(Collectors.toList());
    }

    private ProviderResponse toResponse(Provider p, Double distanceMi) {
        double lat = p.getLocation() != null ? p.getLocation().getY() : 0;
        double lon = p.getLocation() != null ? p.getLocation().getX() : 0;
        List<String> services = p.getServices() != null ? Arrays.asList(p.getServices().split(",")) : List.of();
        ProviderResponse providerResponse = new ProviderResponse();
        providerResponse.setId(p.getId());
        providerResponse.setName(p.getBusinessName());
        providerResponse.setServices(services);
        providerResponse.setRating(p.getRating());
        providerResponse.setLat(lat);
        providerResponse.setLon(lon);
        providerResponse.setDistance(distanceMi);
        return providerResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class ProviderNotFoundException extends RuntimeException {
        ProviderNotFoundException(UUID id) {
            super("Provider not found: " + id);
        }
    }
}
