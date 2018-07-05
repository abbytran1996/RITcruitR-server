package com.avalanche.tmcs.company;

import com.avalanche.tmcs.matching.PresentationLink;
import org.thymeleaf.util.StringUtils;

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
    public enum Status {
        AWAITING_APPROVAL(0),
        APPROVED(1),
        DENIED(2),
        ARCHIVED(3);

        private int status;

        Status(int status) {
            this.status = status;
        }

        public int toInt(){
            return status;
        }
    }

    public enum Size {
        DONT_CARE(0),
        STARTUP(1),
        SMALL(2),
        MEDIUM(3),
        LARGE(4),
        HUGE(5);

        private int companySize;

        Size(int status){
            this.companySize = status;
        }

        public int toInt(){
            return companySize;
        }
    }

    public static int getIntStatusFromString(String statusString){
        Status companyStatus;
        if(StringUtils.equalsIgnoreCase("archived", statusString))
            companyStatus = Status.ARCHIVED;
        else if(StringUtils.equalsIgnoreCase("approved", statusString))
            companyStatus = Status.APPROVED;
        else if(StringUtils.equalsIgnoreCase("denied", statusString))
            companyStatus = Status.DENIED;
        else
            companyStatus = Status.AWAITING_APPROVAL;

        return companyStatus.toInt();

    }

    private long id;

    private int status = Status.AWAITING_APPROVAL.toInt();

    private String companyName;

    private Set<String> locations;

    private int size;

    private Set<String> industries;

    private String presentation;

    private String companyDescription;

    private String websiteURL;

    private String emailSuffix;

    private int userId;

    private Set<PresentationLink> presentationLinks;

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

    @NotNull
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



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
}

