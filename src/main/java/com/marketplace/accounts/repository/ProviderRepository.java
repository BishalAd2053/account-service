package com.marketplace.accounts.repository;

import com.marketplace.accounts.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {

    @Query(value = "SELECT * FROM providers p WHERE ST_DWithin(p.location, ST_SetSRID(ST_MakePoint(:lon,:lat),4326), :radius) " +
            "ORDER BY ST_DistanceSphere(p.location, ST_SetSRID(ST_MakePoint(:lon,:lat),4326)) LIMIT :limit", nativeQuery = true)
    List<Provider> searchNearby(@Param("lat") double lat,
                                @Param("lon") double lon,
                                @Param("radius") double radius,
                                @Param("limit") int limit);
}
