package com.avalanche.tmcs.job_posting;

/**
 * Created by Ryan Hochmuth on 4/10/2018.
 * <p>
 */
public class NewJobPosting extends JobPosting {
    private long recruiterId;

    private String newVideo;

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getNewVideo() {
        return newVideo;
    }

    public void setNewVideo(String newVideo) {
        this.newVideo = newVideo;
    }

    public JobPosting toJobPosting() {
        JobPosting job = new JobPosting();

        job.setStatus(getStatus());
        job.setPositionTitle(getPositionTitle());
        job.setDescription(getDescription());
        job.setLocations(getLocations());
        job.setRequiredSkills(getRequiredSkills());
        job.setNiceToHaveSkills(getNiceToHaveSkills());
        job.setNiceToHaveSkillsWeight(getNiceToHaveSkillsWeight());
        job.setMinGPA(getMinGPA());
        job.setHasWorkExperience(getHasWorkExperience());
        job.setMatchThreshold(getMatchThreshold());
        job.setDuration(getDuration());
        job.setProblemStatement(getProblemStatement());
        job.setVideo(getVideo());
        job.setCompany(getCompany());
        job.setRecruiter(getRecruiter());
        job.setPresentationLinks(getPresentationLinks());

        return job;
    }
}
