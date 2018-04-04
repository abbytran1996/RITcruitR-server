package com.avalanche.tmcs.company;

import java.io.File;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
public class NewCompany extends Company {
    public String companyName;
    public String location;
    public String industry;
    public Size size;
    public String websiteURL;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public Company toCompany() {
        Company company = new Company();

        company.setCompanyName(getCompanyName());
        company.setLocation(getLocation());
        company.setIndustry(getIndustry());
        company.setSize(getSize());
        company.setWebsiteURL(getWebsiteURL());
        company.setApprovalStatus(false);
        company.setPresentation("");
        company.setCompanyDescription("");
        company.setEmailSuffix("");
        company.setUserId(-1);

        return company;
    }
}
