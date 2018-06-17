package com.avalanche.tmcs.company;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.matching.PresentationLink;
import com.avalanche.tmcs.matching.PresentationLinkDAO;
import com.avalanche.tmcs.recruiter.NewRecruiter;
import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.recruiter.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * @author Zane Grasso
 * @since 4/18/17
 */

@RestController
@RequestMapping("/company")
public class CompanyController {
    private CompanyDAO companyDAO;
    private PresentationLinkDAO presentationLinkDAO;
    private RecruiterRepository recruiterRepo;
    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public CompanyController(RecruiterRepository repo, UserService userService, CompanyDAO companyDAO, PresentationLinkDAO presentationLinkDAO, SecurityService securityService){
        this.recruiterRepo = repo;
        this.userService = userService;
        this.companyDAO = companyDAO;
        this.presentationLinkDAO = presentationLinkDAO;
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
    // * GET COMPANY BY APPROVAL STATUS [GET]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public List<Company> getCompanyByStatus(@PathVariable boolean status) {
        return companyDAO.findByApprovalStatus(status);
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
    // * UPDATE COMPANY [PUT]                                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@PathVariable long id, @RequestBody Company updateCompany){
        Company company = companyDAO.findOne(id);
        company.setCompanyName(updateCompany.getCompanyName());
        company.setLocations(updateCompany.getLocations());
        company.setSize(updateCompany.getSize());
        company.setIndustries(updateCompany.getIndustries());
        company.setApprovalStatus(updateCompany.getApprovalStatus());
        company.setCompanyDescription(updateCompany.getCompanyDescription());
        company.setWebsiteURL(updateCompany.getWebsiteURL());
        companyDAO.save(company);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET COMPANY PRESENTATION LINKS [GET]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/links", method = RequestMethod.GET)
    public Set<PresentationLink> getCompanyPresentationLinks(@PathVariable long id){
        return companyDAO.findOne(id).getPresentationLinks();
    }

    // ================================================================================================================
    // * ADD COMPANY PRESENTATION LINK [POST]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/links", method = RequestMethod.POST)
    public ResponseEntity<PresentationLink> addCompanyPresentationLink(@PathVariable long id, @RequestBody PresentationLink presentationLink) {
        PresentationLink newLink = presentationLinkDAO.save(presentationLink);
        Company company = companyDAO.findOne(id);

        Set<PresentationLink> companyLinks = company.getPresentationLinks();
        companyLinks.add(newLink);
        company.setPresentationLinks(companyLinks);
        companyDAO.save(company);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLink.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLink);
    }

    // ================================================================================================================
    // * UPDATE COMPANY PRESENTATION LINK [PUT]                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/links/{linkId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompanyPresentationLink(@PathVariable long id, @PathVariable long linkId, @RequestBody PresentationLink presentationLink) {
        presentationLink.setId(linkId);
        presentationLinkDAO.save(presentationLink);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE COMPANY PRESENTATION LINK [DELETE]                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/links/{linkId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompanyPresentationLink(@PathVariable long id, @PathVariable long linkId) {
        PresentationLink findLink = presentationLinkDAO.findOne(linkId);
        Company company = companyDAO.findOne(id);

        Set<PresentationLink> links = company.getPresentationLinks();
        links.remove(findLink);
        companyDAO.save(company);
        presentationLinkDAO.delete(linkId);

        return ResponseEntity.ok().build();
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
