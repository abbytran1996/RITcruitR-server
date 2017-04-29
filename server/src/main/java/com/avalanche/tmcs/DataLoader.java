package com.avalanche.tmcs;

import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.RoleDAO;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.matching.SkillDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private SkillDAO skillDAO;
    private UserService userService;

    @Autowired
    public DataLoader(RoleDAO roleDAO, CompanyDAO companyDAO, JobPostingDAO jobPostingDAO, StudentDAO studentDAO,
                      UserService userService, SkillDAO skillDAO,
                      @Value(PropertyNames.ADD_TEST_DATA_NAME) boolean addTestData) {
        this.roleDAO = roleDAO;
        this.companyDAO = companyDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.studentDAO = studentDAO;
        this.addTestData = addTestData;
        this.userService = userService;
        this.skillDAO = skillDAO;
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
            try {
                performAdditionOfTestData();
            } catch (URISyntaxException e) {
                LOG.warn("You're bad at typing", e);
            }
        }
    }

    private void performAdditionOfTestData() throws URISyntaxException {
        Skill seizing = skillDAO.findByName("revolution");
        if(seizing != null) {
            return;
        }

        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("seizing"));
        skills.add(new Skill("social ownership"));
        skills.add(new Skill("revolution"));
        Iterable<Skill> savedSkills = skillDAO.save(skills);
        skills.clear();
        savedSkills.forEach(skills::add);

        User user = new User("karl_marx@gmail.edu", "pr0lehero!", "pr0lehero!");
        user = userService.save(user, Role.RoleName.Student);

        Student newStudent = new Student();
        newStudent.setFirstName("Karl");
        newStudent.setLastName("Marx");
        newStudent.setEmail(user.getUsername());
        newStudent.setGraduationDate(new Date(2018, 5, 1));
        newStudent.setUser(user);
        newStudent.setPreferredCompanySize(Company.Size.LARGE);
        newStudent.setSkills(skills);
        newStudent.setSchool("Hard knocks");
        newStudent = studentDAO.save(newStudent);

        Company company = new Company();
        company.setCompanyName("USSR");
        company.setLocation("Eastern Europe");
        company.setSize(Company.Size.HUGE);
        company.setApprovalStatus(true);
        company.setCompanyDescription("Union of Soviet Socialist Republics");
        company.setEmailSuffix("@ussr.gov");
        company.setPresentation(new File("E:\\Documents\\TMCS\\server"));
        company.setUser(user);
        company = companyDAO.save(company);

        JobPosting newPosting = new JobPosting();
        newPosting.setPositionTitle("Seizer");
        newPosting.setDescription("Seize the means of production");
        newPosting.setRequiredSkills(skills);
        newPosting.setMinMatchedRequiredSkills(2);
        newPosting.setRecommendedSkillsWeight(0.1);
        newPosting.setLocation("USSR");
        newPosting.setPhaseTimeout(30);
        newPosting.setProblemStatement("The bourgeoisie have ten cows. They pay you $3 per hour to milk the cows. " +
                "They sell the milk for $5 per gallon. You can milk two cows per hour, and each cow produces two" +
                "gallons of milk. How much are the bourgeoisie ripping you off by each hour?");
        newPosting.setUrl("");
        newPosting.setCompany(company);
        jobPostingDAO.save(newPosting);

        List<JobPosting> postings = new ArrayList<>();
        postings.add(newPosting);
        company.setJobPostings(postings);
        company = companyDAO.save(company);
    }
}