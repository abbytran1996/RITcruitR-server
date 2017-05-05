package com.avalanche.tmcs;

import com.avalanche.tmcs.Recruiter.Recruiter;
import com.avalanche.tmcs.Recruiter.RecruiterRepository;
import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.RoleDAO;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.matching.MatchingService;
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
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Gives the database its initial data
 */
@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
    private final boolean addTestData;

    private RoleDAO roleDAO;

    private RecruiterRepository recruiterDAO;
    private CompanyDAO companyDAO;
    private JobPostingDAO jobPostingDAO;
    private StudentDAO studentDAO;
    private SkillDAO skillDAO;
    private UserService userService;
    private MatchingService matchingService;

    @Autowired
    public DataLoader(RoleDAO roleDAO, RecruiterRepository recruiterDAO, CompanyDAO companyDAO, JobPostingDAO jobPostingDAO, StudentDAO studentDAO,
                      UserService userService, SkillDAO skillDAO, MatchingService matchingService,
                      @Value(PropertyNames.ADD_TEST_DATA_NAME) boolean addTestData) {
        this.roleDAO = roleDAO;
        this.recruiterDAO = recruiterDAO;
        this.companyDAO = companyDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.studentDAO = studentDAO;
        this.addTestData = addTestData;
        this.userService = userService;
        this.skillDAO = skillDAO;
        this.matchingService = matchingService;
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
            LOG.warn("Already added test data, not adding it again");
            return;
        }

        Set<Skill> skills = new HashSet<>();
        skills.add(new Skill("Seizing"));
        skills.add(new Skill("Social Ownership"));
        skills.add(new Skill("Revolution"));
        Iterable<Skill> savedSkills = skillDAO.save(skills);
        skills.clear();
        savedSkills.forEach(skills::add);

        User user = new User("karl_marx@gmail.edu", "pr0lehero!", "pr0lehero!");
        User user1 = new User("lenin@ussr.gov", "pr0lehero!", "pr0lehero!");
        user = userService.save(user, Role.RoleName.Student);
        user1 = userService.save(user1, Role.RoleName.Recruiter);

        Student karlMarx = new Student();
        karlMarx.setFirstName("Karl");
        karlMarx.setLastName("Marx");
        karlMarx.setEmail(user.getUsername());
        karlMarx.setGraduationDate(new Date(2018, 5, 1));
        karlMarx.setUser(user);
        karlMarx.setPreferredCompanySize(Company.Size.LARGE);
        karlMarx.setSkills(skills);
        karlMarx.setSchool("Hard knocks");
        karlMarx = studentDAO.save(karlMarx);

        Company ussr = new Company();
        ussr.setCompanyName("USSR");
        ussr.setLocation("Eastern Europe");
        ussr.setSize(Company.Size.HUGE);
        ussr.setApprovalStatus(true);
        ussr.setCompanyDescription("Union of Soviet Socialist Republics");
        ussr.setEmailSuffix("ussr.gov");
        ussr.setPresentation("sample.youtube.com");
        ussr.setUser(user);
        ussr.setWebsiteURL("ussr.gov");
        ussr = companyDAO.save(ussr);

        Recruiter lenin = new Recruiter();
        lenin.setFirstName("Vladimir");
        lenin.setLastName("Lenin");
        lenin.setEmail("lenin@ussr.gov");
        lenin.setCompany(ussr);
        lenin.setPhoneNumber("555-555-5555");
        recruiterDAO.save(lenin);

        JobPosting seizer = new JobPosting();
        seizer.setPositionTitle("Seizer");
        seizer.setDescription("Seize the means of production");
        seizer.setRequiredSkills(skills);
        seizer.setMinMatchedRequiredSkills(2);
        seizer.setRecommendedSkillsWeight(0.1);
        seizer.setLocation("USSR");
        seizer.setPhaseTimeout(30);
        seizer.setProblemStatement("The bourgeoisie have ten cows. They pay you $3 per hour to milk the cows. " +
                "They sell the milk for $5 per gallon. You can milk two cows per hour, and each cow produces two" +
                "gallons of milk. How much are the bourgeoisie ripping you off by each hour?");
        seizer.setUrl("");
        seizer.setRecruiter(lenin);
        jobPostingDAO.save(seizer);

        matchingService.registerStudent(karlMarx);

        LOG.info("Test data added");
    }
}