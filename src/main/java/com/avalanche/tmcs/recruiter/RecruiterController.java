package com.avalanche.tmcs.recruiter;

import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.Role.RoleName;
import com.avalanche.tmcs.auth.RoleDAO;
import com.avalanche.tmcs.auth.SecurityService;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserDAO;
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
import java.util.Set;

/**
 * Created by John on 4/17/2017.
 */

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    private RecruiterRepository recruiterRepo;
    private UserService userService;
    private CompanyDAO companyDAO;
    private RoleDAO roleDAO;
    private SecurityService securityService;


    @Autowired
    public RecruiterController(RecruiterRepository repo, UserService userService, CompanyDAO companyDAO, RoleDAO roleDAO, SecurityService securityService){
        this.recruiterRepo = repo;
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
    // * UPDATE RECRUITER TO PRIMARY [PUT]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/primary", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRecruiterToPrimary(@PathVariable long id){
        Recruiter recruiter = recruiterRepo.findOne(id);
        Company recruiterCompany = recruiter.getCompany();
        List<Recruiter> companyRecruiters = recruiterRepo.findAllByCompany(recruiterCompany);
        String rolsss = Role.RoleName.PrimaryRecruiter.name().toLowerCase();
        System.out.println("***Rolename: " + rolsss);
        Role primaryRecruiterRole = roleDAO.findByName(rolsss);
        for (Recruiter r : companyRecruiters) {
        	Set<Role> recruiterRoles = r.getUser().getRoles();
        	if (recruiterRoles.contains(primaryRecruiterRole)) {
        		return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        	}
        }
        User user = recruiter.getUser();
        user = userService.addRole(user, RoleName.PrimaryRecruiter);
        recruiter.setUser(user);
        recruiterRepo.save(recruiter);
        return ResponseEntity.ok().build();
    }
    
    // ================================================================================================================
    // * REMOVE PRIMARY RECRUITER STATUS [DELETE]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/primary", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePrimaryRecruiter(@PathVariable long id){
        Recruiter recruiter = recruiterRepo.findOne(id);
        User user = recruiter.getUser();
        Role primaryRecruiterRole = roleDAO.findByName(Role.RoleName.PrimaryRecruiter.name().toLowerCase());
        user = userService.removeRole(user, RoleName.PrimaryRecruiter);
        recruiter.setUser(user);
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
