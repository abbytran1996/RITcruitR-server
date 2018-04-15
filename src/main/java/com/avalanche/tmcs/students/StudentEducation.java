package com.avalanche.tmcs.students;

import java.sql.Date;

/**
 * Created by Ryan Hochmuth on 4/4/2018.
 * <p>
 */
public class StudentEducation {
    private Date graduationDate;

    private String school;

    private String major;

    private double gpa;

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    public double getGpa() { return gpa; }

    public void setGpa(double gpa) { this.gpa = gpa; }
}
