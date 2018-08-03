package com.avalanche.tmcs.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nick Krzysiak
 * @since 7/30/18
 */
@RestController
public class IndustryController {
    private IndustryDAO industryDAO;

    @Autowired
    public IndustryController(IndustryDAO industryDAO){
        this.industryDAO = industryDAO;
    }

    @RequestMapping(value = "/industries", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Industry>> getIndustries() {
        return ResponseEntity.ok(industryDAO.findAll());
    }
}
