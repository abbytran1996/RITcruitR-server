package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.exceptions.ResourceNotFound;
import com.avalanche.tmcs.matching.Match;
import com.avalanche.tmcs.matching.MatchDAO;
import com.avalanche.tmcs.matching.MatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Defines and implements the API for interacting with Students
 *
 * @author ddubois
 * @since 05-Apr-17.
 */
@RestController
@RequestMapping("/students")
public class StudentsController {
    private static final Logger LOG = LoggerFactory.getLogger(StudentsController.class);

    private StudentDAO studentDAO;

    private MatchDAO matchDAO;

    private UserService userService;

    private SecurityService securityService;

    private MatchingService matchingService;

    @Autowired
    public StudentsController(StudentDAO studentDAO, MatchDAO matchDAO, UserService userService, SecurityService securityService, MatchingService matchingService) {
        this.studentDAO = studentDAO;
        this.matchDAO = matchDAO;
        this.userService = userService;
        this.securityService = securityService;
        this.matchingService = matchingService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        LOG.debug("Getting student with id " + id);
        validateStudentId(id);
        Student student = studentDAO.findOne(id);
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByName(@PathVariable String email) {
        LOG.debug("Getting student with email " + email);
        Student student = studentDAO.findByEmail(email);
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Student> addStudent(@RequestBody NewStudent newStudent) {
        User newUser = new User(newStudent.getEmail(), newStudent.getPassword(), newStudent.getPasswordConfirm());

        newUser = userService.save(newUser, Role.RoleName.Student);
        if(securityService.login(newUser.getUsername(), newUser.getPasswordConfirm())) {
            newStudent.setUser(newUser);
            newStudent.getPreferredStates().removeIf(str -> str.isEmpty() || str.matches("\\s+"));
            Student savedStudent = studentDAO.save(newStudent.toStudent());

            matchingService.registerStudent(savedStudent);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedStudent.getId())
                    .toUri();

            return ResponseEntity.created(location).body(savedStudent);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody Student updatedStudent) {
        validateStudentId(id);

        updatedStudent.setId(id);
        studentDAO.save(updatedStudent);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/matches", method = RequestMethod.GET)
    public List<Match> getMatchesForStudent(@PathVariable long id) {
        validateStudentId(id);

        Student student = studentDAO.findOne(id);
        if(student == null) {
            // 404
        }
        List<Match> matches =  matchDAO.findAllByStudent(student);
        return matches;
    }

    private void validateStudentId(long id) {
        if(!studentDAO.exists(id)) {
            throw new ResourceNotFound("student " + id);
        }
    }
}
