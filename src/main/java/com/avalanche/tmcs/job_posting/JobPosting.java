package com.avalanche.tmcs.job_posting;


import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.students.Student;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Maxwell Hadley
 * @since 4/17/17.
 * @author Abigail My Tran
 */
@Entity
@Table(name="job_posting")
public class JobPosting {
    public enum Status {
        ACTIVE,
        INACTIVE,
        ARCHIVED,
        NEEDS_DETAILING
    }

    private long id;

    private String googleCloudJobName;

    private Status status;

    private String positionTitle;

    private String description;

    private Set<String> locations;

    private Set<Skill> requiredSkills;

    private Set<Skill> recommendedSkills;
    private double recommendedSkillsWeight = 0.2;

    private double minGPA;
    private double minGPAWeight = 0.3;

    private boolean hasWorkExperience;

    private double matchThreshold = 0.6;

    @Max(10)
    private int duration = 10;

    private int numDaysRemaining;

    private String problemStatement;

    private String video;

    private Company company;

    private Recruiter recruiter;

    private Set<JobPresentationLink> presentationLinks;

    public boolean readyToMatch(){
        return Status.ACTIVE.equals(this.status) && Company.Status.APPROVED.equals(this.company.getStatus());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoogleCloudJobName() {
        return googleCloudJobName;
    }

    public void setGoogleCloudJobName(String googleCloudJobName) {
        this.googleCloudJobName = googleCloudJobName;
    }

    @NotNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
    public Set<Skill> getRecommendedSkills() {
        return this.recommendedSkills;
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
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getNumDaysRemaining() {
    	return this.numDaysRemaining;
    }
    
    public void setNumDaysRemaining(int numDays) {
    	this.numDaysRemaining = numDays;
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

    public double calculateJobFiltersWeight(){
        return minGPAWeight;
    }

    public double calculateJobFiltersScore(Student student){
        boolean gpaMatch = student.getGpa() >= minGPA;
        return gpaMatch? minGPAWeight * 1.0 : 0;
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
