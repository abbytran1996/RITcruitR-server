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
        switch (phase) {
            case "problem":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
                break;
            case "presentation":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.PRESENTATION_WAITING_FOR_STUDENT);
                break;
            case "interview":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.INTERVIEW);
                break;
            case "final":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.FINAL);
                break;
            case "archived":
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.ARCHIVED);
                break;
            default:
                matchingService.registerStudent(student);
                matches = matchDAO.findAllByStudentAndCurrentPhase(student, Match.CurrentPhase.NONE);
        }

        return ResponseEntity.ok(matches);
    }

    // ================================================================================================================
    // * MATCH APPROVAL [PATCH]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/approve", method = RequestMethod.PATCH)
    public ResponseEntity<?> approveMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        switch (match.getCurrentPhase()) {
            case NONE:
                match.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_STUDENT);
                match.setApplicationStatus(Match.ApplicationStatus.IN_PROGRESS);
                break;
            case PROBLEM_WAITING_FOR_STUDENT:
                match.setCurrentPhase(Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER);
                break;
            case PROBLEM_WAITING_FOR_RECRUITER:
                match.setCurrentPhase(Match.CurrentPhase.PRESENTATION_WAITING_FOR_STUDENT);
                break;
            case PRESENTATION_WAITING_FOR_STUDENT:
                match.setCurrentPhase(Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER);
                break;
            case PRESENTATION_WAITING_FOR_RECRUITER:
                match.setCurrentPhase(Match.CurrentPhase.INTERVIEW);
                break;
            case INTERVIEW:
                match.setCurrentPhase(Match.CurrentPhase.FINAL);
                match.setApplicationStatus(Match.ApplicationStatus.ACCEPTED);
                break;
            case FINAL:
            default:
                return ResponseEntity.status(300).build();
        }

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * MATCH DECLINATION [PATCH]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/decline", method = RequestMethod.PATCH)
    public ResponseEntity<?> declineMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        match.setCurrentPhase(Match.CurrentPhase.NONE);
        match.setApplicationStatus(Match.ApplicationStatus.REJECTED);

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * MATCH ARCHIVAL [PATCH]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/archive", method = RequestMethod.PATCH)
    public ResponseEntity<?> archiveMatch(@PathVariable long id) {
        Match match = matchDAO.findOne(id);

        if (match == null){
            return ResponseEntity.notFound().build();
        }

        // checks if the match isn't in the final stage
        if (match.getCurrentPhase() == Match.CurrentPhase.FINAL) {
            return ResponseEntity.status(300).build();
        }

        match.setCurrentPhase(Match.CurrentPhase.ARCHIVED);

        match.setLastUpdatedTimeToNow();
        matchDAO.save(match);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * MATCH DELETION [PATCH]                                                                                       *
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
        switch(phase) {
            case "problem":
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER);
                break;
            case "presentation":
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.PRESENTATION_WAITING_FOR_RECRUITER);
                break;
            case "interview":
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.INTERVIEW);
                break;
            case "final":
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.FINAL);
                break;
            case "archived":
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.ARCHIVED);
                break;
            default:
                matches = matchDAO.findAllByJobAndCurrentPhase(job, Match.CurrentPhase.NONE);
        }

        return ResponseEntity.ok(matches);
    }
}
