package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.matching.PresentationLink;
import com.avalanche.tmcs.matching.Skill;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Set;

/**
 * Class to represent a student in the database
 *
 * @author David Dubois
 * @since 05-Apr-17.
 */
@Entity
@Table(name="students")
public class Student {
    private long id;

    private String firstName;

    private String lastName;

    private Set<Skill> skills;

    private String email;

    private Date graduationDate;

    private String school;

    private String major;

    private double gpa;

    private User user;

    private String phoneNumber;

    private String contactEmail;

    private String website; // TODO: Remove field

    private Set<String> preferredLocations;

    private Set<String> preferredIndustries;

    private Set<Integer> preferredCompanySizes;

    private Set<PresentationLink> presentationLinks;

    private Set<ProblemStatement> problemStatements;

    // TODO: Figure out what the job preferences and notification preferences will look like
    // Pretty sure we agreed to handle them later

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
    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    @NotNull
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @OneToOne
    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany
    @JoinTable(name = "student_skill", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @ElementCollection
    public Set<String> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(Set<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    @ElementCollection
    public Set<String> getPreferredIndustries() {
        return preferredIndustries;
    }

    public void setPreferredIndustries(Set<String> preferredIndustries) {
        this.preferredIndustries = preferredIndustries;
    }

    @ElementCollection
    public Set<Integer> getPreferredCompanySizes() {
        return preferredCompanySizes;
    }

    public void setPreferredCompanySizes(Set<Integer> preferredCompanySizes) {
        this.preferredCompanySizes = preferredCompanySizes;
    }

    @ElementCollection
    public Set<PresentationLink> getPresentationLinks() {
        return presentationLinks;
    }

    public void setPresentationLinks(Set<PresentationLink> presentationLinks) {
        this.presentationLinks = presentationLinks;
    }

    @ElementCollection
    public Set<ProblemStatement> getProblemStatements() {
        return problemStatements;
    }

    public void setProblemStatements(Set<ProblemStatement> problemStatements) {
        this.problemStatements = problemStatements;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;

        return id == student.id && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        return result;
    }
}
