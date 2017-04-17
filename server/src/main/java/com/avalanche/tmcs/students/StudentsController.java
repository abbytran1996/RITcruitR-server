package com.avalanche.tmcs.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private StudentDAO studentDAO;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudent(@PathVariable long id) {
        validateStudentId(id);
        StudentModel student = studentDAO.findOne(id);
        return new Student(student);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> addStudent(@RequestBody NewStudent newStudent) {
        // TODO: Create a new user account
        StudentModel studentModel = new StudentModel(newStudent);
        StudentModel savedStudent = studentDAO.save(studentModel);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody Student updatedStudent) {
        validateStudentId(id);

        StudentModel studentModel = new StudentModel(updatedStudent);

        studentModel.setId(id);
        studentDAO.save(studentModel);

        return ResponseEntity.ok().build();
    }

    private void validateStudentId(long id) {
        if(!studentDAO.exists(id)) {
            throw new StudentNotFoundException(id);
        }
    }
}
