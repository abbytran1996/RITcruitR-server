package com.avalanche.tmcs.job_posting;


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

    private int minMatchedRequiredSkills;

    private Set<Skill> recommendedSkills;

    private double recommendedSkillsWeight;

    //private Recruiter recruiter;

    private String location;

    private long phaseTimeout;

    private String problemStatement;

    private String url;

    private Company company;

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

    @ManyToMany
    @JoinColumn(name = "id")
    public Set<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(Set<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    @NotNull
    public int getMinMatchedRequiredSkills() {
        return minMatchedRequiredSkills;
    }

    public void setMinMatchedRequiredSkills(int minMatchedRequiredSkills) {
        this.minMatchedRequiredSkills = minMatchedRequiredSkills;
    }

    @ManyToMany
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

    @NotNull
    @ManyToOne
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
