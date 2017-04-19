package com.avalanche.tmcs.company;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
public class NewCompany extends Company {
    /**
     * Creates a new Company
     * returns new company object
     */

    //may need to reference other attributes to company

    public Company toCompany(){
        Company company = new Company();

        company.setApprovalStatus(getApprovalStatus());
        company.setEmailSuffix(getEmailSuffix());
        company.setId(getId());
        company.setCompanyName(getCompanyName());
        company.setCompanyDescription(getCompanyDescription());
        company.setPresentation(getPresentation());
        company.setLocation(getLocation());
        company.setSize(getSize());
        company.setUser(getUser());

        return company;
    }
}
