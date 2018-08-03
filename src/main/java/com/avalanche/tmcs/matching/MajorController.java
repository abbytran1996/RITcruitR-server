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
public class MajorController {
    private MajorDAO majorDAO;

    @Autowired
    public MajorController(MajorDAO majorDAO){
        this.majorDAO = majorDAO;
    }

    @RequestMapping(value = "/majors", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Major>> getMajors() {
        return ResponseEntity.ok(majorDAO.findAll());
    }
}
