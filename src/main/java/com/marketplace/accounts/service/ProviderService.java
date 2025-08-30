package com.marketplace.accounts.service;

import com.carmarketplace.common.domain.provider.ServiceProvider;
import com.marketplace.accounts.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProviderService {
    private final ProviderRepository repository;

    public ProviderService(ProviderRepository repository) {
        this.repository = repository;
    }

    public ServiceProvider register(ServiceProvider provider) {
        return repository.save(provider);
    }

    public ServiceProvider update(UUID id, ServiceProvider provider) {
        provider.setId(id);
        return repository.save(provider);
    }

    public ServiceProvider get(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
