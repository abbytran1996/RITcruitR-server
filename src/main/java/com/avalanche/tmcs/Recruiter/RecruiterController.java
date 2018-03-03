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

    /**
     * @param newRecruiter: info for new recruiter
     * @return TODO
     */
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

    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public Recruiter getRecruiterByEmail(@PathVariable String email) {
        return recruiterRepo.findByEmail(email);
    }

    /**
     * TODO: When companies are included, add the controller actions here
     */

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


    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editCompany(@PathVariable long id, @RequestBody Company editCompany){
        editCompany.setId(id);
        companyDAO.save(editCompany);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRecruiter(@PathVariable long id, @RequestBody Recruiter updateRecruiter){
        updateRecruiter.setId(id);
        recruiterRepo.save(updateRecruiter);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable long id){
        //validateCompanyId(id);
        return companyDAO.findOne(id);
    }

    /*
    @RequestMapping(value = "/company/company_name/{company_name}", method = RequestMethod.GET)
    public Company getCompanyByName(@PathVariable String companyName) {
        return companyDAO.findByEmailSuffix(companyName);

    }
    */

    /**
     * TODO: Approve/decline company
     */

}
