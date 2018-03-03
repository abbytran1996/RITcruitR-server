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
        Skill xd = skillDAO.findByName("Experience Design");
        if(xd != null) {
            LOG.warn("Already added test data, not adding it again");
            return;
        }

        List<Skill> skills = new ArrayList<>();
        // 0
        skills.add(new Skill("Experience Design"));
        skills.add(new Skill("Agile Development"));
        skills.add(new Skill("Vision"));
        skills.add(new Skill("Organization"));
        skills.add(new Skill("Communication"));

        // 5
        skills.add(new Skill("Presentation Skills"));
        skills.add(new Skill("Negotiation"));
        skills.add(new Skill("Photoshop"));
        skills.add(new Skill("Illustrator"));
        skills.add(new Skill("Dreamweaver"));

        // 10
        skills.add(new Skill("Prototyping"));
        skills.add(new Skill("Web technologies"));
        skills.add(new Skill("HTML5"));
        skills.add(new Skill("CSS3"));
        skills.add(new Skill("Javascript"));

        // 15
        skills.add(new Skill("React"));
        skills.add(new Skill("Human Computer Interaction"));
        skills.add(new Skill("Web Frameworks"));
        skills.add(new Skill("Responsive Design"));
        skills.add(new Skill("CMS Solutions"));

        // 20
        skills.add(new Skill("Asynchronous processing"));
        skills.add(new Skill("SCRUM"));
        skills.add(new Skill("Single-Page Applications"));
        skills.add(new Skill("Java"));
        skills.add(new Skill("J2EE"));

        // 25
        skills.add(new Skill("Unix"));
        skills.add(new Skill("Software Architexture"));
        skills.add(new Skill("Big Data"));
        skills.add(new Skill("SQL"));
        skills.add(new Skill("NoSQL"));

        // 30
        skills.add(new Skill("JSON"));
        skills.add(new Skill("Backbone.js"));
        skills.add(new Skill("AngularJS"));
        skills.add(new Skill("Ember.js"));
        skills.add(new Skill("LESS"));

        // 35
        skills.add(new Skill("Sass"));
        skills.add(new Skill("NodeJS"));
        skills.add(new Skill("Express"));
        skills.add(new Skill("Express"));
        skills.add(new Skill("Handlebars"));

        // 40
        skills.add(new Skill("REST"));
        skills.add(new Skill("Object Oriented Programming"));
        skills.add(new Skill("Test-Driven Development"));
        skills.add(new Skill("Selenium"));
        Iterable<Skill> savedSkills = skillDAO.save(skills);
        skills.clear();
        savedSkills.forEach(skills::add);

        User student1User = new User("perfect_student@rit.edu", "Student1!", "Student1!");
        User student2User = new User("bad_student@rit.edu", "Student1!", "Student1!");
        User student3User = new User("middle_student@rit.edu", "Student1!", "Student1!");
        student1User = userService.save(student1User, Role.RoleName.Student);
        student2User = userService.save(student2User, Role.RoleName.Student);
        student3User = userService.save(student3User, Role.RoleName.Student);

        User recruiter1 = new User("brad_smith@intuit.com", "TurboTax12", "TurboTax12");
        recruiter1 = userService.save(recruiter1, Role.RoleName.Recruiter);

        Student student1 = new Student();
        student1.setFirstName("Perfect");
        student1.setLastName("Student");
        student1.setEmail(student1User.getUsername());
        student1.setGraduationDate(new Date(2018, 5, 1));
        student1.setUser(student1User);
        student1.setPreferredCompanySize(Company.Size.LARGE);
        Set<Skill> skillSet = new HashSet<>();
        for(int i = 1; i < skills.size(); i += 2) {
            skillSet.add(skills.get(i));
        }
        student1.setSkills(skillSet);
        student1.setSchool("Rochester Institute of Technology");
        student1 = studentDAO.save(student1);

        Student student2 = new Student();
        student2.setFirstName("Slacker");
        student2.setLastName("Student");
        student2.setEmail(student2User.getUsername());
        student2.setGraduationDate(new Date(2018, 5, 1));
        student2.setUser(student2User);
        student2.setPreferredCompanySize(Company.Size.LARGE);
        skillSet.clear();
        for(int i = 1; i < skills.size(); i += 6) {
            skillSet.add(skills.get(i));
        }
        student2.setSkills(skillSet);
        student2.setSchool("Rochester Institute of Technology");
        student2 = studentDAO.save(student2);

        Student student3 = new Student();
        student3.setFirstName("Middling");
        student3.setLastName("Student");
        student3.setEmail(student3User.getUsername());
        student3.setGraduationDate(new Date(2018, 5, 1));
        student3.setUser(student3User);
        student3.setPreferredCompanySize(Company.Size.LARGE);
        skillSet.clear();
        for(int i = 1; i < skills.size(); i += 3) {
            skillSet.add(skills.get(i));
        }
        student3.setSkills(skillSet);
        student3.setSchool("Rochester Institute of Technology");
        student3 = studentDAO.save(student3);

        Company intuit = new Company();
        intuit.setCompanyName("Intuit");
        intuit.setLocation("United States and Canada");
        intuit.setSize(Company.Size.HUGE);
        intuit.setApprovalStatus(true);
        intuit.setCompanyDescription("Innovating. Empowering. Delighting.\n" +
                "\n" +
                "Intuit believes in the power of the individual. The power to do more. To make more. To be more.\n" +
                "\n" +
                "\n" +
                "We believe in the people who do things – the hat jugglers, the to-do list junkies, the masters of getting it done faster, better and more efficiently than ever before. Whether that's balancing the household budget, running a business or paying taxes.\n" +
                "\n" +
                " \n" +
                "\n" +
                "We believe in these people because we are these people. We thrive on action – and results. And by making things simpler, we all get more out of doing what we love. We're innovators, and have been at it for more than three decades. And we don't stand still.\n" +
                "\n" +
                " \n" +
                "\n" +
                "As the world evolves, so do we – inventing new solutions to solve important problems, perfecting those solutions and delighting our customers. In short: We’re on a mission to power prosperity around the world.\n" +
                "\n" +
                "\n" +
                "We started small in 1983 with Quicken personal finance software, simplifying a common household dilemma: balancing the family check book. Today, we serve 42 million customers in North America, Europe, Australia and Brazil, with products available from the desktop to the cloud.\n" +
                "\n" +
                "\n" +
                "We're publicly traded with the symbol INTU on the Nasdaq Stock Market, and regularly recognized as one of the best places to work in locations around the world.\n" +
                "\n" +
                "Our Products\n" +
                "\n" +
                "Our flagship products – QuickBooks, TurboTax and Mint – support our mission to power prosperity around the world for small businesses, the self-employed and consumers through an ecosystem of innovative financial management solutions.\n" +
                "\n" +
                " \n" +
                "\n" +
                "QuickBooks® and TurboTax® make it easier to manage small businesses and tax preparation and filing. QuickBooks Self-Employed provides freelancers and independent contractors with an easy and affordable way to manage their finances and save money at tax time. And Mint delivers financial tools and insights to help people make smart choices about their money.\n" +
                " \n" +
                "\n" +
                "Intuit’s ProConnect brand portfolio includes ProConnect Tax Online, ProSeries® and Lacerte®, our leading tax preparation offerings for professional accountants.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Today’s expanding portfolio serves customers in North America, Europe, Australia and Brazil, with products available from the desktop to the cloud.\n" +
                "\n" +
                "Our Strategy\n" +
                "\n" +
                "Supporting our mission, our strategy is to apply a laser-like focus to help our customers prosper through One Intuit Ecosystem that:\n" +
                "\n" +
                "    Puts more money in their pockets through data-driven services,\n" +
                "    Eliminates work through innovative technology, and\n" +
                "    Provides complete confidence that they can do it right – by themselves – with complete confidence.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Just as we transformed before, from DOS to the web, and to the cloud and mobile, we are evolving again to deliver personalized experiences on a trusted, open platform and help create indispensable connections.\n" +
                "\n" +
                "Our Future\n" +
                "\n" +
                "As the way we live and work evolves, we adapt our strategy to meet and lead these changes. No matter where you find us – and whether you use our products on your PC, mobile phone or tablet – we remain committed to creating new and easier ways for consumers and businesses to tackle life's financial chores, giving them more time to live their lives and run their businesses. As our business and product lines grow beyond accounting and into new areas, we will build on our heritage of innovation. That's not just our history. It's our future.s");
        intuit.setEmailSuffix("intuit.com");
        intuit.setPresentation("https://www.youtube.com/embed/TXODDVWAPjE");
        intuit.setUser(recruiter1);
        intuit.setWebsiteURL("intuit.com");
        intuit = companyDAO.save(intuit);

        Recruiter rectuiter = new Recruiter();
        rectuiter.setFirstName("Brad");
        rectuiter.setLastName("Smith");
        rectuiter.setEmail("brad_smith@intuit.com");
        rectuiter.setCompany(intuit);
        rectuiter.setUser(recruiter1);
        rectuiter.setPhoneNumber("555-555-5555");
        recruiterDAO.save(rectuiter);

        JobPosting interactionDesignStrategest = new JobPosting();
        interactionDesignStrategest.setUrl("http://careers.intuit.com/job-category/2/design-and-user-experience/job/00127433/interaction-design-strategist-intuit-design-systems");
        interactionDesignStrategest.setProblemStatement("Tell us about a time you created deep design solutions for high complexity high-visibility projects");
        interactionDesignStrategest.setStatus(JobPosting.Status.OPEN.ordinal());
        interactionDesignStrategest.setPositionTitle("Interaction Design Strategist, Intuit Design Systems");
        interactionDesignStrategest.setDescription("Come join the CTO Dev Design Team as a Design Strategist. We are working across the ecosystem to define the future design systems and patterns for the company.     Responsibilities:  \n" +
                "\n" +
                "    Uses deep professional expertise in specific technical or domain areas to contribute to development of company priorities and achieves goals in creative and effective ways\n" +
                "    Works on the most complex and unique issues\n" +
                "    Acts independently to determine approach and process for new assignments. May manage the activities of other employees.\n" +
                "    Work has a profound impact on crucial business objectives and resource decisions\n" +
                "    Influence typically impacts multiple related projects within a business or functional group and will often affect other business and functional groups\n" +
                "    Applies long term thinking with respect to major design and technology choices\n" +
                "    While they do not own an architecture they typically contribute to the relevant architecture(s)\n" +
                "    Leads through influence to promote changes in skills, approach and mindset needed for where the organization is going within their areas of technical expertise");
        Set<Skill> importantSkills = new HashSet<>();
        importantSkills.add(skills.get(0));
        interactionDesignStrategest.setImportantSkills(importantSkills);
        Set<Skill> niceToHaveSkills = new HashSet<>();
        niceToHaveSkills.add(skills.get(1));
        interactionDesignStrategest.setNicetohaveSkills(niceToHaveSkills);
        interactionDesignStrategest.setNicetohaveSkillsWeight(0.2);
        interactionDesignStrategest.setMatchThreshold(0.5);
        interactionDesignStrategest.setRecruiter(rectuiter);
        interactionDesignStrategest.setLocation("Mountain View, California");
        interactionDesignStrategest.setPhaseTimeout(7);
        jobPostingDAO.save(interactionDesignStrategest);

        JobPosting designManager = new JobPosting();
        designManager.setStatus(JobPosting.Status.OPEN.ordinal());
        designManager.setPositionTitle("Design Manager, Intuit Design Systems");
        designManager.setDescription("Come join our team as a Design Leader within the CTO Dev Design team. This role reports to the Chief Designer.\n" +
                " \n" +
                "\n" +
                "    Manage, inspire, mentor, lead, and scale a talented group of entry level to mid-level designers and researchers.\n" +
                "    Conduct design reviews and help team align on common patterns and best outcomes for the customer and business\n" +
                "    Partner closely with engineering, product management, and design strategist stakeholders within the CTO organization and across the company to align on priorities and outcomes\n" +
                "    Review design asks, and assign best talent for role at hand\n" +
                "    Assess new talent, for full time or contract positions\n" +
                "    Lead cross-company initiatives\n" +
                "    Apply strategic thinking to design and deliver innovative end-to-end user experiences that optimize among user needs, business goals, and technological realities across web & mobile platforms\n" +
                "    Help the team take ideas and concepts, and visualize them in such that they are communicated effectively and compellingly for internal leaders, partners and customers\n" +
                "    Turn visions into concepts and translate those concepts into designs that illustrate simplicity, despite complexity of the system\n" +
                "    Directly responsible for delivering User Experience visions, and working through others to deliver UI specifications, wireframes and prototypes\n" +
                "    Collaborate effectively with researchers, product management, development, marketing, and other team members\n" +
                "    Use facilitative leadership skills to drive to the best outcome for stakeholders, resulting in others learning from you, and inspiring others to want to work with you\n" +
                "    Participate in and foster the Intuit Experience Design Community");
        importantSkills.clear();
        importantSkills.add(skills.get(11));
        importantSkills.add(skills.get(10));
        importantSkills.add(skills.get(9));
        importantSkills.add(skills.get(8));
        importantSkills.add(skills.get(7));
        importantSkills.add(skills.get(6));
        designManager.setImportantSkills(importantSkills);
        niceToHaveSkills.clear();
        niceToHaveSkills.add(skills.get(5));
        niceToHaveSkills.add(skills.get(4));
        niceToHaveSkills.add(skills.get(3));
        niceToHaveSkills.add(skills.get(2));
        niceToHaveSkills.add(skills.get(1));
        designManager.setNicetohaveSkills(niceToHaveSkills);
        designManager.setNicetohaveSkillsWeight(0.4);
        designManager.setMatchThreshold(0.8);
        designManager.setRecruiter(rectuiter);
        designManager.setLocation("Mountain View, California");
        designManager.setPhaseTimeout(7);
        designManager.setProblemStatement("Tell us about your experience with web technologies");
        designManager.setUrl("http://careers.intuit.com/job-category/2/design-and-user-experience/job/00127435/design-manager-intuit-design-systems");
        jobPostingDAO.save(designManager);

        JobPosting webArchitect = new JobPosting();
        webArchitect.setPositionTitle("Web Architect");
        webArchitect.setDescription("Come join the TurboTax Team as a Web Architect defining the technology strategy and architecture for our next generation of mobile and web UI's that will revolutionize millions of consumers financial lives so profoundly that they can't imagine going back to the old way.\n" +
                "\n" +
                "  RESPONSIBILITIES:\n" +
                "\n" +
                "    Define strategy and architecture for our next generation mobile and web UI\n" +
                "    Coordinate technology adoption and feature designs across product lines\n" +
                "    Provide leadership in all areas of the development process, including development standards and practices, system architecture and design, functional and technical design and implementation, and unit and regression test design\n" +
                "    Formulate and drive best practices around Javascript, HTML, and CSS\n" +
                "    Build prototypes to rapidly iterate on the problem and zero in on the optimal customer experience.\n" +
                "    Participate in cross-company technology initiatives");
        importantSkills.clear();
        importantSkills.add(skills.get(11));
        importantSkills.add(skills.get(12));
        importantSkills.add(skills.get(13));
        importantSkills.add(skills.get(14));
        importantSkills.add(skills.get(15));
        importantSkills.add(skills.get(16));
        importantSkills.add(skills.get(17));
        webArchitect.setImportantSkills(importantSkills);
        niceToHaveSkills.clear();
        niceToHaveSkills.add(skills.get(18));
        niceToHaveSkills.add(skills.get(19));
        niceToHaveSkills.add(skills.get(20));
        niceToHaveSkills.add(skills.get(21));
        niceToHaveSkills.add(skills.get(22));
        webArchitect.setNicetohaveSkills(niceToHaveSkills);
        webArchitect.setNicetohaveSkillsWeight(0.3);
        webArchitect.setMatchThreshold(0.8);
        webArchitect.setRecruiter(rectuiter);
        webArchitect.setLocation("San Diego, California");
        webArchitect.setPhaseTimeout(7);
        webArchitect.setProblemStatement("Compare Nginx and NodeJS, listing the pros and cons of each");
        webArchitect.setUrl("http://careers.intuit.com/job-category/1/software-engineering/job/00126881/web-architect");
        jobPostingDAO.save(webArchitect);

        JobPosting seniorSoftwareEngineer = new JobPosting();
        seniorSoftwareEngineer.setPositionTitle("Senior Software Engineer");
        seniorSoftwareEngineer.setDescription("Intuit is seeking a Senior Software Engineer to join our Artificial Intelligence & Machine Learning group in Hod Hasharon, Israel.\n" +
                "\n" +
                " \n" +
                "\n" +
                "The AI & ML team develops data-driven product features for financial management products like TurboTax, Mint and QuickBooks, as well as algorithms and analyses to drive business decisions.\n" +
                "\n" +
                " \n" +
                "\n" +
                "You’ll have the opportunity to work alongside world-class data scientists, gain insight into cutting edge data-mining and machine learning techniques and implement complex distributed algorithms to create insight and value from Intuit’s large-scale data assets.\n" +
                "\n" +
                " \n" +
                "\n" +
                "You thrive on ambiguity and enjoy the frequent pivoting that comes with agile development. Your team will be very small – no bigger than two pizzas can feed – and team members frequently wear multiple hats. If you are passionate about designing and managing world-class web products that leverage data to delight customers, this is a unique opportunity to apply your creativity.\n" +
                "\n" +
                "Responsibilities\n" +
                "\n" +
                "    Partner and collaborate with business and product management to plan, design, build, test, and launch data-driven products\n" +
                "    Architect & design whole systems or significant portions of complex systems\n" +
                "    Drive the design and implementation of highly scalable products and platforms that require advanced data techniques\n" +
                "    Work cross-functionally with various Intuit teams: product management, QA/QE, various product lines, or business units to drive forward results\n" +
                "    Deliver frequent product deployments in a fast paced Agile environment\n" +
                "    Assist in planning near term product deliverables, longer term vision, and scaling out of our current architecture");
        importantSkills.clear();
        importantSkills.add(skills.get(23));
        importantSkills.add(skills.get(25));
        importantSkills.add(skills.get(26));
        importantSkills.add(skills.get(27));
        importantSkills.add(skills.get(28));
        seniorSoftwareEngineer.setImportantSkills(importantSkills);
        niceToHaveSkills.clear();
        niceToHaveSkills.add(skills.get(24));
        niceToHaveSkills.add(skills.get(29));
        niceToHaveSkills.add(skills.get(30));
        seniorSoftwareEngineer.setNicetohaveSkills(niceToHaveSkills);
        seniorSoftwareEngineer.setNicetohaveSkillsWeight(0.2);
        seniorSoftwareEngineer.setMatchThreshold(0.8);
        seniorSoftwareEngineer.setRecruiter(rectuiter);
        seniorSoftwareEngineer.setLocation("Hod HaSharon");
        seniorSoftwareEngineer.setPhaseTimeout(7);
        seniorSoftwareEngineer.setProblemStatement("Tell us about your experience with big data processing");
        seniorSoftwareEngineer.setUrl("http://careers.intuit.com/job-category/1/software-engineering/job/00127273/senior-software-engineer");
        jobPostingDAO.save(seniorSoftwareEngineer);

        JobPosting softwareEngineer2 = new JobPosting();
        softwareEngineer2.setPositionTitle("Software Engineer 2");
        softwareEngineer2.setDescription("QuickBooks Financing is a nimble and high-priority fin-tech start-up within Intuit that is looking to reinvent small business lending. We are leveraging the unique relationships we have with small businesses to solve one of the biggest challenges they face:  getting access to capital.  We are looking for team members that love new challenges, cracking tough problems and working cross-functionally.  If you are looking to join a fast-paced, innovative and incredibly fun team, then we encourage you to apply. Come join the Quick Books Financing team as a Software Engineer 2. You will be developing a Single Page Application using the latest JavaScript, CSS, and HTML technologies.\n" +
                "\n" +
                "Responsibilities:   \n" +
                "\n" +
                "    Successful delivery of high quality web application code (requirements, design, code, documentation, etc.)\n" +
                "    Roughly 80-95% hands-on coding\n" +
                "    Contribute to overall site stability, including code reviews, writing unit and integration tests\n" +
                "    Resolve defects/bugs during testing, pre-production, production, and post-release patches\n" +
                "    Work effectively within your team and cross-functionally with various Intuit teams: product management, QE, various product lines, or business units to drive forward results\n" +
                "    Commitment to team success and positive team dynamics\n" +
                "    Passion for growing and applying technical skills in service to customers");
        importantSkills.clear();
        importantSkills.add(skills.get(14));
        importantSkills.add(skills.get(31));
        importantSkills.add(skills.get(13));
        importantSkills.add(skills.get(36));
        importantSkills.add(skills.get(40));
        importantSkills.add(skills.get(41));
        importantSkills.add(skills.get(42));
        softwareEngineer2.setImportantSkills(importantSkills);
        niceToHaveSkills.clear();
        niceToHaveSkills.add(skills.get(15));
        niceToHaveSkills.add(skills.get(32));
        niceToHaveSkills.add(skills.get(33));
        niceToHaveSkills.add(skills.get(34));
        niceToHaveSkills.add(skills.get(35));
        niceToHaveSkills.add(skills.get(37));
        niceToHaveSkills.add(skills.get(38));
        niceToHaveSkills.add(skills.get(39));
        niceToHaveSkills.add(skills.get(43));
        niceToHaveSkills.add(skills.get(23));
        niceToHaveSkills.add(skills.get(4));
        softwareEngineer2.setNicetohaveSkills(niceToHaveSkills);
        softwareEngineer2.setNicetohaveSkillsWeight(0.3);
        softwareEngineer2.setMatchThreshold(0.7);
        softwareEngineer2.setRecruiter(rectuiter);
        softwareEngineer2.setLocation("Mountain View, California");
        softwareEngineer2.setPhaseTimeout(7);
        softwareEngineer2.setProblemStatement("Compare and contrast React and AngularJS");
        softwareEngineer2.setUrl("http://careers.intuit.com/job-category/1/software-engineering/job/00126569/software-engineer-2");
        jobPostingDAO.save(softwareEngineer2);

        matchingService.registerStudent(student1);
        matchingService.registerStudent(student2);
        matchingService.registerStudent(student3);

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
        comp.setPresentation("https://www.youtube.com/embed/fSQgCy_iIcc");
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