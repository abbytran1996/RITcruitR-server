package com.avalanche.tmcs.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

/**
 * @author Maxwell Hadley
 * @since 4/18/17.
 */
@RestController
public class SkillController {
    private SkillDAO skillDAO;

    @Autowired
    public SkillController(SkillDAO skillDAO){
        this.skillDAO = skillDAO;
    }

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Skill>> getSkills() {
        return ResponseEntity.ok(skillDAO.findAll());
    }
}
