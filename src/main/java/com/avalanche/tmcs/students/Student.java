package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.matching.Skill;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.net.URI;
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

    private User user;

    private String phoneNumber;

    private Set<String> preferredStates;

    private Company.Size preferredCompanySize;

    private String resumeLocation;

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
    // Emails have something, then an @ sign, then something, then a period, then something
    // This is possibly overly broad, but students have to validateNewUser their email addresses with a confirmation email so
    // this is mostly a sanity check to ensure things aren't horrible
    // Also I wanted to use JPA validation annotations
    @Pattern(regexp = "^.+@.+\\..+$")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // This app is intended for a replacement for on-school career fairs. Students have not graduated yet by definition,
    // so their graduation dates are in the future implicitly.
    @Future
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public Set<String> getPreferredStates() {
        return preferredStates;
    }

    public void setPreferredStates(Set<String> preferredStates) {
        this.preferredStates = preferredStates;
    }

    @NotNull
    public Company.Size getPreferredCompanySize() {
        return preferredCompanySize;
    }

    public void setPreferredCompanySize(Company.Size preferredCompanySize) {
        this.preferredCompanySize = preferredCompanySize;
    }

    public String getResumeLocation(){
        return resumeLocation;
    }

    public void setResumeLocation(String newResume){
        this.resumeLocation = newResume;
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
