package com.avalanche.tmcs;

import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.RoleDAO;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Gives the database its initial data
 */
@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
    private final boolean addTestData;

    private RoleDAO roleDAO;

    private CompanyDAO companyDAO;
    private JobPostingDAO jobPostingDAO;
    private StudentDAO studentDAO;
    private UserService userService;

    @Autowired
    public DataLoader(RoleDAO roleDAO, CompanyDAO companyDAO, JobPostingDAO jobPostingDAO, StudentDAO studentDAO,
                      UserService userService, @Value(PropertyNames.ADD_TEST_DATA_NAME) boolean addTestData) {
        this.roleDAO = roleDAO;
        this.companyDAO = companyDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.studentDAO = studentDAO;
        this.addTestData = addTestData;
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {
        if(roleDAO.findByName("student") == null) {
            roleDAO.save(new Role("student"));
        }

        if(roleDAO.findByName("recruiter") == null) {
            roleDAO.save(new Role("recruiter"));
        }

        if(roleDAO.findByName("admin") == null) {
            roleDAO.save(new Role("admin"));
        }

        LOG.info("Added Role definitions");

        if(addTestData) {
            performAdditionOfTestData();
        }
    }

    private void performAdditionOfTestData() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("siezing"));
        skills.add(new Skill("social ownership"));
        skills.add(new Skill("revolution"));

        User user = new User("karl_marx@gmail.com", "pr0lehero!", "pr0lehero!");
        user = userService.save(user, Role.RoleName.Student);

        Student newStudent = new Student();
        newStudent.setFirstName("Karl");
        newStudent.setLastName("Marx");
        newStudent.setEmail("karl_marx@gmail.edu");
        newStudent.setGraduationDate(new Date(2018, 5, 1));
        newStudent.setUser(user);
        newStudent.setPreferredCompanySize(Company.Size.LARGE);
    }
}