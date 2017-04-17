package com.avalanche.tmcs.students;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

/**
 * Class to represent a student in the database
 *
 * @author David Dubois
 * @since 05-Apr-17.
 */
@Entity
@Table(name="students")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    // TODO: Uncomment when we write the Skills model
    // @OneToMany(mappedBy = "student")
    // private Set<Skill> skills = new HashSet<>();

    @NotNull
    // Emails have something, then an @ sign, then something, then a period, then something
    // This is possibly overly broad, but students have to validate their email addresses with a confirmation email so
    // this is mostly a sanity check to ensure things aren't horrible
    // Also I wanted to use JPA validation annotations
    @Pattern(regexp = "^.+@.+\\..+$")
    private String email;

    @NotNull
    private String password;

    // This app is intended for a replacement for on-school career fairs. Students have not graduated yet by definition,
    // so their graduation dates are in the future implicitly.
    @Future
    private Date graduationDate;

    @NotNull
    private String school;

    private String phoneNumber;

    // TODO: Figure out what the job preferences and notification preferences will look like
    // Pretty sure we agreed to handle them later

    public StudentModel() {}

    public StudentModel(Student other) {
        firstName = other.getFirstName();
        lastName = other.getLastName();
        email = other.getEmail();
        graduationDate = other.getGraduationDate();
        school = other.getSchool();
        phoneNumber = other.getPhoneNumber();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof StudentModel)) {
            return false;
        }

        StudentModel student = (StudentModel) o;

        return id == student.id && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        return result;
    }
}
