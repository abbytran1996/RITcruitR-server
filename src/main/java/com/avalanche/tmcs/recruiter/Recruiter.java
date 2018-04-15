package com.avalanche.tmcs.recruiter;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.company.Company;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class To Represent a recruiter for a company
 *
 * Created by John on 4/17/2017.
 */
@Entity
@Table(name="recruiters")
public class Recruiter {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private Company company;

    /**
     * TODO: Notification Preferences
     */

    /**
     * TODO: Interview Schedule
     */

    private String phoneNumber;

    private String contactEmail;

    private User user;

    public Recruiter(){}


    public void editRecruiter(Recruiter newinfo){
        if(newinfo.getFirstName()!=null)
            firstName = newinfo.getFirstName();
        if(newinfo.getLastName() != null)
            lastName = newinfo.getLastName();
        if(newinfo.getEmail()!=null)
            email = newinfo.getEmail();
        if(newinfo.getPhoneNumber() !=null)
            phoneNumber = newinfo.getPhoneNumber();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Pattern(regexp = "^.+@.+\\..+$")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    @ManyToOne
    public Company getCompany() { return company; }

    public void setCompany(Company company) { this.company = company; }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Recruiter)){
            return false;
        }
        return id == ((Recruiter) obj).getId();
    }

    @OneToOne
    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
