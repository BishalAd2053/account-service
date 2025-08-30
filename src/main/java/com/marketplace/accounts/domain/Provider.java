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
    private String businessName;

    private BusinessAddress address;

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


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public BusinessAddress getAddress() {
        return address;
    }

    public void setAddress(BusinessAddress address) {
        this.address = address;
    }
}
