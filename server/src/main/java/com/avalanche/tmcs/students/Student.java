package com.avalanche.tmcs.students;

import java.sql.Date;

/**
 * @author David Dubois
 * @since 10-Apr-17.
 */
public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private Date graduationDate;
    private String school;
    private String phoneNumber;

    public Student(StudentModel other) {
        firstName = other.getFirstName();
        lastName = other.getLastName();
        email = other.getEmail();
        graduationDate = other.getGraduationDate();
        school = other.getSchool();
        phoneNumber = other.getPhoneNumber();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public String getSchool() {
        return school;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
