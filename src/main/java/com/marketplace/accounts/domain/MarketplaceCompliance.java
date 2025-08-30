package com.marketplace.accounts.domain;

import com.carmarketplace.common.domain.Guard;
import jakarta.persistence.Embeddable;

import java.util.Map;

/**
 * Information required for operating within the marketplace.
 */
@Embeddable
public class MarketplaceCompliance {
    private Map<String, Object> paymentDetails;
    private boolean agreedTerms;
    private boolean identityVerified;
    private String governmentIdUrl;

    public MarketplaceCompliance(Map<String, Object> paymentDetails,
                                 boolean agreedTerms,
                                 boolean identityVerified,
                                 String governmentIdUrl) {
        setPaymentDetails(paymentDetails);
        setAgreedTerms(agreedTerms);
        setIdentityVerified(identityVerified);
        setGovernmentIdUrl(governmentIdUrl);
    }

    public Map<String, Object> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Map<String, Object> paymentDetails) {
        Guard.notNull(paymentDetails, "paymentDetails");
        this.paymentDetails = Map.copyOf(paymentDetails);
    }

    public boolean isAgreedTerms() {
        return agreedTerms;
    }

    public void setAgreedTerms(boolean agreedTerms) {
        this.agreedTerms = agreedTerms;
    }

    public boolean isIdentityVerified() {
        return identityVerified;
    }

    public void setIdentityVerified(boolean identityVerified) {
        this.identityVerified = identityVerified;
    }

    public String getGovernmentIdUrl() {
        return governmentIdUrl;
    }

    public void setGovernmentIdUrl(String governmentIdUrl) {
        Guard.notBlank(governmentIdUrl, "governmentIdUrl");
        this.governmentIdUrl = governmentIdUrl;
    }
}
