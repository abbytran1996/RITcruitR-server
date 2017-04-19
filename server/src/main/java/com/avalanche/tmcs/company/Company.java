package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.User;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */
public class Company {

    private long id;

    //private String firstName;

    //private String lastName;

    private String companyName;

    private String email;

    private User user;

    private String phoneNumber;

    private String companyDescription;

    //to do; data type for uploading file, CompanyPresentation

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /* Not sure if I need these...
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
    */


    @NotNull
    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    @NotNull
    // Copied from David's Student Class
    @Pattern(regexp = "^.+@.+\\..+$")
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


    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Company)) {
            return false;
        }

        Company student = (Company) o;

        return id == student.id && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        return result;
    }

}

