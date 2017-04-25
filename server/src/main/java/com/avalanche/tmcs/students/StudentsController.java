package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.exceptions.ResourceNotFound;
import com.avalanche.tmcs.matching.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Defines and implements the API for interacting with Students
 *
 * @author ddubois
 * @since 05-Apr-17.
 */
@RestController
@RequestMapping("/students")
public class StudentsController {
    private StudentDAO studentDAO;

    private UserService userService;

    private SecurityService securityService;

    private MatchingService matchingService;

    @Autowired
    public StudentsController(StudentDAO studentDAO, UserService userService, SecurityService securityService, MatchingService matchingService) {
        this.studentDAO = studentDAO;
        this.userService = userService;
        this.securityService = securityService;
        this.matchingService = matchingService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudent(@PathVariable long id) {
        validateStudentId(id);
        return studentDAO.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> addStudent(@RequestBody NewStudent newStudent) {
        User newUser = new User(newStudent.getEmail(), newStudent.getPassword(), newStudent.getPasswordConfirm());

        userService.save(newUser, UserService.RoleName.Student);
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

            return ResponseEntity.created(location).build();
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

    private void validateStudentId(long id) {
        if(!studentDAO.exists(id)) {
            throw new ResourceNotFound("student " + id);
        }
    }
}
