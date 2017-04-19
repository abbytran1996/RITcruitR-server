package com.avalanche.tmcs.company;

/**
 * Created by zanegrasso
 * Created on 4/18/17.
 */
public class NewCompany extends Company {
    private String password;

    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm(){
        return passwordConfirm;
    }

    /**
     * Creates a new Company
     * returns new company object
     */
    //may need to reference other attributes to company

    public Company toCompany(){
        Company company = new Company();

        company.setEmail(getEmail());
        company.setEmail(getEmail());
        company.setId(getId());
        company.setCompanyName(getCompanyName());
        company.setCompanyDescription(getCompanyDescription());
        company.setPhoneNumber(getPhoneNumber());

        return company;
    }
}
