package com.avalanche.tmcs.Recruiter;

import com.avalanche.tmcs.company.Company;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * Class To Represent a recruiter for a company
 *
 * Created by John on 4/17/2017.
 */
@Entity
@Table(name="recruiters")
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Pattern(regexp = "^.+@.+\\..+$")
    private String email;

    @NotNull
    @ManyToOne
    private Company company;

    /**
     * TODO: Notification Preferences
     */

    /**
     * TODO: Interview Schedule
     */

    @NotNull
    /**
     * TODO: is this supposed to be implimented differently?
     */
    private String password;

    @NotNull
    private String phoneNumber;

    public Recruiter(){}


    public Recruiter(Recruiter other) {
            firstName = other.getFirstName();
            lastName = other.getLastName();
            email = other.getEmail();
            phoneNumber = other.getPhoneNumber();

    }
    public Recruiter(NewRecruiter other) {
        firstName = other.firstName;
        lastName = other.lastName;
        email = other.eMail;
        phoneNumber = other.phoneNumber;

    }

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
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

}
