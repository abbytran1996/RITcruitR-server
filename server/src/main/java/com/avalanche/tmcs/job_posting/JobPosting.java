package com.avalanche.tmcs.job_posting;


import com.avalanche.tmcs.Recruiter.Recruiter;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.matching.Skill;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/17/17.
 */
@Entity
public class JobPosting {

    private long id;

    //Status Enum Below
    private int status;

    private String positionTitle;

    private String description;

    private Set<Skill> requiredSkills;

    private Set<Skill> recommendedSkills;

    private double recommendedSkillsWeight;

    private double matchThreshold;

    private Recruiter recruiter;

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
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public Set<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public Set<Skill> getRecommendedSkills() {
        return recommendedSkills;
    }

    public void setRecommendedSkills(Set<Skill> recommendedSkills) {
        this.recommendedSkills = recommendedSkills;
    }

    @NotNull
    public double getRecommendedSkillsWeight() {
        return recommendedSkillsWeight;
    }

    public void setRecommendedSkillsWeight(double recommendedSkillsWeight) {
        this.recommendedSkillsWeight = recommendedSkillsWeight;
    }

    @NotNull
    public double getMatchThreshold() {
        return matchThreshold;
    }

    public void setMatchThreshold(double matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

    @NotNull
    @ManyToOne
    public Recruiter getRecruiter(){return recruiter;}

    public void setRecruiter(Recruiter newRecruiter){this.recruiter = newRecruiter;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobPosting)) return false;

        JobPosting that = (JobPosting) o;

        if (getId() != that.getId()) return false;
        if (!getPositionTitle().equals(that.getPositionTitle())) return false;
        return getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getPositionTitle().hashCode();
        result = 31 * result + getUrl().hashCode();
        return result;
    }
}

enum Status{
    OPEN(0),
    FULFILLED(1),
    DELETED(2);

    private int status;

    Status(int status){
        this.status = status;
    }

    int toInt(){
        return status;
    }
}
