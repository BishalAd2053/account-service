package com.marketplace.accounts.domain;

import com.carmarketplace.common.domain.Guard;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Information about a specific service offered by a provider.
 */
public class ServiceDetails {
    private UUID id;
    private ServiceCategory category;
    private String title;
    private String description;
    private PriceType priceType;
    private BigDecimal priceValue;
    private List<String> specialOffers;

    public ServiceDetails(UUID id,
                          ServiceCategory category,
                          String title,
                          String description,
                          PriceType priceType,
                          BigDecimal priceValue,
                          List<String> specialOffers) {
        setId(id);
        setCategory(category);
        setTitle(title);
        setDescription(description);
        setPriceType(priceType);
        setPriceValue(priceValue);
        setSpecialOffers(specialOffers);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        Guard.notNull(id, "id");
        this.id = id;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        Guard.notNull(category, "category");
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Guard.notBlank(title, "title");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Guard.notBlank(description, "description");
        this.description = description;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        Guard.notNull(priceType, "priceType");
        this.priceType = priceType;
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(BigDecimal priceValue) {
        Guard.notNull(priceValue, "priceValue");
        if (priceValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("priceValue must be non-negative");
        }
        this.priceValue = priceValue;
    }

    public List<String> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<String> specialOffers) {
        Guard.notNull(specialOffers, "specialOffers");
        this.specialOffers = List.copyOf(specialOffers);
    }
}
