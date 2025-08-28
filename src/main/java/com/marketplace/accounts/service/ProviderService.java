package com.marketplace.accounts.service;

import com.marketplace.accounts.domain.Provider;
import com.marketplace.accounts.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository repository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public Provider register(String name, List<String> services, double lat, double lon) {
        Point location = geometryFactory.createPoint(new Coordinate(lon, lat));
        Provider provider = Provider.builder()
                .name(name)
                .services(String.join(",", services))
                .location(location)
                .build();
        return repository.save(provider);
    }

    public Provider update(UUID id, String name, List<String> services, double lat, double lon) {
        Provider provider = repository.findById(id).orElseThrow();
        provider.setName(name);
        provider.setServices(String.join(",", services));
        provider.setLocation(geometryFactory.createPoint(new Coordinate(lon, lat)));
        return repository.save(provider);
    }

    public Provider get(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public List<Provider> nearby(double lat, double lon, double radiusMi, int limit) {
        double radiusMeters = radiusMi * 1609.34;
        return repository.searchNearby(lat, lon, radiusMeters, limit);
    }
}
