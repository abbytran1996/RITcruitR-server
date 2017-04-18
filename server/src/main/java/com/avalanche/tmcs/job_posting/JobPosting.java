package com.avalanche.tmcs.job_posting;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Maxwell Hadley
 * @since 4/17/17.
 */
@Entity
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String positionTitle;

    @NotNull
    private String description;

    // @ManyToMany
    // @JoinColumn(name = "id")
    // private Set<Skill> requiredSkills = new HashSet<Skill>();

    // @ManyToMany
    // @JoinColumn(name = "id")
    // private Set<Skill> recommendedSkills = new HashSet<Skill>();

    // @ManyToOne
    // @JoinColumn(name = "id")
    // private Recruiter recruiter;

    @NotNull
    private String location;

    @NotNull
    private long phaseTimeout;

    @NotNull
    private String problemStatement;

    @NotNull
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getPhaseTimeout() {
        return phaseTimeout;
    }

    public void setPhaseTimeout(long phaseTimeout) {
        this.phaseTimeout = phaseTimeout;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
