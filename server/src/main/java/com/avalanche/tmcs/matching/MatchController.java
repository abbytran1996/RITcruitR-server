package com.avalanche.tmcs.matching;

        import com.avalanche.tmcs.job_posting.JobPosting;
        import com.avalanche.tmcs.job_posting.JobPostingDAO;
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
        Long numInProbPhase = (matchDAO.findAllByJob(job).stream().filter(p->p.getCurrentPhase()== Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER)).count();

        return ResponseEntity.ok(numInProbPhase);
    }

    @RequestMapping(value = "/{jobPostingID}/problemResponsePending", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getMatchesWithProblemResponsePending(@PathVariable long jobPostingID){
        JobPosting job = new JobPosting();
        job.setId(jobPostingID);
        List<Match> matches = matchDAO.findAllByJobAndCurrentPhase(job,
                Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER);

        return ResponseEntity.ok(matches);
    }
}
