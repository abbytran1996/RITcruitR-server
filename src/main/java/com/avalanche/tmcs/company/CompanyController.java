package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.recruiter.NewRecruiter;
import com.avalanche.tmcs.recruiter.RecruiterController;
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

        @Autowired
        public CompanyController(CompanyDAO companyDAO){
            this.companyDAO = companyDAO;
        }


    @RequestMapping(value = "", method=RequestMethod.POST)
    public void addCompany(@RequestBody Company newCompany) {
        companyDAO.save(newCompany);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable long id){
        //validateCompanyId(id);
        return companyDAO.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@PathVariable long id, @RequestBody Company updateCompany){
        updateCompany.setId(id);
        companyDAO.save(updateCompany);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompanyDetails(@PathVariable long id, @RequestBody Company updateCompany){
        Company company = companyDAO.findOne(id);
        company.setCompanyName(updateCompany.getCompanyName());
        //TODO: Add industry to Company and update here
        company.setSize(updateCompany.getSize());
        company.setWebsiteURL(updateCompany.getWebsiteURL());
        companyDAO.save(company);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{id}/videos", method = RequestMethod.POST)
    public ResponseEntity<?> addCompanyVideo(@PathVariable long id, @RequestBody Company updateCompany){
        Company company = companyDAO.findOne(id);
        company.setPresentation(updateCompany.getPresentation());
        companyDAO.save(company);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/videos/{videoid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyVideo(@PathVariable long id, @PathVariable long videoid) {
            //TODO: implement deletes
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/locations", method = RequestMethod.POST)
    public ResponseEntity<?> addCompanyLocations(@PathVariable long id, @RequestBody Company updateCompany) {
            Company company = companyDAO.findOne(id);
            company.setLocation(updateCompany.getLocation());
            companyDAO.save(company);
            return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/locations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyLocation(@PathVariable long id, @PathVariable long locationid) {
        //TODO: implement deletes
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/email_suffix/{emailSuffix}", method = RequestMethod.GET)
    public Company getCompanyByEmailSuffix(@PathVariable String emailSuffix) {
        return companyDAO.findByEmailSuffix(emailSuffix);
    }

    @RequestMapping(value = "/company_name/{companyName}", method = RequestMethod.GET)
    public Company getCompanyByName(@PathVariable String companyName) {
        return companyDAO.findByCompanyName(companyName);
    }

}
