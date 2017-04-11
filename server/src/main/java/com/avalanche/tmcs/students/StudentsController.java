package com.avalanche.tmcs.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @author ddubois
 * @since 05-Apr-17.
 */
@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    private StudentDAO studentDAO;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StudentModel getStudent(@PathVariable long id) {
        validateStudentId(id);
        return studentDAO.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> addStudent(@RequestBody StudentModel newStudent) {
        StudentModel savedStudent = studentDAO.save(newStudent);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(savedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody StudentModel updatedStudent) {
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
