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
import com.avalanche.tmcs.job_posting.NewJobPosting;
import com.avalanche.tmcs.matching.Location;
import com.avalanche.tmcs.matching.LocationDAO;
import com.avalanche.tmcs.matching.MatchingService;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.matching.SkillDAO;
import com.avalanche.tmcs.students.NewStudent;
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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    private RecruiterRepository recruiterDAO;
    private CompanyDAO companyDAO;
    private JobPostingDAO jobPostingDAO;
    private StudentDAO studentDAO;
    private SkillDAO skillDAO;
    private LocationDAO locationDAO;
    private UserService userService;
    private MatchingService matchingService;

    @Autowired
    public DataLoader(RoleDAO roleDAO, RecruiterRepository recruiterDAO, CompanyDAO companyDAO, JobPostingDAO jobPostingDAO, StudentDAO studentDAO,
                      UserService userService, SkillDAO skillDAO, LocationDAO locationDAO, MatchingService matchingService,
                      @Value(PropertyNames.ADD_TEST_DATA_NAME) boolean addTestData) {
        this.roleDAO = roleDAO;
        this.recruiterDAO = recruiterDAO;
        this.companyDAO = companyDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.studentDAO = studentDAO;
        this.addTestData = addTestData;
        this.userService = userService;
        this.skillDAO = skillDAO;
        this.locationDAO = locationDAO;
        this.matchingService = matchingService;
    }

    public void run(ApplicationArguments args) {
        LOG.info("Adding role definitions...");
        if(roleDAO.findByName("student") == null) {
            roleDAO.save(new Role("student"));
        }

        if(roleDAO.findByName("recruiter") == null) {
            roleDAO.save(new Role("recruiter"));
        }

        if(roleDAO.findByName("admin") == null) {
            roleDAO.save(new Role("admin"));
        }

        if(addTestData) {
            try {
                LOG.info("Adding test data...");
                String skillFilePath = new File("skills.json").getAbsolutePath();
                String jobFilePath = new File("jobs.json").getAbsolutePath();
                String studentsFilePath = new File("students.json").getAbsolutePath();
                String locationsFilePath = new File("locations.json").getAbsolutePath();
                loadSkills(skillFilePath);
                loadJobs(jobFilePath);
              loadLocations(locationsFilePath);
            } catch (IOException e) {
                LOG.warn("IOException reached while trying to load the test data. Please check the filename for any typos.", e);
            }
        }
    }

    private void loadStudents(String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            for (Object studentObject : arr) {
                JSONObject student = (JSONObject) studentObject;
                NewStudent newStudent = new NewStudent();
                newStudent.setContactEmail((String) student.get("contactEmail"));
                newStudent.setEmail((String) student.get("email"));
                newStudent.setFirstName((String) student.get("firstName"));
                newStudent.setGpa((long) student.get("gpa"));
                Date graduationDate = Date.valueOf((String) student.get("graduationDate"));
                newStudent.setGraduationDate(graduationDate);
                newStudent.setLastName((String) student.get("lastName"));
                newStudent.setMajor((String) student.get("major"));
                newStudent.setPhoneNumber((String) student.get("phoneNumber"));
                ArrayList<Integer> companySizesList = (ArrayList<Integer>) student.get("preferredCompanySizes");
                Set<Integer> preferredCompanySizes = new HashSet<Integer>();
                for (Integer size : companySizesList) {
                	preferredCompanySizes.add(size);
                }
                newStudent.setPreferredCompanySizes(preferredCompanySizes);
                ArrayList<String> industriesList = (ArrayList<String>) student.get("preferredIndustries");
                Set<String> preferredIndustries = new HashSet<String>();
                for (String industry : industriesList) {
                	preferredIndustries.add(industry);
                }
                newStudent.setPreferredIndustries(preferredIndustries);
                ArrayList<String> locationsList = (ArrayList<String>) student.get("preferredLocations");
                Set<String> preferredLocations = new HashSet<String>();
                for (String location : locationsList) {
                	preferredLocations.add(location);
                }
                newStudent.setPreferredLocations(preferredLocations);
                newStudent.setSchool((String) student.get("school"));
                Set<Skill> studentSkills = new HashSet<Skill>();
                JSONArray skillsList = (JSONArray) student.get("skills");
                for (Object skillObject : skillsList) {
                	JSONObject skill = (JSONObject) skillObject;
                	String skillName = (String) skill.get("name");
                	Skill newSkill = new Skill(skillName);
                	studentSkills.add(newSkill);
                }
                newStudent.setSkills(studentSkills);
                newStudent.setWebsite((String) student.get("website"));
                //a new student needs to be linked to a user object which needs an email and password to be in the system
//                User newUser = new User(email, password);
//                newStudent.setUser(newUser);
                Student savedStudent = studentDAO.save(newStudent.toStudent());
                matchingService.registerStudent(savedStudent);
            }
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
        }
    }
    
    private void loadJobs(String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            for (Object jobObject : arr) {
                JSONObject job = (JSONObject) jobObject;
                String jobTitle = (String) job.get("positionTitle");
                String description = (String) job.get("description");
                //locations
                ArrayList<String> locationsList = (ArrayList<String>) job.get("locations");
                Set<String> locations = new HashSet<String>();
                for (String loc : locationsList) {
                	locations.add(loc);
                }
                
                //required skills
                Set<Skill> requiredSkills = new HashSet<Skill>();
                JSONArray requiredSkillsList = (JSONArray) job.get("requiredSkills");
                for (Object requiredSkillObject : requiredSkillsList) {
                	JSONObject requiredSkill = (JSONObject) requiredSkillObject;
                	String skillName = (String) requiredSkill.get("name");
                	Skill newSkill = new Skill(skillName);
                	requiredSkills.add(newSkill);
                }
                
                //nice to have skills
                Set<Skill> niceToHaveSkills = new HashSet<Skill>();
                JSONArray niceToHaveSkillsList = (JSONArray) job.get("niceToHaveSkills");
                for (Object niceToHaveSkillObject : niceToHaveSkillsList) {
                	JSONObject niceToHaveSkill = (JSONObject) niceToHaveSkillObject;
                	String skillName = (String) niceToHaveSkill.get("name");
                	Skill newSkill = new Skill(skillName);
                	niceToHaveSkills.add(newSkill);
                }
                
                //nice to have skills weight ?
                long minGPA = (long) job.get("minGPA");
                //has work experience
                //match threshold
                long duration = (long) job.get("duration");
                String problemStatement = (String) job.get("problemStatement");
                String video = (String) job.get("video");
                
                //company
                JSONObject companyObject = (JSONObject) job.get("company");
                String companyName = (String) companyObject.get("companyName");
                Company company = companyDAO.findByCompanyName(companyName);
                
                //recruiter
                JSONObject recruiterObject = (JSONObject) job.get("recruiter");
                Recruiter recruiter = recruiterDAO.findByEmail((String) recruiterObject.get("email"));
                
                NewJobPosting newJob = new NewJobPosting();
                newJob.setCompany(company);
                newJob.setRecruiter(recruiter);
                newJob.setDescription(description);
                newJob.setDuration(duration);
//                newJob.setHasWorkExperience(hasWorkExperience);
                newJob.setLocations(locations);
//                newJob.setMatchThreshold(matchThreshold);
                newJob.setMinGPA(minGPA);
//                newJob.setNewVideo(newVideo);
                newJob.setNiceToHaveSkills(niceToHaveSkills);
//                newJob.setNiceToHaveSkillsWeight(niceToHaveSkillsWeight);
                newJob.setPositionTitle(jobTitle);
                newJob.setProblemStatement(problemStatement);
                newJob.setRecruiter(recruiter);
                newJob.setRequiredSkills(requiredSkills);
                newJob.setStatus(0);
                newJob.setVideo(video);
                System.out.println(newJob.toString());
//                JobPosting savedJobPosting = jobPostingDAO.save(newJob.toJobPosting());
//                matchingService.registerJobPosting(savedJobPosting);
            }
//            Iterable<JobPosting> savedJobs = jobPostingDAO.save(jobsToAdd);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
        }
    }
    
    private void loadLocations (String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            Iterable<Location> iterableLocationsInDb = locationDAO.findAll();
            List<Location> locationsInDb = new ArrayList<>();
            iterableLocationsInDb.forEach(locationsInDb::add);
            List<Location> locationsToAdd = new ArrayList<>();

            for (Object locationObject : arr) {
                JSONObject location = (JSONObject) locationObject;
                String newLocationName = (String) location.get("name");
                Location newLocation = new Location(newLocationName);
                boolean isLocationInDb = false;
                for (Location lid : locationsInDb) {
                	if (lid.getName().equals(newLocation.getName())) {
                		isLocationInDb = true;
                		break;
                	}
                }
                //save newLocation to db
                if (!isLocationInDb) {
                    locationsToAdd.add(newLocation);
                }
            }
            Iterable<Location> savedLocations = locationDAO.save(locationsToAdd);
        } catch (ParseException e) {
            LOG.warn(e.getMessage());
        }
    }
    
    private void loadSkills (String fileName) throws IOException {
        try {
            // populate list of skills to save with those already in the database
            List<Skill> skillsToSave = new ArrayList<>();
            skillDAO.findAll().forEach(skillsToSave::add);

            // add in new skills from the json
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));
            for (Object skillObject : arr) {
                JSONObject skill = (JSONObject) skillObject;
                String newSkillName = (String) skill.get("name");
                Skill newSkill = new Skill(newSkillName);

                // ensure there aren't any duplicates
                if(!skillsToSave.contains(newSkill))
                    skillsToSave.add(newSkill);
            }

            // update the existing skill list with the new master set
            skillDAO.save(skillsToSave);
        } catch (ParseException e) {
            LOG.warn("Unable to loadSkills from fileName: " + fileName, e);
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