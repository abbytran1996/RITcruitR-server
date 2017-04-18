package com.avalanche.tmcs.job_posting;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Maxwell Hadley
 * @since 4/17/17.
 */
@Entity
public class JobPosting {

    private long id;

    private String positionTitle;

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

    private String location;

    private long phaseTimeout;

    private String problemStatement;

    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NotNull
    public long getPhaseTimeout() {
        return phaseTimeout;
    }

    public void setPhaseTimeout(long phaseTimeout) {
        this.phaseTimeout = phaseTimeout;
    }

    @NotNull
    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
