package com.avalanche.tmcs.company;

import com.avalanche.tmcs.data.Industry;
import com.avalanche.tmcs.matching.PresentationLink;
import org.springframework.data.annotation.CreatedDate;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
@Entity
@Table(name="company")
public class Company {
    public enum Status {
        AWAITING_APPROVAL, APPROVED, DENIED, ARCHIVED
    }

    public enum Size {
        DONT_CARE, STARTUP, SMALL, MEDIUM, LARGE, HUGE
    }

    public static Status getStatusFromString(String statusString){
        Status companyStatus;
        if(StringUtils.equalsIgnoreCase("archived", statusString))
            companyStatus = Status.ARCHIVED;
        else if(StringUtils.equalsIgnoreCase("approved", statusString))
            companyStatus = Status.APPROVED;
        else if(StringUtils.equalsIgnoreCase("denied", statusString))
            companyStatus = Status.DENIED;
        else
            companyStatus = Status.AWAITING_APPROVAL;

        return companyStatus;

    }

    private long id;

    private Status status = Status.AWAITING_APPROVAL;

    private String companyName;

    private Set<String> locations;

    private Size size;

    private Set<Industry> industries;

    private String presentation;

    private String companyDescription;

    private String websiteURL;

    private String emailSuffix;

    private int userId;

    private Date timeRegistered;

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
    public Set<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(Set<Industry> industries) {
        this.industries = industries;
    }

    @NotNull
    public Size getSize(){return size;}

    public void setSize(Size size) {this.size = size;}

    @NotNull
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
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

    @CreatedDate
    @Temporal(TIMESTAMP)
    public Date getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(Date timeRegistered) {
        this.timeRegistered = timeRegistered;
    }

    @ElementCollection
    public Set<PresentationLink> getPresentationLinks() {
        return presentationLinks;
    }

    public void setPresentationLinks(Set<PresentationLink> presentationLinks) {
        this.presentationLinks = presentationLinks;
    }
}

