package com.avalanche.tmcs.job_posting;


import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.Skill;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/17/17.
 */
@Entity
@Table(name="job_posting")
public class JobPosting {
    public enum Status{
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

    private long id;

    // Status Enum above
    private int status;

    private String positionTitle;

    private String description;

    private Set<String> locations;

    private Set<Skill> requiredSkills;

    private Set<Skill> niceToHaveSkills;

    private double niceToHaveSkillsWeight;

    private double minGPA;

    private boolean hasWorkExperience;

    private double matchThreshold;

    private long duration;

    private String problemStatement;

    private String video;

    private Company company;

    private Recruiter recruiter;

    private Set<JobPresentationLink> presentationLinks;

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

    @Lob
    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @ElementCollection
    public Set<String> getLocations() {
        return locations;
    }

    public void setLocations(Set<String> locations) {
        this.locations = locations;
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
    public Set<Skill> getNiceToHaveSkills() {
        return niceToHaveSkills;
    }

    public void setNiceToHaveSkills(Set<Skill> niceToHaveSkills) {
        this.niceToHaveSkills = niceToHaveSkills;
    }

    @NotNull
    public double getNiceToHaveSkillsWeight() {
        return niceToHaveSkillsWeight;
    }

    public void setNiceToHaveSkillsWeight(double niceToHaveSkillsWeight) {
        this.niceToHaveSkillsWeight = niceToHaveSkillsWeight;
    }

    public double getMinGPA() { return minGPA; }

    public void setMinGPA(double gpa) { this.minGPA = gpa; }

    public boolean getHasWorkExperience() {
        return hasWorkExperience;
    }

    public void setHasWorkExperience(boolean hasWorkExperience) {
        this.hasWorkExperience = hasWorkExperience;
    }

    @NotNull
    public double getMatchThreshold() {
        return matchThreshold;
    }

    public void setMatchThreshold(double matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

    @NotNull
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @NotNull
    @Lob
    @Column( length = 100000 )
    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    @NotNull
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @NotNull
    @ManyToOne
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @NotNull
    @ManyToOne
    public Recruiter getRecruiter(){return recruiter;}

    public void setRecruiter(Recruiter newRecruiter){this.recruiter = newRecruiter;}

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    public Set<JobPresentationLink> getPresentationLinks() {
        return presentationLinks;
    }

    public void setPresentationLinks(Set<JobPresentationLink> presentationLinks) {
        this.presentationLinks = presentationLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobPosting)) return false;

        JobPosting that = (JobPosting) o;

        if (getId() != that.getId()) return false;
        if (!getPositionTitle().equals(that.getPositionTitle())) return false;
        return getVideo().equals(that.getVideo());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getPositionTitle().hashCode();
        result = 31 * result + getVideo().hashCode();
        return result;
    }
}
