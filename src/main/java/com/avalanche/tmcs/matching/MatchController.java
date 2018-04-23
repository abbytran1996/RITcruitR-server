package com.avalanche.tmcs.matching;

        import com.avalanche.tmcs.job_posting.JobPosting;
        import com.avalanche.tmcs.job_posting.JobPostingDAO;
        import com.avalanche.tmcs.students.Student;
        import com.avalanche.tmcs.students.StudentDAO;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import com.avalanche.tmcs.auth.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
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
    private StudentDAO studentDAO;
    private UserService userService;
    private SecurityService securityServices;
    private MatchingService matchingService;

    @Autowired
    public MatchController(MatchDAO matchDAO, JobPostingDAO jobDAO, StudentDAO studentDAO, UserService userService, SecurityService securityService, MatchingService matchingService) {
        this.matchDAO = matchDAO;
        this.userService = userService;
        this.securityServices = securityService;
        this.jobDAO = jobDAO;
        this.studentDAO = studentDAO;
        this.matchingService = matchingService;
    }

    // ================================================================================================================
    // * GET STUDENT MATCHES [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/studentMatches/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getMatchesForStudent(@PathVariable long id, @RequestParam(value = "phase", defaultValue = "") String phase) {
        Student student = studentDAO.findOne(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        List<Match> matches;
        switch(phase) {
            case "problem":
                matches = matchDAO.findAllByStudentAndCurrentPhaseAndApplicationStatus(
                        student, Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT, Match.ApplicationStatus.IN_PROGRESS);
                break;
            case "presentation":
                matches = matchDAO.findAllByStudentAndCurrentPhaseAndApplicationStatus(
                        student, Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER, Match.ApplicationStatus.IN_PROGRESS);
                break;
            case "final":
                matches = matchDAO.findAllByStudentAndCurrentPhaseAndApplicationStatus(
                        student, Match.CurrentPhase.INTERVIEW, Match.ApplicationStatus.IN_PROGRESS);
                break;
            case "archived":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.ARCHIVED);
                break;
            default:
                matchingService.registerStudent(student);
                matches = matchDAO.findAllByStudentAndCurrentPhaseAndApplicationStatus(
                        student, Match.CurrentPhase.NONE, Match.ApplicationStatus.NEW);
        }
        return ResponseEntity.ok(matches);
    }

    // ================================================================================================================
    // * APPROVAL FOR STUDENTS [PATCH]                                                                                *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        // TODO: increment stage to next
        // increment NONE to PROBLEM
        if (match.getCurrentPhase() == Match.CurrentPhase.NONE) {
            
        } else {

        }

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DECLINE FOR STUDENTS [PATCH]                                                                                 *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/decline", method = RequestMethod.PATCH)
    public ResponseEntity<?> declineMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        match.setCurrentPhase(Match.CurrentPhase.NONE);

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * ARCHIVE FOR STUDENTS [PATCH]                                                                                 *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/archive", method = RequestMethod.PATCH)
    public ResponseEntity<?> archiveMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        // checks if the match isn't in the final stage
        if (match.getCurrentPhase() == Match.CurrentPhase.INTERVIEW) {
            return ResponseEntity.status(300).build();
        }

        match.setCurrentPhase(Match.CurrentPhase.ARCHIVED);

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE FOR STUDENTS [PATCH]                                                                                  *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PATCH)
    public ResponseEntity<?> deleteMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        // checks if the match isn't archived
        if (match.getCurrentPhase() == Match.CurrentPhase.ARCHIVED) {
            return ResponseEntity.status(300).build();
        }

        matchDAO.delete(id);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET RECRUITER MATCHES [GET]                                                                                  *
    // ================================================================================================================
    @RequestMapping(value = "/posting/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getRecruiterMatches(@PathVariable long id, @RequestParam(value = "phase", defaultValue = "") String phase) {
        JobPosting job = jobDAO.findOne(id);

        if (job == null) {
            return ResponseEntity.notFound().build();
        }

        List<Match> matches;

        if (phase.equals("problem")) {
            matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job, Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER,
                    Match.ApplicationStatus.IN_PROGRESS);
        } else if (phase.equals("presentation")) {
            matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job, Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER,
                    Match.ApplicationStatus.IN_PROGRESS);
        } else if (phase.equals("final")) {
            matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job, Match.CurrentPhase.INTERVIEW,
                    Match.ApplicationStatus.ACCEPTED);
        } else if (phase.equals("archived")) {
            matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.ARCHIVED);
        } else { // show a list of "unmatched" matches
            matches = matchDAO.findAllByJobAndCurrentPhaseAndApplicationStatus(job, Match.CurrentPhase.NONE,
                    Match.ApplicationStatus.NEW);
        }

        return ResponseEntity.ok(matches);
    }
}
