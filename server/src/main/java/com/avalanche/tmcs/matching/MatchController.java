package com.avalanche.tmcs.matching;

        import com.avalanche.tmcs.job_posting.JobPosting;
        import com.avalanche.tmcs.job_posting.JobPostingDAO;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import com.avalanche.tmcs.auth.*;
        import org.springframework.beans.factory.annotation.Autowired;
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
            match.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
        }
        else{
            match.setApplicationStatus(Match.ApplicationStatus.REJECTED);
        }
        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/posting/{id}/probphase", method=RequestMethod.GET)
    public ResponseEntity<Long> getProbPhaseMatches(@PathVariable long id){
        JobPosting job = jobDAO.findOne(id);
        if(job ==null){
            return ResponseEntity.notFound().build();
        }
        long matchesForJob = matchDAO.countAllByJobAndCurrentPhaseAndApplicationStatus(job,Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matchesForJob);
    }
    @RequestMapping(value = "/posting/{id}/presentationphase", method=RequestMethod.GET)
    public ResponseEntity<Long> getPresentationPhaseMatches(@PathVariable long id){
        JobPosting job = jobDAO.findOne(id);
        if(job ==null){
            return ResponseEntity.notFound().build();
        }
        long matchesForJob = matchDAO.countAllByJobAndCurrentPhaseAndApplicationStatus(job,Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matchesForJob);
    }

    @RequestMapping(value = "/{jobPostingID}/problemResponsePending", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getMatchesWithProblemResponsePending(@PathVariable long jobPostingID){
        JobPosting job = jobDAO.findOne(jobPostingID);
        List<Match> matches = matchDAO.readAllByJobAndCurrentPhaseAndApplicationStatus(job,
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

        List<Match> matches = matchDAO.readAllByJobAndCurrentPhaseAndApplicationStatus(job,
                Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matches);
    }

    @RequestMapping(value="/{jobPostingID}/interviewPhaseMatches/Count", method= RequestMethod.GET)
    public ResponseEntity<Long> getInterviewPhaseMatchesCount(@PathVariable long jobPostingID){
        JobPosting job = jobDAO.findOne(jobPostingID);
        long matches = matchDAO.countAllByJobAndCurrentPhaseAndApplicationStatus(job,
                Match.CurrentPhase.INTERVIEW,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matches);
    }

    @RequestMapping(value="/{jobPostingID}/interviewPhaseMatches", method= RequestMethod.GET)
    public ResponseEntity<List<Match>> getInterviewPhaseMatches(@PathVariable long jobPostingID){
        JobPosting job = jobDAO.findOne(jobPostingID);
        List<Match> matches = matchDAO.readAllByJobAndCurrentPhaseAndApplicationStatus(job,
                Match.CurrentPhase.INTERVIEW,
                Match.ApplicationStatus.IN_PROGRESS);

        return ResponseEntity.ok(matches);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateMatch(@PathVariable long id, @RequestBody Match match){
        if(id == match.getId()){
            match.setLastUpdatedTimeToNow();
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
        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "{id}/link/{link}", method = RequestMethod.POST)
    public ResponseEntity<?> setStudentLink(@PathVariable long id, @PathVariable String link) {
        Match match = matchDAO.findOne(id);
        if(match == null) {
            return ResponseEntity.notFound().build();
        }

        match.setStudentPresentationLink(link);
        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }
}
