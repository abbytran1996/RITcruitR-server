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
public class UniversityController {
    private UniversityDAO universityDAO;

    @Autowired
    public UniversityController(UniversityDAO universityDAO){
        this.universityDAO = universityDAO;
    }

    @RequestMapping(value = "/universities", method = RequestMethod.GET)
    public ResponseEntity<Iterable<University>> getUniversities() {
        return ResponseEntity.ok(universityDAO.findAll());
    }
}
