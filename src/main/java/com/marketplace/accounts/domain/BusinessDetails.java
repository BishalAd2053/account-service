package com.marketplace.accounts.domain;

import com.carmarketplace.common.domain.Guard;
import jakarta.persistence.Embeddable;

import java.util.List;

/**
 * Licensing and compliance information for a business.
 */
@Embeddable
public class BusinessDetails {
    private String businessLicense;
    private String insuranceInfo;
    private List<String> certifications;
    private String taxInfo;

    public BusinessDetails(String businessLicense,
                           String insuranceInfo,
                           List<String> certifications,
                           String taxInfo) {
        setBusinessLicense(businessLicense);
        setInsuranceInfo(insuranceInfo);
        setCertifications(certifications);
        setTaxInfo(taxInfo);
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        Guard.notBlank(businessLicense, "businessLicense");
        this.businessLicense = businessLicense;
    }

    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        Guard.notBlank(insuranceInfo, "insuranceInfo");
        this.insuranceInfo = insuranceInfo;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<String> certifications) {
        Guard.notNull(certifications, "certifications");
        this.certifications = List.copyOf(certifications);
    }

    public String getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(String taxInfo) {
        Guard.notBlank(taxInfo, "taxInfo");
        this.taxInfo = taxInfo;
    }
}
