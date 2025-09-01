package com.marketplace.accounts.domain;

import com.carmarketplace.common.domain.Guard;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root representing a service provider in the marketplace.
 */
public class ServiceProvider {
    private UUID id;
    private String fullName;
    private String contactPhone;
    private String contactEmail;
    private String address;
    private String profilePictureUrl;
    private String bio;
    private BusinessDetails businessDetails;
    private List<ServiceDetails> services;
    private OperationalDetails operationalDetails;
    private Experience experience;
    private MarketplaceCompliance marketplaceCompliance;
    private List<Review> reviews;

    public ServiceProvider(UUID id,
                           String fullName,
                           String contactPhone,
                           String contactEmail,
                           String address,
                           String profilePictureUrl,
                           String bio,
                           BusinessDetails businessDetails,
                           List<ServiceDetails> services,
                           OperationalDetails operationalDetails,
                           Experience experience,
                           MarketplaceCompliance marketplaceCompliance,
                           List<Review> reviews) {
        setId(id);
        setFullName(fullName);
        setContactPhone(contactPhone);
        setContactEmail(contactEmail);
        setAddress(address);
        setProfilePictureUrl(profilePictureUrl);
        setBio(bio);
        setBusinessDetails(businessDetails);
        setServices(services);
        setOperationalDetails(operationalDetails);
        setExperience(experience);
        setMarketplaceCompliance(marketplaceCompliance);
        setReviews(reviews);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        Guard.notNull(id, "id");
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        Guard.notBlank(fullName, "fullName");
        this.fullName = fullName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        Guard.notBlank(contactPhone, "contactPhone");
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        Guard.notBlank(contactEmail, "contactEmail");
        this.contactEmail = contactEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Guard.notBlank(address, "address");
        this.address = address;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        Guard.notBlank(profilePictureUrl, "profilePictureUrl");
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        Guard.notBlank(bio, "bio");
        this.bio = bio;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        Guard.notNull(businessDetails, "businessDetails");
        this.businessDetails = businessDetails;
    }

    public List<ServiceDetails> getServices() {
        return services;
    }

    public void setServices(List<ServiceDetails> services) {
        Guard.notNull(services, "services");
        this.services = List.copyOf(services);
    }

    public OperationalDetails getOperationalDetails() {
        return operationalDetails;
    }

    public void setOperationalDetails(OperationalDetails operationalDetails) {
        Guard.notNull(operationalDetails, "operationalDetails");
        this.operationalDetails = operationalDetails;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        Guard.notNull(experience, "experience");
        this.experience = experience;
    }

    public MarketplaceCompliance getMarketplaceCompliance() {
        return marketplaceCompliance;
    }

    public void setMarketplaceCompliance(MarketplaceCompliance marketplaceCompliance) {
        Guard.notNull(marketplaceCompliance, "marketplaceCompliance");
        this.marketplaceCompliance = marketplaceCompliance;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        Guard.notNull(reviews, "reviews");
        this.reviews = List.copyOf(reviews);
    }
}
