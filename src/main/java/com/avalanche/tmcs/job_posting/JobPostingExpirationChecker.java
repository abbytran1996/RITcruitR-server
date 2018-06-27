package com.avalanche.tmcs.job_posting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    @Scheduled(cron = "0 * 17 * * *")
    public void automateJobExpiration() {
        LOGGER.info("Checking whether if jobs have expired or not...");

        List<JobPosting> allActiveJobPostings = jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE.toInt());
        allActiveJobPostings.stream()
                .filter(jobPosting -> {
                    LocalDate currentDate = LocalDate.now();
                    Date jobPostingDate = new Date();
                    LocalDate jobPostingDate2 = jobPostingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    long jobPostingDuration = jobPosting.getDuration();

                    Duration duration = Duration.between(currentDate, jobPostingDate2);
                    long difference = Math.abs(duration.toDays());

                    return difference >= jobPostingDuration;
                })
                .forEach(jobPosting -> {
                    System.out.println(jobPosting.getId());
                });
    }
}