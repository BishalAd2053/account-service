package com.marketplace.accounts.domain;

import com.carmarketplace.common.domain.Guard;
import java.util.List;

/**
 * Professional experience information for a provider.
 */
public class Experience {
    private int yearsOfExperience;
    private List<String> portfolioUrls;
    private List<String> references;

    public Experience(int yearsOfExperience,
                      List<String> portfolioUrls,
                      List<String> references) {
        setYearsOfExperience(yearsOfExperience);
        setPortfolioUrls(portfolioUrls);
        setReferences(references);
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        Guard.nonNegative(yearsOfExperience, "yearsOfExperience");
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<String> getPortfolioUrls() {
        return portfolioUrls;
    }

    public void setPortfolioUrls(List<String> portfolioUrls) {
        Guard.notNull(portfolioUrls, "portfolioUrls");
        this.portfolioUrls = List.copyOf(portfolioUrls);
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        Guard.notNull(references, "references");
        this.references = List.copyOf(references);
    }
}
