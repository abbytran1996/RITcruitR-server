package com.avalanche.tmcs.Recruiter;

import com.avalanche.tmcs.students.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by John on 4/17/2017.
 */

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    private RecruiterRepository recruiterRepo;

    @Autowired
    public RecruiterController(RecruiterRepository repo){
        this.recruiterRepo = repo;
    }

    /**
     * @param newRecruiter: info for new recruiter
     * @return TODO
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<String> registerRecruiter(@RequestBody Recruiter newRecruiter){
        Recruiter newguy = new Recruiter(newRecruiter);
        recruiterRepo.save(newguy);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * TODO: Authorization
     * @param newInfo: new info for a recruiter, non-null values replace the old ones
     * @return TODO
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ResponseEntity<String> editRecruiter(@RequestBody Recruiter newInfo){
        Recruiter oldGuy = recruiterRepo.findOne(newInfo.getId());
        oldGuy.editRecruiter(newInfo);
        recruiterRepo.save(oldGuy);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * TODO: Authorization
     * @param id: id of recruiter to find
     * @return Recruiter object for the found recruiter
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public Recruiter getEmployer(@PathVariable long id) {
        return recruiterRepo.findOne(id);
    }


    /**
     * TODO: When companies are included, add the controller actions here
     */
    /**
     * TODO: Create company
     */

    /**
     * TODO: Edit company
     */

    /**
     * TODO: Get company
     */

    /**
     * TODO: Approve/decline company
     */

}
