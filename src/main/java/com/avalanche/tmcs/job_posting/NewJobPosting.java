package com.avalanche.tmcs.job_posting;

import java.util.Set;

/**
 * Created by Ryan Hochmuth on 4/10/2018.
 * <p>
 * @author Abigail My Tran
 */
public class NewJobPosting extends JobPosting {
    private long recruiterId;


    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }


    public JobPosting toJobPosting() {
        JobPosting job = new JobPosting();

        job.setGoogleCloudJobName("");
        job.setStatus(Status.ACTIVE);
        job.setPositionTitle(getPositionTitle());
        job.setDescription(getDescription());
        job.setLocations(getLocations());
        job.setRequiredSkills(getRequiredSkills());
        job.setRecommendedSkills(getRecommendedSkills());
        job.setRecommendedSkillsWeight(getRecommendedSkillsWeight());
        job.setMinGPA(getMinGPA());
        job.setHasWorkExperience(getHasWorkExperience());
        job.setMatchThreshold(getMatchThreshold());
        job.setDuration(getDuration());
        job.setProblemStatement(getProblemStatement());
        job.setVideo("");
        job.setCompany(getCompany());
        job.setRecruiter(getRecruiter());

        return job;
    }
}
