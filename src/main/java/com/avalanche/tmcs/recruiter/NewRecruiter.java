package com.avalanche.tmcs.recruiter;

/**
 * @author Zane Grasso
 * @since 7-May-17.
 */
public class NewRecruiter extends Recruiter {
    private String password;
    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Creates a new recruiter with the information from this NewRecruiter
     * <p>This method is provided so Hibernate won't explode</p>
     *
     * @return The new recruiter object
     */
    public Recruiter toRecruiter() {
        Recruiter recruiter = new Recruiter();

        recruiter.setEmail(getEmail());
        recruiter.setFirstName(getFirstName());
        recruiter.setLastName(getLastName());
        recruiter.setPhoneNumber("");
        recruiter.setUser(getUser());
        recruiter.setCompany(getCompany());

        return recruiter;
    }
}
