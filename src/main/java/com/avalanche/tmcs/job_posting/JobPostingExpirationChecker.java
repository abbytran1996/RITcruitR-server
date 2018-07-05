package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.Match;
import com.avalanche.tmcs.matching.MatchDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    private final MatchDAO matchDAO;

    public JobPostingExpirationChecker (final JobPostingDAO jobPostingDAO, final MatchDAO matchDAO) {
        this.jobPostingDAO = jobPostingDAO;
        this.matchDAO = matchDAO;
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

                        List<Match> newlyExpiredMatches = matchDAO.findAllByJobAndApplicationStatus(jobPosting, Match.ApplicationStatus.IN_PROGRESS);
                        newlyExpiredMatches.stream()
                                .forEach(match -> {
                                    match.setApplicationStatus(Match.ApplicationStatus.TIMED_OUT);

                                    matchDAO.save(match);
                                });
                    } else {
                        jobPosting.setNumDaysRemaining(numDaysRemaining - 1);
                    }

                    jobPostingDAO.save(jobPosting);
                });
    }

    /**
     * When a job is being reactivated, its number of days remaining will be reset back to its duration
     * and the status will be set as active.
     */
    public void resetExpiration(long jobPostingId) {
        JobPosting jobPosting = jobPostingDAO.findOne(jobPostingId);

        if (jobPosting != null) {
            jobPosting.setStatus(JobPosting.Status.ACTIVE.toInt());
            jobPosting.setNumDaysRemaining(jobPosting.getDuration());

            jobPostingDAO.save(jobPosting);
        }
    }
}