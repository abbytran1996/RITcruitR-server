package com.avalanche.tmcs.students;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
public class NewStudent extends Student {
    private String password;

    private String passwordConfirm;

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
        student.setGraduationDate(getGraduationDate());
        student.setPhoneNumber(getPhoneNumber());
        student.setPreferredStates(getPreferredStates());
        student.setPreferredCompanySize(getPreferredCompanySize());
        student.setSchool(getSchool());
        student.setSkills(getSkills());
        student.setUser(getUser());

        return student;
    }
}
