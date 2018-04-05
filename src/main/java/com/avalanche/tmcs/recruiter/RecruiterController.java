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
    // * UPDATE RECRUITER [PUT] - **NOT WORKING**                                                                     *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRecruiter(@PathVariable long id, @RequestBody Recruiter updateRecruiter){
        updateRecruiter.setId(id);
        recruiterRepo.save(updateRecruiter);

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
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING (MOVED TO COMPANY CONTROLLER)             *
    // ================================================================================================================
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Recruiter> addRecruiter(@RequestBody NewRecruiter newRecruiter) {
        User newUser = new User(newRecruiter.getEmail(), newRecruiter.getPassword(), newRecruiter.getPasswordConfirm());
        newUser = userService.save(newUser, Role.RoleName.Recruiter);
        if(securityService.login(newUser.getUsername(), newUser.getPasswordConfirm())) {
            newRecruiter.setUser(newUser);
            Recruiter savedRecruiter = recruiterRepo.save(newRecruiter.toRecruiter());


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRecruiter.getId())
                    .toUri();

            return ResponseEntity.created(location).body(savedRecruiter);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // ================================================================================================================
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING                                           *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    public ResponseEntity<String> editRecruiter(@RequestBody Recruiter newInfo){
        Recruiter oldGuy = recruiterRepo.findOne(newInfo.getId());
        oldGuy.editRecruiter(newInfo);
        recruiterRepo.save(oldGuy);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    // ================================================================================================================
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING                                           *
    // ================================================================================================================
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public ResponseEntity<?> addCompany(@PathVariable long id, @RequestBody Company newCompany){
        Company savedCompany = companyDAO.save(newCompany);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("company/{id}")
                .buildAndExpand(savedCompany.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // ================================================================================================================
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING                                           *
    // ================================================================================================================
    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editCompany(@PathVariable long id, @RequestBody Company editCompany){
        editCompany.setId(id);
        companyDAO.save(editCompany);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING                                           *
    // ================================================================================================================
    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable long id){
        //validateCompanyId(id);
        return companyDAO.findOne(id);
    }
}
