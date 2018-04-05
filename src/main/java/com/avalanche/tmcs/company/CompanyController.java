package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.recruiter.NewRecruiter;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.recruiter.RecruiterController;
import com.avalanche.tmcs.recruiter.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
/**
 * @author Zane Grasso
 * @since 4/18/17
 */

    @RestController
    @RequestMapping("/company")
public class CompanyController {
    private CompanyDAO companyDAO;
    private RecruiterRepository recruiterRepo;
    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public CompanyController(RecruiterRepository repo, UserService userService, CompanyDAO companyDAO, SecurityService securityService){
        this.recruiterRepo = repo;
        this.userService = userService;
        this.companyDAO = companyDAO;
        this.securityService = securityService;
    }

    // ================================================================================================================
    // * GET COMPANY BY ID [GET]                                                                                      *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable long id){
        //validateCompanyId(id);
        return companyDAO.findOne(id);
    }

    // ================================================================================================================
    // * GET COMPANY BY NAME [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/company_name/{companyName}", method = RequestMethod.GET)
    public Company getCompanyByName(@PathVariable String companyName) {
        return companyDAO.findByCompanyName(companyName);
    }

    // ================================================================================================================
    // * ADD NEW COMPANY [POST]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "", method=RequestMethod.POST)
    public ResponseEntity<Company> addCompany(@RequestBody NewCompany newCompany) {
        Company savedCompany = companyDAO.save(newCompany.toCompany());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCompany.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCompany);
    }

    // ================================================================================================================
    // * UPDATE COMPANY [PUT] - **NOT WORKING**                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@PathVariable long id, @RequestBody Company updateCompany){
        updateCompany.setId(id);
        companyDAO.save(updateCompany);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * UPDATE COMPANY DETAILS [PUT] - **NOT WORKING**                                                               *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/details", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompanyDetails(@PathVariable long id, @RequestBody Company updateCompany){
        Company company = companyDAO.findOne(id);
        company.setCompanyName(updateCompany.getCompanyName());
        company.setIndustry(updateCompany.getIndustry());
        company.setSize(updateCompany.getSize());
        company.setWebsiteURL(updateCompany.getWebsiteURL());
        companyDAO.save(company);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * ADD COMPANY VIDEO [POST] - **NOT WORKING**                                                                   *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/videos", method = RequestMethod.POST)
    public ResponseEntity<?> addCompanyVideo(@PathVariable long id, @RequestBody Company updateCompany){
        Company company = companyDAO.findOne(id);
        company.setPresentation(updateCompany.getPresentation());
        companyDAO.save(company);
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE COMPANY VIDEO [DELETE] - **NOT WORKING**                                                              *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/videos/{videoid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyVideo(@PathVariable long id, @PathVariable long videoid) {
            //TODO: implement deletes
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * ADD COMPANY LOCATION [POST] - **NOT WORKING**                                                                *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/locations", method = RequestMethod.POST)
    public ResponseEntity<?> addCompanyLocations(@PathVariable long id, @RequestBody Company updateCompany) {
            Company company = companyDAO.findOne(id);
            company.setLocation(updateCompany.getLocation());
            companyDAO.save(company);
            return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE COMPANY LOCATION [DELETE] - **NOT WORKING**                                                           *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/locations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyLocation(@PathVariable long id, @PathVariable long locationid) {
        //TODO: implement deletes
        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * TODO: REMOVE THIS FUNCTION ONCE DETERMINED IT WON'T BREAK ANYTHING                                           *
    // ================================================================================================================
    @RequestMapping(value = "/email_suffix/{emailSuffix}", method = RequestMethod.GET)
    public Company getCompanyByEmailSuffix(@PathVariable String emailSuffix) {
        return companyDAO.findByEmailSuffix(emailSuffix);
    }

    // ================================================================================================================
    // * ADD RECRUITER [POST]                                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/recruiter", method = RequestMethod.POST)
    public ResponseEntity<Recruiter> addRecruiter(@PathVariable long id, @RequestBody NewRecruiter newRecruiter) {
        User newUser = new User(newRecruiter.getEmail(), newRecruiter.getPassword(), newRecruiter.getPasswordConfirm());
        newUser = userService.save(newUser, Role.RoleName.Recruiter);
        if(securityService.login(newUser.getUsername(), newUser.getPasswordConfirm())) {
            newRecruiter.setUser(newUser);
            Company company = companyDAO.findOne(id);
            newRecruiter.setCompany(company);

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
}
