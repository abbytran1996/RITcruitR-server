package com.avalanche.tmcs.students;

import com.avalanche.tmcs.company.Company;

import java.sql.Date;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
public class NewStudent extends Student {
    private String password;

    private String passwordConfirm;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Creates a new Student with the information from this NewStudent
     * <p>This method is provided so Hibernate won't explode</p>
     *
     * @return The new Student object
     */
    public Student toStudent() {
        Student student = new Student();

        student.setEmail(getEmail());
        student.setFirstName(getFirstName());
        student.setLastName(getLastName());
        student.setGraduationDate(new Date(System.currentTimeMillis() + 900000));
        student.setPhoneNumber(null);
        student.setContactEmail(null);
        student.setWebsite(null);
        student.setPreferredLocations(null);
        student.setPreferredIndustries(null);
        student.setPreferredCompanySizes(null);
        student.setSchool("");
        student.setMajor(null);
        student.setGpa(0.0);
        student.setSkills(null);
        student.setUser(getUser());
        student.setPresentationLinks(getPresentationLinks());

        return student;
    }
}
