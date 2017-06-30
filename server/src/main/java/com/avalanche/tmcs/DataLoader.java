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
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
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



        //faker data generation

        final int DATA_SIZE=10;

        Faker faker=new Faker();

        //putting these in lists to view while debugging
        ArrayList<User> testUsers=new ArrayList<User>();
        ArrayList<Skill> testSkills=new ArrayList<Skill>();
        ArrayList<Student> testStudents=new ArrayList<Student>();
        ArrayList<Company> testCompanies=new ArrayList<Company>();
        List<Recruiter> testRecruiters=new ArrayList<Recruiter>();
        List<JobPosting> testJobs=new ArrayList<JobPosting>();

        for(int i=0;i<50;i++){
            testSkills.add(new Skill(faker.superhero().power()));
        }

        for(int i=0;i<DATA_SIZE;i++){
            User user1=newTestUser(faker);
            User amUser=userService.save(user1,Role.RoleName.Student);
            testUsers.add(amUser);
            testStudents.add(newTestStudent(faker,amUser,testSkills));

            User user2=newTestUser(faker);
            User refUser=userService.save(user2,Role.RoleName.Recruiter);
            testUsers.add(refUser);
            Company pleasantCompany=newTestCompany(faker,refUser);
            testCompanies.add(pleasantCompany);
            Recruiter rec=newTestRecruiter(faker,refUser,pleasantCompany);
            testRecruiters.add(rec);
            testJobs.add(newTestJobPosting(faker,rec,testSkills));
        }

        //persist data
        skillDAO.save(testSkills);
        studentDAO.save(testStudents);
        companyDAO.save(testCompanies);
        recruiterDAO.save(testRecruiters);
        jobPostingDAO.save(testJobs);
        for(Student s:testStudents)
            matchingService.registerStudent(s);



        Skill seizing = skillDAO.findByName("Revolution");
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
        ussr.setUser(user1);
        ussr.setWebsiteURL("ussr.gov");
        ussr = companyDAO.save(ussr);

        Recruiter lenin = new Recruiter();
        lenin.setFirstName("Vladimir");
        lenin.setLastName("Lenin");
        lenin.setEmail("lenin@ussr.gov");
        lenin.setCompany(ussr);
        lenin.setUser(user1);
        lenin.setPhoneNumber("555-555-5555");
        recruiterDAO.save(lenin);

        JobPosting seizer = new JobPosting();
        seizer.setPositionTitle("Seizer");
        seizer.setDescription("Seize the means of production");
        seizer.setImportantSkills(skills);
        seizer.setMatchThreshold(0.8);
        seizer.setNicetohaveSkillsWeight(0.1);
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

        stud.setPreferredCompanySize(randomSize(faker));

        HashSet<Skill> skills=new HashSet<Skill>();
        for(int i=0;i<15;i++)
            skills.add(possibleSkills.get(faker.number().numberBetween(0,possibleSkills.size()-1)));
        stud.setSkills(skills);
        stud.setSchool("RIT");

        return stud;
    }

    private Company newTestCompany(Faker faker,User user){
        Company comp = new Company();

        comp.setCompanyName(faker.company().name());
        comp.setLocation(faker.address().fullAddress());
        comp.setSize(randomSize(faker));
        comp.setApprovalStatus(true);
        comp.setCompanyDescription(faker.company().catchPhrase());
        comp.setWebsiteURL(faker.company().url());
        comp.setEmailSuffix(comp.getWebsiteURL().replaceFirst("www\\.",""));
        comp.setPresentation("sample.youtube.com");
        comp.setUser(user);


        return comp;
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

    private JobPosting newTestJobPosting(Faker faker,Recruiter rec,ArrayList<Skill> possibleSkills){
        JobPosting job = new JobPosting();
        job.setPositionTitle(faker.company().profession());
        job.setDescription(faker.company().bs());


        HashSet<Skill> skills=new HashSet<Skill>();
        for(int i=0;i<15;i++)
            skills.add(possibleSkills.get(faker.number().numberBetween(0,possibleSkills.size()-1)));
        job.setImportantSkills(skills);
        job.setMatchThreshold(0.8);
        job.setNicetohaveSkillsWeight(0.1);
        job.setLocation(rec.getCompany().getLocation());
        job.setPhaseTimeout(30);

        //have to do a replace here since pi is an invalid character in sql
        job.setProblemStatement(faker.chuckNorris().fact().replaceAll("π","pi"));
        job.setUrl("<insert job url here>");
        job.setRecruiter(rec);

        return job;
    }

    private Company.Size randomSize(Faker faker){
        List<Company.Size> sizes= Arrays.asList(Company.Size.values());
        return sizes.get(faker.number().numberBetween(0,sizes.size()));
    }
}