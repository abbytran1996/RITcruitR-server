package com.avalanche.tmcs.job_posting;

import java.util.Set;

/**
 * Created by Ryan Hochmuth on 4/10/2018.
 * <p>
 */
public class NewJobPosting extends JobPosting {
    private long recruiterId;
//    private Set<JobPresentationLink> presentationLinks;


    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }

//    public Set<JobPresentationLink> getPresentationLinks() {
//        return presentationLinks;
//    }
//
//    public void setPresentationLinks(Set<JobPresentationLink> presentationLinks) {
//        this.presentationLinks = presentationLinks;
//    }

//    private void createJobPresentationLink(){
//        JobPresentationLink newLink = new JobPresentationLink();
//        newLink.setTitle(getPositionTitle());
//        newLink.setPresentationLink(getPresentationLinks());
//    }

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
//        job.setPresentationLinks(getPresentationLinks());

        return job;
    }
}
