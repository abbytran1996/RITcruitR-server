package com.avalanche.tmcs.auth;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Entity
@Table(name="users")
public class User {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;

    public User() {}

    public User(String username, String password, String passwordConfirm, Role studentRole) {
        setUsername(username);
        setPassword(password);
        setPasswordConfirm(passwordConfirm);

        roles = new HashSet<>();
        roles.add(studentRole);

        validateNewUser();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Checks that the password and password confirm match, and that neither are null. Also checks that the username is
     * not null, and that the password is at least eight characters long
     */
    public void validateNewUser() {
        if(password == null || password.isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }

        if(password.length() < 8) {
            throw new ValidationException("Password must be at least eight characters long");
        }

        if(passwordConfirm == null || passwordConfirm.isEmpty()) {
            throw new ValidationException("Password confirmation cannot be empty");
        }

        if(!password.equals(passwordConfirm)) {
            throw new ValidationException("Password and password confirmation must match");
        }

        if(username == null || username.isEmpty()) {
            // We use emails as usernames
            // This code is no longer portable to any application that does not use emails as usernames but all well
            throw new ValidationException("Email cannot be empty");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
