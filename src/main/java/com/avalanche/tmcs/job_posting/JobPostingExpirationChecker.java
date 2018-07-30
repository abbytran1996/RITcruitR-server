package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.MatchingService;
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
    private final MatchingService matchingService;

    public JobPostingExpirationChecker (final JobPostingDAO jobPostingDAO, final MatchingService matchingService) {
        this.jobPostingDAO = jobPostingDAO;
        this.matchingService= matchingService;
    }

    /**
     * This will occur at 8pm every weekday.
     */
    @Scheduled(cron = "0 0 20 * * 1-5")
    public void automateJobExpiration() {
        LOGGER.info("Checking whether if jobs have expired or not...");

        List<JobPosting> allActiveJobPostings = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE.toInt());
        allActiveJobPostings.stream()
                .forEach(jobPosting -> {
                    int numDaysRemaining = jobPosting.getNumDaysRemaining();

                    if (numDaysRemaining == 0) {
                        jobPosting.setStatus(JobPosting.Status.INACTIVE.toInt());
                        matchingService.expireNonFinalMatchesForJob(jobPosting);
                    } else {
                        jobPosting.setNumDaysRemaining(numDaysRemaining - 1);
                    }

                    jobPostingDAO.save(jobPosting);
                });
    }
}