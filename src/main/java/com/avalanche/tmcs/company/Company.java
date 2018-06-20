package com.avalanche.tmcs.company;

import com.avalanche.tmcs.matching.PresentationLink;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
@Entity
@Table(name="company")
public class Company {
    public enum Size {
        DONT_CARE,
        STARTUP,
        SMALL,
        MEDIUM,
        LARGE,
        HUGE,
    }

    private long id;

    private String companyName;

    private Set<String> locations;

    private int size;

    private Set<String> industries;

    private Boolean approvalStatus;

    private String presentation;

    private String companyDescription;

    private String websiteURL;

    private String emailSuffix;

    private int userId;

    private Set<PresentationLink> presentationLinks;
    
    private int portfolium_id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NotNull
    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    @NotNull
    @ElementCollection
    public Set<String> getLocations(){return locations;}

    public void setLocations(Set<String> locations) {this.locations = locations;}

    @NotNull
    @ElementCollection
    public Set<String> getIndustries() {
        return industries;
    }

    public void setIndustries(Set<String> industries) {
        this.industries = industries;
    }

    @NotNull
    public int getSize(){return size;}

    public void setSize(int size) {this.size = size;}

    public Boolean getApprovalStatus(){return approvalStatus;}

    public void setApprovalStatus(Boolean approvalStatus) {this.approvalStatus = approvalStatus;}

    @NotNull
    public String getPresentation(){return presentation;}

    public void setPresentation(String presentation) {this.presentation = presentation;}

    @NotNull
    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    @NotNull
    public String getCompanyDescription(){
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription){
        this.companyDescription = companyDescription;
    }

    @NotNull
    public String getEmailSuffix() {
        return emailSuffix;
    }

    public void setEmailSuffix(String emailSuffix) {
        this.emailSuffix = emailSuffix;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @ElementCollection
    public Set<PresentationLink> getPresentationLinks() {
        return presentationLinks;
    }

    public void setPresentationLinks(Set<PresentationLink> presentationLinks) {
        this.presentationLinks = presentationLinks;
    }
    
    public int getPortfoliumId() {
    	return this.portfolium_id;
    }
    
    public void setPortfoliumId(int id) {
    	this.portfolium_id = id;
    }
    
    public boolean hasPortfoliumId() {
    	return this.portfolium_id != 0;
    }
}

