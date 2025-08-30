package com.marketplace.accounts.controller;

import com.marketplace.accounts.domain.ServiceProvider;
import com.marketplace.accounts.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts/providers")
public class ProviderController {

    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceProvider register(@Validated @RequestBody ServiceProvider request) {
        return service.register(request);
    }

    @PutMapping("/{id}")
    public ServiceProvider update(@PathVariable UUID id, @Validated @RequestBody ServiceProvider request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    public ServiceProvider byId(@PathVariable UUID id) {
        ServiceProvider provider = service.get(id);
        if (provider == null) {
            throw new ProviderNotFoundException(id);
        }
        return provider;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class ProviderNotFoundException extends RuntimeException {
        ProviderNotFoundException(UUID id) {
            super("Provider not found: " + id);
        }
    }
}
