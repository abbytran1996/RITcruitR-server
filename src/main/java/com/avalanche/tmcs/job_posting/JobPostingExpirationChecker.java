package com.avalanche.tmcs.job_posting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Automated task for job expiration
 *
 * @author Jan
 */
@Component
public class JobPostingExpirationChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobPostingExpirationChecker.class);

    private final JobPostingDAO jobPostingDAO;

    public JobPostingExpirationChecker (final JobPostingDAO jobPostingDAO) {
        this.jobPostingDAO = jobPostingDAO;
    }

    /**
     * This will occur at 8pm every night.
     */
    @Scheduled(cron = "0 0 20 * * *")
    public void automateJobExpiration() {
        LOGGER.info("Checking whether if jobs have expired or not...");

        List<JobPosting> allActiveJobPostings = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE.toInt());
        allActiveJobPostings.stream()
                .forEach(jobPosting -> {
                    long duration = jobPosting.getDuration();

                    // If the duration equals 0, set the status to inactive. Otherwise, decrement by one.
                    if (duration == 0) {
                        jobPosting.setStatus(JobPosting.Status.INACTIVE.toInt());
                    } else {
                        jobPosting.setDuration(duration - 1);
                    }

                    jobPostingDAO.save(jobPosting);
                });
    }
}