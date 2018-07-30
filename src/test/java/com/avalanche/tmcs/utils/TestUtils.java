package com.avalanche.tmcs.utils;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.students.NewStudent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines utility methods to do things like ensure that there's data in the database and login users
 *
 * @author ddubois
 * @since 5/15/17.
 */
public class TestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);
    private static final String LOGIN_EMAIL           = "student21@rit.edu";
    private static final String LOGIN_PASSWORD        = "Password1!";

    private static final String LOGIN_URL             = "/user/login";
    private static final String REGISTER_STUDENT_URL  = "/students";

    private static final RestTemplate restTemplate = new RestTemplate();

    public static void ensureTestStudent() {
        NewStudent newStudent = new NewStudent();
        newStudent.setEmail(LOGIN_EMAIL);
        newStudent.setPassword(LOGIN_PASSWORD);
        newStudent.setPasswordConfirm(LOGIN_PASSWORD);
        newStudent.setFirstName("Login");
        newStudent.setLastName("Student");
        newStudent.setGraduationDate(new Date(2099, 1, 1));
        newStudent.setPhoneNumber("5555555555");
        Set<Company.Size> sizes = new HashSet<>();
        sizes.add(Company.Size.DONT_CARE);
        newStudent.setPreferredCompanySizes(sizes);
        newStudent.setPreferredLocations(new HashSet<>());
        newStudent.setSchool("RIT");
        newStudent.setSkills(new HashSet<>());

        restTemplate.postForLocation(ServerContext.urlBase + REGISTER_STUDENT_URL, newStudent);
    }

    public static String login() {
        ensureTestStudent();

        User user = new User();
        user.setUsername(LOGIN_EMAIL);
        user.setPassword(LOGIN_PASSWORD);

        LOG.info("Logging in as {}", user);

        HttpHeaders headers = restTemplate.exchange(
                ServerContext.urlBase + LOGIN_URL,
                HttpMethod.POST,
                new HttpEntity<>(user),
                new ParameterizedTypeReference<ResponseEntity<User>>() {}
        ).getHeaders();

        return "";
    }
}