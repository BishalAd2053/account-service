package com.marketplace.accounts.service;

import com.marketplace.accounts.domain.ServiceProvider;
import com.marketplace.accounts.repository.BusinessDetailsDao;
import com.marketplace.accounts.repository.ServiceProviderDao;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProviderService {
    private final ServiceProviderDao providerDao;
    private final BusinessDetailsDao businessDetailsDao;

    public ProviderService(ServiceProviderDao providerDao,
                           BusinessDetailsDao businessDetailsDao) {
        this.providerDao = providerDao;
        this.businessDetailsDao = businessDetailsDao;
    }

    public ServiceProvider register(ServiceProvider provider) {
        ServiceProvider saved = providerDao.save(provider);
        if (provider.getBusinessDetails() != null) {
            businessDetailsDao.save(saved.getId(), provider.getBusinessDetails());
        }
        return saved;
    }

    public ServiceProvider update(UUID id, ServiceProvider provider) {
        provider.setId(id);
        return register(provider);
    }

    public ServiceProvider get(UUID id) {
        return providerDao.find(id);
    }
}
