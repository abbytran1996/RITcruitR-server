package com.avalanche.tmcs.matching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

/**
 * A Runnable to check all the Matches in the database and see if any haven't been updated recently
 *
 * @author ddubois
 * @since 04-Jun-17
 */
@Component
public class MatchExpirationChecker {
    private static final long HOURS_TO_MILLISECONDS = 1000 * 60 * 60;
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchExpirationChecker.class);

    private final MatchDAO matchDAO;

    /**
     * Creates a new MatchExpirationChecker that checks all the Matches in the provided MatchDAO every hour
     *
     * @param matchDAO The MatchDAO to access Matches through
     */
    public MatchExpirationChecker(final MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    /**
     * Checks every hour if any of the matches in the database haven't been updated recently
     */
    @Scheduled(fixedDelay = HOURS_TO_MILLISECONDS)
    public void checkForExpiredMatches() {
        LOGGER.info("Doing the thing");
        List<Match> allMatches = matchDAO.findAllByApplicationStatus(Match.ApplicationStatus.IN_PROGRESS);
        allMatches.stream()
                .filter(match -> {
                    // Assume the phase timeout is hours
                    long matchUpdateTime = match.getJob().getPhaseTimeout() * 24 * HOURS_TO_MILLISECONDS;
                    Date updateDate = new Date(new java.util.Date().getTime() + matchUpdateTime);
                    return match.getTimeLastUpdated().compareTo(updateDate) < 0;
                })
                .forEach(match -> {
                    match.setApplicationStatus(Match.ApplicationStatus.TIMED_OUT);

                    // TODO: Update the student that their match timed out
                    matchDAO.save(match);
                 });
    }
}
