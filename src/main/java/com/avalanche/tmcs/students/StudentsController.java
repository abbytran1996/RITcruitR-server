package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.data.DataController;
import com.avalanche.tmcs.data.Skill;
import com.avalanche.tmcs.exceptions.ResourceNotFound;
import com.avalanche.tmcs.matching.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private static final Logger LOG = LoggerFactory.getLogger(StudentsController.class);

    private StudentDAO studentDAO;
    private PresentationLinkDAO presentationLinkDAO;
    private ProblemStatementDAO problemStatementDAO;
    private DataController dataController;

    private UserService userService;
    private SecurityService securityService;
    private MatchingService matchingService;

    @Autowired
    public StudentsController(StudentDAO studentDAO, PresentationLinkDAO presentationLinkDAO, ProblemStatementDAO problemStatementDAO, DataController dataController,
                              UserService userService, SecurityService securityService, MatchingService matchingService) {
        this.studentDAO = studentDAO;
        this.presentationLinkDAO = presentationLinkDAO;
        this.problemStatementDAO = problemStatementDAO;
        this.dataController = dataController;

        this.userService = userService;
        this.securityService = securityService;
        this.matchingService = matchingService;
    }

    // ================================================================================================================
    // * GET STUDENT BY ID [GET]                                                                                      *
    // ================================================================================================================
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

    // ================================================================================================================
    // * GET STUDENT BY EMAIL [GET]                                                                                   *
    // ================================================================================================================
    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        LOG.debug("Getting student with email " + email);
        Student student = studentDAO.findByEmail(email);
        if(student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    // ================================================================================================================
    // * ADD NEW STUDENT [POST]                                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Student> addStudent(@RequestBody NewStudent newStudent) {
        User newUser = new User(newStudent.getEmail(), newStudent.getPassword(), newStudent.getPasswordConfirm());

        newUser = userService.save(newUser, Role.RoleName.Student);
        if(securityService.login(newUser.getUsername(), newUser.getPasswordConfirm())) {
            newStudent.setUser(newUser);
            dataController.updateUsageScoreData(newStudent);

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

    // ================================================================================================================
    // * UPDATE STUDENT [PUT]                                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable long id, @RequestBody Student updatedStudent) {
        validateStudentId(id);
        Student student = studentDAO.findOne(id);
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());
        student.setGraduationDate(updatedStudent.getGraduationDate());
        student.setSchool(updatedStudent.getSchool());
        student.setMajor(updatedStudent.getMajor());
        student.setGpa(updatedStudent.getGpa());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setContactEmail(updatedStudent.getContactEmail());
        student.setWebsite(updatedStudent.getWebsite());
        student.setPreferredLocations(updatedStudent.getPreferredLocations());
        student.setPreferredIndustries(updatedStudent.getPreferredIndustries());
        student.setPreferredCompanySizes(updatedStudent.getPreferredCompanySizes());
        student.setPreferredLocationsWeight(updatedStudent.getPreferredLocationsWeight());
        student.setPreferredIndustriesWeight(updatedStudent.getPreferredIndustriesWeight());
        student.setPreferredCompanySizeWeight(updatedStudent.getPreferredCompanySizeWeight());
        studentDAO.save(student);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET STUDENT PRESENTATION LINKS [GET]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/links", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentPresentationLinks(@PathVariable long id){
        Student student = studentDAO.findOne(id);

        if (student != null) {
            return ResponseEntity.ok(student.getPresentationLinks());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // ================================================================================================================
    // * ADD STUDENT PRESENTATION LINK [POST]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/link", method = RequestMethod.POST)
    public ResponseEntity<PresentationLink> addStudentPresentationLink(@PathVariable long id, @RequestBody PresentationLink presentationLink) {
        PresentationLink newLink = presentationLinkDAO.save(presentationLink);
        Student student = studentDAO.findOne(id);

        Set<PresentationLink> studentLinks = student.getPresentationLinks();
        studentLinks.add(newLink);
        student.setPresentationLinks(studentLinks);
        studentDAO.save(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLink.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLink);
    }

    // ================================================================================================================
    // * UPDATE STUDENT PRESENTATION LINK [PUT]                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/link/{linkId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentPresentationLink(@PathVariable long id, @PathVariable long linkId, @RequestBody PresentationLink presentationLink) {
        presentationLink.setId(linkId);
        presentationLinkDAO.save(presentationLink);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE STUDENT PRESENTATION LINK [DELETE]                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/link/{linkId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentPresentationLink(@PathVariable long id, @PathVariable long linkId) {
        PresentationLink findLink = presentationLinkDAO.findOne(linkId);
        Student student = studentDAO.findOne(id);

        Set<PresentationLink> links = student.getPresentationLinks();
        links.remove(findLink);
        studentDAO.save(student);
        presentationLinkDAO.delete(linkId);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * GET STUDENT PROBLEM STATEMENTS [GET]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/problemstatements", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentProblemStatements(@PathVariable long id){
        Student student = studentDAO.findOne(id);

        if (student != null) {
            return ResponseEntity.ok(student.getProblemStatements());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    // ================================================================================================================
    // * ADD STUDENT PROBLEM STATEMENT [POST]                                                                         *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/problemstatement", method = RequestMethod.POST)
    public ResponseEntity<ProblemStatement> addStudentProblemStatement(@PathVariable long id, @RequestBody ProblemStatement problemStatement) {
        ProblemStatement newStatement = problemStatementDAO.save(problemStatement);
        Student student = studentDAO.findOne(id);

        Set<ProblemStatement> studentStatements = student.getProblemStatements();
        studentStatements.add(newStatement);
        student.setProblemStatements(studentStatements);
        studentDAO.save(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStatement.getId())
                .toUri();

        return ResponseEntity.created(location).body(newStatement);
    }

    // ================================================================================================================
    // * UPDATE STUDENT PROBLEM STATEMENT [PUT]                                                                       *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/problemstatement/{statementId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentProblemStatement(@PathVariable long id, @PathVariable long statementId, @RequestBody ProblemStatement problemStatement) {
        problemStatement.setId(statementId);
        problemStatementDAO.save(problemStatement);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * DELETE STUDENT PROBLEM STATEMENT [DELETE]                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/problemstatement/{statementId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentProblemStatement(@PathVariable long id, @PathVariable long statementId) {
        ProblemStatement findStatement = problemStatementDAO.findOne(statementId);
        Student student = studentDAO.findOne(id);

        Set<ProblemStatement> statements = student.getProblemStatements();
        statements.remove(findStatement);
        studentDAO.save(student);
        problemStatementDAO.delete(statementId);

        return ResponseEntity.ok().build();
    }

    // ================================================================================================================
    // * ADD STUDENT SKILLS [POST]                                                                                    *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/skills", method = RequestMethod.POST)
    public ResponseEntity<Student> updateSkills(@PathVariable long id, @RequestBody Set<Skill> skills){
        Student ourstudent = studentDAO.findOne(id);
        if(ourstudent == null) {
            return ResponseEntity.notFound().build();
        }
        ourstudent.setSkills(skills);
        studentDAO.save(ourstudent);
        matchingService.registerStudent(ourstudent);
        return ResponseEntity.ok(ourstudent);
    }

    // ================================================================================================================
    // * COMPLETE STUDENT SETUP [GET]                                                                                 *
    // ================================================================================================================
    @RequestMapping(value = "/{id}/completesetup", method = RequestMethod.GET)
    public ResponseEntity<Student> completeStudentSetup(@PathVariable long id){
        Student ourstudent = studentDAO.findOne(id);
        if(ourstudent == null) {
            return ResponseEntity.notFound().build();
        }
        ourstudent.setIsSetup(true);
        studentDAO.save(ourstudent);
        return ResponseEntity.ok(ourstudent);
    }

    /*
        Validate that a student with the given id exists.
     */
    private void validateStudentId(long id) {
        if(!studentDAO.exists(id)) {
            throw new ResourceNotFound("student " + id);
        }
    }
}
