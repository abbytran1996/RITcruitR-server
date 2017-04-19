package com.avalanche.tmcs.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Iterable<Skill> getSkills() {
        return skillDAO.findAll();
    }
}
