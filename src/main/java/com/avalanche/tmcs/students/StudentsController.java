package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.*;
import com.avalanche.tmcs.exceptions.ResourceNotFound;
import com.avalanche.tmcs.matching.Match;
import com.avalanche.tmcs.matching.MatchDAO;
import com.avalanche.tmcs.matching.MatchingService;
import com.avalanche.tmcs.matching.SkillDAO;
import com.avalanche.tmcs.matching.Skill;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.tomcat.jni.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private SkillDAO skillDAO;
    private MatchDAO matchDAO;

    private UserService userService;

    private SecurityService securityService;

    private MatchingService matchingService;

    @Autowired
    public StudentsController(StudentDAO studentDAO, SkillDAO skillDAO, MatchDAO matchDAO, UserService userService, SecurityService securityService, MatchingService matchingService) {
        this.studentDAO = studentDAO;
        this.skillDAO = skillDAO;
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

    @RequestMapping(value = "/{id}/education", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEducation(@PathVariable long id, @RequestBody StudentEducation studentEducation) {
        validateStudentId(id);
        Student student = studentDAO.findOne(id);
        student.setSchool(studentEducation.getSchool());
        student.setMajor(studentEducation.getMajor());
        student.setGpa(studentEducation.getGpa());
        student.setGraduationDate(studentEducation.getGraduationDate());
        studentDAO.save(student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/work-experience", method = RequestMethod.POST)
    public ResponseEntity<?> addWorkExperience(@PathVariable long id, @RequestBody NewStudent studentWorkExperience) {
        validateStudentId(id);
        Student student = studentDAO.findOne(id);
        //TODO: set work experience after methods have been added
        //set company
        //set jobTitle
        //set startDate
        //set endDate
        //set description
        studentDAO.save(student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}/work-experience", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWorkExperience(@PathVariable long id, @RequestBody NewStudent studentWorkExperience) {
        validateStudentId(id);
        Student student = studentDAO.findOne(id);
        //TODO: delete work experience after methods have been added
        //delete company
        //delete jobTitle
        //delete startDate
        //delete endDate
        //delete description
        studentDAO.save(student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/byEmail/{email}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
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
        student.setWebsite(updatedStudent.getPhoneNumber());
        student.setPreferredStates(updatedStudent.getPreferredStates());
        student.setPreferredIndustries(updatedStudent.getPreferredIndustries());
        student.setPreferredCompanySize(updatedStudent.getPreferredCompanySize());
        studentDAO.save(student);
        return ResponseEntity.ok().build();
    }

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

    @RequestMapping(value = "/{id}/uploadResume", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> uploadResume(@PathVariable long id, @RequestBody Resume resume){
        Student student = studentDAO.findOne(id);
        boolean success = false;
        try {
            if(student.getResumeLocation() != null){
                File oldResumeFile = new File("./resumes/" + Long.toString(id) + "/" +
                                        student.getResumeLocation());
                if(oldResumeFile.exists()){
                    oldResumeFile.delete();
                }
            }

            File resumeFile = new File("./resumes/" + Long.toString(id) + "/" +
                    resume.getFileName());
            File resumePath = new File("./resumes/" + Long.toString(id) + "/");
            resumePath.mkdirs();
            student.setResumeLocation(resume.getFileName());
            Files.write(Paths.get(resumeFile.getCanonicalPath()), resume.getFile());
            studentDAO.save(student);
            success = true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return ResponseEntity.ok(success);
    }


    @RequestMapping(value = "/{id}/resume", method = RequestMethod.GET)
    public ResponseEntity getResume(@PathVariable long id){
        Student student = studentDAO.findOne(id);
        byte[] contents = null;
        try{
            contents = Files.readAllBytes(new File("./resumes/" + Long.toString(student.getId())
                    + "/" + student.getResumeLocation()).toPath());

        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //you don't have the pdf file
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }


    private void validateStudentId(long id) {
        if(!studentDAO.exists(id)) {
            throw new ResourceNotFound("student " + id);
        }
    }
}
