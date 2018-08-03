package com.avalanche.tmcs.data;

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
@RequestMapping("/data")
public class DataController {
    private IndustryDAO industryDAO;
    private LocationDAO locationDAO;
    private MajorDAO majorDAO;
    private SkillDAO skillDAO;
    private UniversityDAO universityDAO;


    @Autowired
    public DataController(IndustryDAO industryDAO, LocationDAO locationDAO, MajorDAO majorDAO, SkillDAO skillDAO, UniversityDAO universityDAO){
        this.industryDAO = industryDAO;
        this.locationDAO = locationDAO;
        this.majorDAO = majorDAO;
        this.skillDAO = skillDAO;
        this.universityDAO = universityDAO;
    }

    @RequestMapping(value = "/industries", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Industry>> getIndustries() {
        return ResponseEntity.ok(industryDAO.findAllOrOrderByCount());
    }

    @RequestMapping(value = "/majors", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Major>> getMajors() {
        return ResponseEntity.ok(majorDAO.findAllOrOrderByCount());
    }

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Skill>> getSkills() {
        return ResponseEntity.ok(skillDAO.findAllOrOrderByCount());
    }

    @RequestMapping(value = "/universities", method = RequestMethod.GET)
    public ResponseEntity<Iterable<University>> getUniversities() {
        return ResponseEntity.ok(universityDAO.findAllOrOrderByCount());
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Location>> getLocations() {
        return ResponseEntity.ok(locationDAO.findAllOrOrderByCount());
    }

}
