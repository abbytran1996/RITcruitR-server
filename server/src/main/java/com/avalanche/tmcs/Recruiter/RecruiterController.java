package com.avalanche.tmcs.Recruiter;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.company.NewCompany;
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
    private UserService userService;
    private CompanyDAO companyDAO;

    @Autowired
    public RecruiterController(RecruiterRepository repo, UserService userService, CompanyDAO companyDAO){
        this.recruiterRepo = repo;
        this.userService = userService;
        this.companyDAO = companyDAO;
    }

    /**
     * @param newRecruiter: info for new recruiter
     * @return TODO
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<String> registerRecruiter(@RequestBody NewRecruiter newRecruiter){
        User newUser = new User(newRecruiter.eMail,newRecruiter.password);
        Recruiter newguy = new Recruiter(newRecruiter);
        userService.save(newUser, UserService.RoleName.Recruiter);
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

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ResponseEntity<?> addCompany(@PathVariable long id, @RequestBody NewCompany newCompany){
        Company newcomp = new Company();
        companyDAO.save(newcomp);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editCompany(@PathVariable long id, @RequestBody Company editCompany){
        editCompany.setId(id);
        companyDAO.save(editCompany);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable long id){
        //validateCompanyId(id);
        return companyDAO.findOne(id);
    }

    /**
     * TODO: Approve/decline company
     */

}
