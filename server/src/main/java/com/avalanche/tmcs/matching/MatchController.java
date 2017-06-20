package com.avalanche.tmcs.matching;

        import com.avalanche.tmcs.job_posting.JobPosting;
        import com.avalanche.tmcs.job_posting.JobPostingDAO;
        import com.sun.org.apache.regexp.internal.RE;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import com.avalanche.tmcs.auth.*;
        import com.avalanche.tmcs.exceptions.ResourceNotFound;
        import com.avalanche.tmcs.matching.Match;
        import com.avalanche.tmcs.matching.MatchDAO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

/**
 * Allows you to easily interact with matches
 *
 * @author ddubois
 * @since 6/6/17.
 */
@RestController
@RequestMapping("/matches")
public class MatchController {
    private MatchDAO matchDAO;
    private JobPostingDAO jobDAO;
    private UserService userService;
    private SecurityService securityServices;

    @Autowired
    public MatchController(MatchDAO matchDAO, JobPostingDAO jobDao, UserService userService, SecurityService securityService) {
        this.matchDAO = matchDAO;
        this.userService = userService;
        this.securityServices = securityService;
        this.jobDAO = jobDao;
    }

    @RequestMapping(value = "/{id}/accept", method = RequestMethod.POST)
    public ResponseEntity<?> showInterest(@PathVariable long id, @RequestBody boolean acceptthis) {
        Match match = matchDAO.findOne(id);
        if(match ==null){
            return ResponseEntity.notFound().build();
        }
        if(acceptthis){
            match.setApplicationStatus(Match.ApplicationStatus.IN_PROGRESS);
        }
        else{
            match.setApplicationStatus(Match.ApplicationStatus.REJECTED);
        }
        matchDAO.save(match);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/posting/{id}/probphase", method=RequestMethod.GET)
    public ResponseEntity<Long> getProbPhaseMatches(@PathVariable long id){
        JobPosting job = jobDAO.findOne(id);
        if(job ==null){
            return ResponseEntity.notFound().build();
        }
        List<Match> matchesForJob = matchDAO.findAllByJob(job);
        Long numInProbPhase = matchesForJob.stream().filter(p->p.getCurrentPhase()== Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER).count();

        return ResponseEntity.ok(numInProbPhase);
    }

    @RequestMapping(value = "/{jobPostingID}/problemResponsePending", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getMatchesWithProblemResponsePending(@PathVariable long jobPostingID){
        JobPosting job = jobDAO.findOne(jobPostingID);
        List<Match> matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job,
                Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matches);
    }

    @RequestMapping(value = "/{jobPostingId}/presentationResponsePending", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getMatchesWithPresentationResponsePending(@PathVariable long jobPostingId) {
        JobPosting job = jobDAO.findOne(jobPostingId);
        if(job == null) {
            return ResponseEntity.notFound().build();
        }

        List<Match> matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job,
                Match.CurrentPhase.PRESENTATION,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matches);
    }

    @RequestMapping(value="/{jobPostingID}/interviewPhaseMatchesCount", method= RequestMethod.GET)
    public ResponseEntity<Long> getInterviewPhaseMatchesCount(@PathVariable long jobPostingID){
        return ResponseEntity.ok(
                 matchDAO.countAllByJobAndCurrentPhaseAndApplicationStatus(jobDAO.findOne(jobPostingID),
                        Match.CurrentPhase.INTERVIEW,
                        Match.ApplicationStatus.IN_PROGRESS));
    }

    @RequestMapping(value="/{jobPostingID}/interviewPhaseMatches", method= RequestMethod.GET)
    public ResponseEntity<List<Match>> getInterviewPhaseMatches(@PathVariable long jobPostingID){
        return ResponseEntity.ok(
                matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(jobDAO.findOne(jobPostingID),
                        Match.CurrentPhase.INTERVIEW,
                        Match.ApplicationStatus.IN_PROGRESS));
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateMatch(@PathVariable long id, @RequestBody Match match){
        if(id == match.getId()){
            matchDAO.save(match);
        }

        return ResponseEntity.ok(true); //TODO make the result variable
    }

    @RequestMapping(value = "{id}/response/{response}", method = RequestMethod.POST)
    public ResponseEntity<?> setProblemResponse(@PathVariable long id, @PathVariable String response) {
        Match match = matchDAO.findOne(id);
        if(match == null) {
            return ResponseEntity.notFound().build();
        }

        match.setStudentProblemResponse(response);
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }
}
