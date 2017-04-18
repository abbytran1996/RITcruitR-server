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

    public Company toCompany(){
        Company company = new Company();

        company.setEmail(getEmail());
    }
}




     /*   Student student = new Student();

        student.setEmail(getEmail());
        student.setFirstName(getFirstName());
        student.setLastName(getLastName());
        student.setGraduationDate(getGraduationDate());
        student.setPhoneNumber(getPhoneNumber());
        student.setPreferredStates(getPreferredStates());
        student.setSchool(getSchool());
        student.setSkills(getSkills());
        student.setUser(getUser());

        return student;

        */