package com.marketplace.accounts.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    /**
     * Comma separated list of service tags offered by the provider.
     */
    @Column(nullable = false)
    private String services;

    private BigDecimal rating;

    /**
     * Location stored as PostGIS geometry point in WGS84 (lat/lon).
     */
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;
}
