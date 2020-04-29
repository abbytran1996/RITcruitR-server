package com.avalanche.tmcs.company;

import com.avalanche.tmcs.matching.Industry;

import java.util.Set;

/**
 * @author Zane Grasso
 * @since 4/18/17
 * @author Abigail My Tran
 */
public class NewCompany extends Company {
    //TODO: Add a field for headquartersAddress
    public String companyName;
    public Set<String> locations;
    public Set<Industry> industries;
    public Size size;
    public String websiteURL;

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<String> getLocations() {
        return locations;
    }
    public void setLocations(Set<String> locations) {
        this.locations = locations;
    }

    public Set<Industry> getIndustries() {
        return industries;
    }
    public void setIndustries(Set<Industry> industries) {
        this.industries = industries;
    }

    public Size getSize() { return size; }
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
        company.setLocations(getLocations());
        company.setIndustries(getIndustries());
        company.setSize(getSize());
        company.setWebsiteURL(getWebsiteURL());
        company.setStatus(Status.AWAITING_APPROVAL);
        company.setPresentation("");
        company.setCompanyDescription("");
        company.setEmailSuffix("");
        company.setUserId(-1);
        company.setPresentationLinks(getPresentationLinks());
        company.setGoogleCloudName("");

        return company;
    }
}
