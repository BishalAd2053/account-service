package com.marketplace.accounts.repository;

import com.carmarketplace.common.domain.provider.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProviderRepository extends JpaRepository<ServiceProvider, UUID> {
}
