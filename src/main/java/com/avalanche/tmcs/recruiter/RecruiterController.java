package com.avalanche.tmcs.recruiter;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.auth.Role.RoleName;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by John on 4/17/2017.
 */

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    private RecruiterDAO recruiterRepo;
    private UserService userService;
    private CompanyDAO companyDAO;
    private RoleDAO roleDAO;
    private SecurityService securityService;


    @Autowired
    public RecruiterController(RecruiterDAO recruiterDAO, UserService userService, CompanyDAO companyDAO, RoleDAO roleDAO, SecurityService securityService){
        this.recruiterRepo = recruiterDAO;
        this.userService = userService;
        this.companyDAO = companyDAO;
        this.roleDAO = roleDAO;
        this.securityService = securityService;
    }

    // ================================================================================================================
    // * GET RECRUITER BY ID [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Recruiter> getEmployer(@PathVariable long id) {
        Recruiter recruiter = recruiterRepo.findOne(id);

        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recruiter);
    }

    // ================================================================================================================
    // * GET RECRUITER BY EMAIL [GET]                                                                                 *
    // ================================================================================================================
    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<Recruiter> getRecruiterByEmail(@PathVariable String email) {
        Recruiter recruiter = recruiterRepo.findByEmail(email);

        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recruiter);
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
    // * UPDATE RECRUITER TO PRIMARY [PUT]                                                                            *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/primary", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateRecruiterToPrimary(@PathVariable long id, @RequestBody User currentUser){
        if (!currentUser.getRoles().contains(new Role(Role.RoleName.PrimaryRecruiter.name().toLowerCase()))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Recruiter requestedUser = recruiterRepo.findByEmail(currentUser.getUsername());
        Recruiter recruiter = recruiterRepo.findOne(id);
        if (requestedUser.getCompany().getId() != recruiter.getCompany().getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = recruiter.getUser();
        user = userService.removeRole(user, RoleName.Recruiter);
        user = userService.addRole(user, RoleName.PrimaryRecruiter);
        recruiter.setUser(user);
        recruiterRepo.save(recruiter);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * REMOVE PRIMARY RECRUITER STATUS [DELETE]                                                                     *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/primary", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePrimaryRecruiter(@PathVariable long id){
        Recruiter recruiter = recruiterRepo.findOne(id);
        User user = recruiter.getUser();
        Role primaryRecruiterRole = roleDAO.findByName(Role.RoleName.PrimaryRecruiter.name().toLowerCase());
        user = userService.removeRole(user, RoleName.PrimaryRecruiter);
        user = userService.addRole(user, RoleName.Recruiter);
        recruiter.setUser(user);
        recruiterRepo.save(recruiter);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE RECRUITER [DELETE]                                                              *
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
