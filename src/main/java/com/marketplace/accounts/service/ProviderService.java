package com.marketplace.accounts.service;

import com.marketplace.accounts.domain.ServiceProvider;
import com.marketplace.accounts.repository.BusinessDetailsDao;
import com.marketplace.accounts.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProviderService {
    private final ProviderRepository repository;
    private final BusinessDetailsDao businessDetailsDao;

    public ProviderService(ProviderRepository repository,
                           BusinessDetailsDao businessDetailsDao) {
        this.repository = repository;
        this.businessDetailsDao = businessDetailsDao;
    }

    public ServiceProvider register(ServiceProvider provider) {
        ServiceProvider saved = repository.save(provider);
        if (provider.getBusinessDetails() != null) {
            businessDetailsDao.save(saved.getId(), provider.getBusinessDetails());
        }
        return saved;
    }

    public ServiceProvider update(UUID id, ServiceProvider provider) {
        provider.setId(id);
        ServiceProvider saved = repository.save(provider);
        if (provider.getBusinessDetails() != null) {
            businessDetailsDao.save(saved.getId(), provider.getBusinessDetails());
        }
        return saved;
    }

    public ServiceProvider get(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
