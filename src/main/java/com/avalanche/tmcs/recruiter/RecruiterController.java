package com.avalanche.tmcs.recruiter;

import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.SecurityService;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by John on 4/17/2017.
 */

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    private RecruiterRepository recruiterRepo;
    private UserService userService;
    private CompanyDAO companyDAO;
    private SecurityService securityService;


    @Autowired
    public RecruiterController(RecruiterRepository repo, UserService userService, CompanyDAO companyDAO, SecurityService securityService){
        this.recruiterRepo = repo;
        this.userService = userService;
        this.companyDAO = companyDAO;
        this.securityService = securityService;
    }

    // ================================================================================================================
    // * GET RECRUITER BY ID [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public Recruiter getEmployer(@PathVariable long id) {
        return recruiterRepo.findOne(id);
    }

    // ================================================================================================================
    // * GET RECRUITER BY EMAIL [GET]                                                                                 *
    // ================================================================================================================
    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public Recruiter getRecruiterByEmail(@PathVariable String email) {
        return recruiterRepo.findByEmail(email);
    }

    // ================================================================================================================
    // * UPDATE RECRUITER [PUT]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRecruiter(@PathVariable long id, @RequestBody Recruiter updatedRecruiter){
        Recruiter recruiter = recruiterRepo.findOne(id);
        recruiter.setFirstName(updatedRecruiter.getFirstName());
        recruiter.setLastName(updatedRecruiter.getLastName());
        recruiter.setPhoneNumber(updatedRecruiter.getPhoneNumber());
        recruiter.setContactEmail(updatedRecruiter.getContactEmail());
        recruiterRepo.save(recruiter);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE RECRUITER [DELETE] - **NOT WORKING**                                                                  *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRecruiter(@PathVariable long id) {
        Recruiter recruiter = recruiterRepo.findOne(id);
        recruiterRepo.delete(recruiter);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET RECRUITERS BY COMPANY [GET]                                                                              *
    // ================================================================================================================
    @RequestMapping(value = "/company/{company_id}", method=RequestMethod.GET)
    public ResponseEntity<List<Recruiter>> getRecruitersByCompany(@PathVariable long company_id){
        Company companyWithID = new Company();
        companyWithID.setId(company_id);

        List<Recruiter> recruiters = recruiterRepo.findAllByCompany(companyWithID);

        return ResponseEntity.ok(recruiters);
    }
}
