package com.avalanche.tmcs.data;

import com.avalanche.tmcs.job_posting.NewJobPosting;
import com.avalanche.tmcs.students.NewStudent;
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

    public void updateUsageScoreData(NewJobPosting newJobPosting){
        for (Skill skill : newJobPosting.getRequiredSkills()) {
            skill.setUsageScore(skill.getUsageScore() + 2);
            skillDAO.save(skill);
        }

        for (Skill skill : newJobPosting.getRecommendedSkills()) {
            skill.setUsageScore(skill.getUsageScore() + 1);
            skillDAO.save(skill);
        }

        for (String locationStr: newJobPosting.getLocations()){
            Location location = locationDAO.findFirstByName(locationStr);
            location.setUsageScore(location.getUsageScore() + 1);
            locationDAO.save(location);
        }
    }

    public void updateUsageScoreData(NewStudent newStudent){
        for(Skill skill : newStudent.getSkills()){
            skill.setUsageScore(skill.getUsageScore() + 1);
            skillDAO.save(skill);
        }

        for(String locationStr: newStudent.getPreferredLocations()){
            Location location = locationDAO.findFirstByName(locationStr);
            location.setUsageScore(location.getUsageScore() + 1);
            locationDAO.save(location);
        }

        for(Industry industry : newStudent.getPreferredIndustries()){
            industry.setUsageScore(industry.getUsageScore() + 1);
            industryDAO.save(industry);
        }

        Major major = newStudent.getMajor();
        major.setUsageScore(major.getUsageScore() + 1);
        majorDAO.save(major);

        University university = newStudent.getSchool();
        university.setUsageScore(university.getUsageScore() + 1);
        universityDAO.save(university);
    }

}
