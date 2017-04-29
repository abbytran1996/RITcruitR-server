package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.job_posting.JobPosting;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.util.List;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
@Entity
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

    private User user;

    private String companyName;

    private String location;

    private Size size;

    private Boolean approvalStatus;

    private String emailSuffix;

    private File presentation;

    private String companyDescription;

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
    public String getLocation(){return location;}

    public void setLocation(String location) {this.location = location;}

    @NotNull
    public Size getSize(){return size;}

    public void setSize(Size size) {this.size = size;}

    @NotNull
    public Boolean getApprovalStatus(){return approvalStatus;}

    public void setApprovalStatus(Boolean approvalStatus) {this.approvalStatus = approvalStatus;}

    @NotNull
    public String getEmailSuffix(){return emailSuffix;}

    public void setEmailSuffix(String emailSuffix) {this.emailSuffix = emailSuffix;}

    @NotNull
    public File getPresentation(){return presentation;}

    public void setPresentation(File presentation) {this.presentation = presentation;}


    @NotNull
    public String getCompanyDescription(){
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription){
        this.companyDescription = companyDescription;
    }

    @OneToOne
    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

