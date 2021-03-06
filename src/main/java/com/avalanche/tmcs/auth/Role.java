package com.avalanche.tmcs.auth;

import javax.persistence.*;
import java.util.Set;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Entity
@Table(name = "role")
public class Role {
    public enum RoleName {
        Student,
        Recruiter,
        Administrator,
        PrimaryRecruiter
    }

    private Long id;
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
