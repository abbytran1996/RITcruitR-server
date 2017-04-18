package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.*;
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

    private RoleDAO roleDAO;

    @Autowired
    public StudentsController(StudentDAO studentDAO, UserService userService, SecurityService securityService, RoleDAO roleDAO) {
        this.studentDAO = studentDAO;
        this.userService = userService;
        this.securityService = securityService;
        this.roleDAO = roleDAO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudent(@PathVariable long id) {
        validateStudentId(id);
        return studentDAO.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> addStudent(@RequestBody NewStudent newStudent) {
        Role studentRole = roleDAO.findByName("student");
        User newUser = new User(newStudent.getEmail(), newStudent.getPassword(), newStudent.getPasswordConfirm(), studentRole);

        userService.save(newUser);
        if(securityService.login(newUser.getUsername(), newUser.getPasswordConfirm())) {
            newStudent.setUser(newUser);
            Student savedStudent = studentDAO.save(newStudent.toStudent());

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
            throw new StudentNotFoundException(id);
        }
    }
}
