package com.avalanche.tmcs;

import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.recruiter.RecruiterRepository;
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
import com.github.javafaker.Faker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

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
                String skillFilePath = new File("skills.json").getAbsolutePath();
                loadSkills(skillFilePath);
            } catch (IOException e) {
                LOG.warn("You're bad at typing", e);
            }
        }
    }

    private void loadSkills (String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            Iterable<Skill> iterableSkillsInDb = skillDAO.findAll();
            List<Skill> skillsInDb = new ArrayList<>();
            iterableSkillsInDb.forEach(skillsInDb::add);
            List<Skill> skillsToAdd = new ArrayList<>();

            for (Object skillObject : arr) {
                JSONObject skill = (JSONObject) skillObject;
                String newSkillName = (String) skill.get("name");
                Skill newSkill = new Skill(newSkillName);
                //do not add skill to the database if it is already there
                boolean isSkillInDb = false;
                for (Skill sid : skillsInDb) {
                    if (sid.getName().equals(newSkillName)) {
                        isSkillInDb = true;
                        break;
                    }
                }
                if (!isSkillInDb) {
                    skillsToAdd.add(newSkill);
                }
            }
            Iterable<Skill> savedSkills = skillDAO.save(skillsToAdd);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
        }
    }

    private User newTestUser(Faker faker){
        User user=new User();

        user.setUsername(faker.internet().emailAddress(
                faker.hacker().ingverb()+faker.team().creature()+faker.number().digits(4)
        ).replaceAll(" ",""));
        user.setPassword(faker.hacker().adjective()+faker.hacker().adjective()+faker.hacker().noun());
        user.setPasswordConfirm(user.getPassword());

        return user;
    }

    private Student newTestStudent(Faker faker,User user,ArrayList<Skill> possibleSkills){
        Student stud= new Student();

        stud.setFirstName(faker.name().firstName());
        stud.setLastName(faker.name().lastName());
        stud.setEmail(user.getUsername());

        Calendar javaSucks=Calendar.getInstance();
        javaSucks.set(Calendar.YEAR,faker.number().numberBetween(2018,2025));
        javaSucks.set(Calendar.MONTH,faker.number().numberBetween(1,12));
        javaSucks.set(Calendar.DAY_OF_MONTH,faker.number().numberBetween(1,28));

        stud.setGraduationDate(new Date(javaSucks.getTimeInMillis()));

        stud.setUser(user);

        stud.setPreferredCompanySizes(new HashSet<Integer>());

        HashSet<Skill> skills=new HashSet<Skill>();
        for(int i=0;i<15;i++)
            skills.add(possibleSkills.get(faker.number().numberBetween(0,possibleSkills.size()-1)));
        stud.setSkills(skills);
        stud.setSchool("RIT");
        stud.setMajor("Software Engineering");
        stud.setGpa(3.00);

        return stud;
    }

    private Recruiter newTestRecruiter(Faker faker,User user,Company comp){
        Recruiter rec = new Recruiter();
        rec.setFirstName(faker.name().firstName());
        rec.setLastName(faker.name().lastName());
        rec.setEmail(rec.getFirstName().toLowerCase()+"@"+comp.getEmailSuffix());
        rec.setCompany(comp);
        rec.setUser(user);
        rec.setPhoneNumber(faker.phoneNumber().phoneNumber());

        return rec;
    }

    private Company.Size randomSize(Faker faker){
        List<Company.Size> sizes= Arrays.asList(Company.Size.values());
        return sizes.get(faker.number().numberBetween(0,sizes.size()));
    }
}