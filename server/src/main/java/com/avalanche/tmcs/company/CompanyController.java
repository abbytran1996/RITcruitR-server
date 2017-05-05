package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.*;
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

    /*private void validateCompanyId(long id) {
            if(!companyDAO.exists(id)){
                throw ExceptionHandler("");
            }
    }
    */

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

    @RequestMapping(value = "/email_suffix/{emailSuffix}", method = RequestMethod.GET)
    public Company getCompanyByEmailSuffix(@PathVariable String emailSuffix) {
        return companyDAO.findByEmailSuffix(emailSuffix);
    }
    @RequestMapping(value = "/company_name/{companyName}", method = RequestMethod.GET)
    public Company getCompanyByName(@PathVariable String companyName) {
        return companyDAO.findByCompanyName(companyName);
    }

}
