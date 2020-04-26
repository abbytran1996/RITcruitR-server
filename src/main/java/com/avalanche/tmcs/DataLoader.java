package com.avalanche.tmcs;

import com.avalanche.tmcs.recruiter.Recruiter;
import com.avalanche.tmcs.recruiter.RecruiterDAO;
import com.avalanche.tmcs.auth.Role;
import com.avalanche.tmcs.auth.RoleDAO;
import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.auth.UserService;
import com.avalanche.tmcs.company.CompanyDAO;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.job_posting.NewJobPosting;
import com.avalanche.tmcs.matching.Industry;
import com.avalanche.tmcs.matching.IndustryDAO;
import com.avalanche.tmcs.matching.Location;
import com.avalanche.tmcs.matching.LocationDAO;
import com.avalanche.tmcs.matching.Major;
import com.avalanche.tmcs.matching.MajorDAO;
import com.avalanche.tmcs.matching.MatchingService;
import com.avalanche.tmcs.matching.Skill;
import com.avalanche.tmcs.matching.SkillDAO;
import com.avalanche.tmcs.matching.University;
import com.avalanche.tmcs.matching.UniversityDAO;
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
import java.util.TimerTask;

/**
 * Gives the database its initial data
 */
@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
    private final boolean addTestData;

    private RoleDAO roleDAO;

    private RecruiterDAO recruiterDAO;
    private CompanyDAO companyDAO;
    private JobPostingDAO jobPostingDAO;
    private StudentDAO studentDAO;
    private SkillDAO skillDAO;
    private LocationDAO locationDAO;
    private MajorDAO majorDAO;
    private IndustryDAO industryDAO;
    private UniversityDAO universityDAO;
    private UserService userService;
    private MatchingService matchingService;
    private CompanyService companyService;
    private JobService jobService;

    @Autowired
    public DataLoader(RoleDAO roleDAO, RecruiterDAO recruiterDAO, CompanyDAO companyDAO, JobPostingDAO jobPostingDAO, StudentDAO studentDAO,
                      UserService userService, SkillDAO skillDAO, LocationDAO locationDAO, MajorDAO majorDAO, IndustryDAO industryDAO,
                      UniversityDAO universityDAO, MatchingService matchingService, @Value(PropertyNames.ADD_TEST_DATA_NAME) boolean addTestData) {
        this.roleDAO = roleDAO;
        this.recruiterDAO = recruiterDAO;
        this.companyDAO = companyDAO;
        this.jobPostingDAO = jobPostingDAO;
        this.studentDAO = studentDAO;
        this.addTestData = addTestData;
        this.userService = userService;
        this.skillDAO = skillDAO;
        this.locationDAO = locationDAO;
        this.majorDAO = majorDAO;
        this.industryDAO = industryDAO;
        this.universityDAO = universityDAO;
        this.matchingService = matchingService;
    }

    public void run(ApplicationArguments args) throws IOException {
        this.companyService = new CompanyService();
        String PROJECT_ID = "recruitrtest-256719";
        String microsoftName = "projects/recruitrtest-256719/tenants/075e3c6b-df00-0000-0000-00fbd63c7ae0/companies/95040b83-b531-40b4-81a3-caccbf92ca80";
        String languageCode = "en-US";
        String SAMPLE_COMPANY_ID = "075e3c6b-df00-0000-0000-00fbd63c7ae0";
        String SAMPLE_JOB_ID = "136747347917841094";

        CompanyService.listCompaniesGoogleAPI(PROJECT_ID);

        //jobService.sampleCreateJob(PROJECT_ID, microsoftName, "110", "Software Developer", "Create a website for the company", Arrays.asList("New York, NY", "San Francisco, CA"), Arrays.asList("Java,Python"), Arrays.asList("Java,Python"),false, "www.microsoft.com", "Software Developer", "","recruiter@microsoft.com",3.5,"www.microsoft.com", languageCode);
        //jobService.sampleGetJob(PROJECT_ID, SAMPLE_COMPANY_ID, SAMPLE_JOB_ID);
//        jobService.listJobsGoogleAPI(PROJECT_ID, "companyName=\"projects/recruitrtest-256719/tenants/075e3c6b-df00-0000-0000-00fbd63c7ae0/companies/95040b83-b531-40b4-81a3-caccbf92ca80\"");
//        jobService.searchJobsGoogleAPI(PROJECT_ID, "Software Engineer");

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

        if(roleDAO.findByName("primaryrecruiter") == null) {
            roleDAO.save(new Role("primaryrecruiter"));
        }

        if(addTestData) {
            try {
                LOG.info("Adding test data...");
                String skillFilePath = new File("skills.json").getAbsolutePath();
                String toolFilePath = new File("tools.json").getAbsolutePath();
                String jobFilePath = new File("jobs.json").getAbsolutePath();
                String locationsFilePath = new File("locations.json").getAbsolutePath();
                String majorsFilePath = new File("majors.json").getAbsolutePath();
                String universitiesFilePath = new File("universities.json").getAbsolutePath();
                String industriesFilePath = new File("industries.json").getAbsolutePath();
                loadSkills(skillFilePath);
                loadJobs(jobFilePath);
                loadLocations(locationsFilePath);
                loadMajors(majorsFilePath);
                loadUniversities(universitiesFilePath);
                loadIndustries(industriesFilePath);

            } catch (IOException e) {
                LOG.warn("IOException reached while trying to load the test data. Please check the filename for any typos.", e);
            }
        }
    }

    private void updateMajorsFromPortfolium() {
    	//Need to get response from portfolium API first
    	String portfoliumMajors = "";
    	JSONParser jsonParser = new JSONParser();
    	List<Major> savedMajors = new ArrayList<>();
    	majorDAO.findAll().forEach(savedMajors::add);
    	ArrayList<Major> majorsToSave = new ArrayList<>();
    	try {
			JSONArray arr = (JSONArray) jsonParser.parse(portfoliumMajors);
	    	for (Object majorObject : arr) {
	    		JSONObject major = (JSONObject) majorObject;
	    		String newMajorName = (String) major.get("major");
	    		Major newMajor = new Major(newMajorName);
	    		if (!savedMajors.contains(newMajor)) {
	    			majorsToSave.add(newMajor);
	    		}
	    	}
	    	majorDAO.save(majorsToSave);
		} catch (ParseException e) {
			LOG.warn("Unable to retrieve/parse majors from Portfolium API", e);
		}
    }

    private void updateIndustriesFromPortfolium() {
    	//Need to get response from portfolium API first
    	String portfoliumIndustries = "";
    	JSONParser jsonParser = new JSONParser();
    	List<Industry> savedIndustries = new ArrayList<>();
    	industryDAO.findAll().forEach(savedIndustries::add);
    	ArrayList<Industry> industriesToSave = new ArrayList<>();
    	try {
			JSONArray arr = (JSONArray) jsonParser.parse(portfoliumIndustries);
	    	for (Object industryObject : arr) {
	    		JSONObject industry = (JSONObject) industryObject;
	    		String newIndustryName = (String) industry.get("industry");
	    		Industry newIndustry = new Industry(newIndustryName);
	    		if (!savedIndustries.contains(newIndustry)) {
	    			industriesToSave.add(newIndustry);
	    		}
	    	}
	    	industryDAO.save(industriesToSave);
		} catch (ParseException e) {
			LOG.warn("Unable to retrieve/parse industries from Portfolium API", e);
		}
    }

    private void updateUniversitiesFromPortfolium() {
    	//Need to get response from portfolium API first
    	String portfoliumUniversities = "";
    	JSONParser jsonParser = new JSONParser();
    	List<University> savedUniversities = new ArrayList<>();
    	universityDAO.findAll().forEach(savedUniversities::add);
    	ArrayList<University> universitiesToSave = new ArrayList<>();
    	try {
			JSONArray arr = (JSONArray) jsonParser.parse(portfoliumUniversities);
	    	for (Object universityObject : arr) {
	    		JSONObject university = (JSONObject) universityObject;
	    		String newUniversityName = (String) university.get("name");
	    		University newUniversity = new University(newUniversityName);
	    		if (!savedUniversities.contains(newUniversity)) {
	    			universitiesToSave.add(newUniversity);
	    		}
	    	}
	    	universityDAO.save(universitiesToSave);
		} catch (ParseException e) {
			LOG.warn("Unable to retrieve/parse universities from Portfolium API", e);
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
                locations.addAll(locationsList);

                //required skills
                Set<Skill> requiredSkills = new HashSet<Skill>();
                JSONArray requiredSkillsList = (JSONArray) job.get("requiredSkills");
                for (Object requiredSkillObject : requiredSkillsList) {
                	JSONObject requiredSkill = (JSONObject) requiredSkillObject;
                	String skillName = (String) requiredSkill.get("name");
                    String skillType = (String) requiredSkill.get("type");

                	Skill newSkill = new Skill(skillName, 0, skillType);
                	requiredSkills.add(newSkill);
                }
                
                //recommended to have skills
                Set<Skill> recommendedSkills = new HashSet<Skill>();
                JSONArray recommendedSkillsList = (JSONArray) job.get("recommendedSkills");
                for (Object recommendedSkillObject : recommendedSkillsList) {
                	JSONObject recommendedSkill = (JSONObject) recommendedSkillObject;
                	String skillName = (String) recommendedSkill.get("name");
                    String skillType = (String) recommendedSkill.get("type");

                	Skill newSkill = new Skill(skillName, 0, skillType);
                    recommendedSkills.add(newSkill);
                }
                
                //recommended to have skills weight ?
                double minGPA = new Double(job.get("minGPA").toString());

                //match threshold
                int duration = ((Long) job.get("duration")).intValue();
                String problemStatement = (String) job.get("problemStatement");
                String video = (String) job.get("video");
                
                //company
                JSONObject companyObject = (JSONObject) job.get("company");
                String companyName = (String) companyObject.get("companyName");
                com.avalanche.tmcs.company.Company company = companyDAO.findByCompanyName(companyName);
                
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
                newJob.setRecommendedSkills(recommendedSkills);
                newJob.setRequiredSkills(requiredSkills);
//                newJob.setRecommendedSkillsWeight(recommendedSkillsWeight);
                newJob.setPositionTitle(jobTitle);
                newJob.setProblemStatement(problemStatement);
                newJob.setRecruiter(recruiter);
                newJob.setStatus(JobPosting.Status.ACTIVE);
                newJob.setVideo(video);
                System.out.println(newJob.toString());
//                JobPosting savedJobPosting = jobPostingDAO.save(newJob.toJobPosting());
//                matchingService.registerJobPosting(savedJobPosting);
            }
//            Iterable<JobPosting> savedJobs = jobPostingDAO.save(jobsToAdd);
        } catch (ParseException | IOException e) {
            LOG.warn(e.getMessage());
        }
    }
    
    private void loadLocations (String fileName) {
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
                	if (lid.getName().equalsIgnoreCase(newLocation.getName())) {
                		isLocationInDb = true;
                		break;
                	}
                }
                //save new location to db
                if (!isLocationInDb) {
                    locationsToAdd.add(newLocation);
                }
            }
            Iterable<Location> savedLocations = locationDAO.save(locationsToAdd);
        } catch (ParseException | IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    private void loadMajors (String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            Iterable<Major> iterableMajorsInDb = majorDAO.findAll();
            List<Major> majorsInDb = new ArrayList<>();
            iterableMajorsInDb.forEach(majorsInDb::add);
            List<Major> majorsToAdd = new ArrayList<>();

            for (Object majorObject : arr) {
                JSONObject major = (JSONObject) majorObject;
                String newMajorName = (String) major.get("name");
                Major newMajor = new Major(newMajorName);
                boolean isMajorInDb = false;
                for (Major dbMajor : majorsInDb) {
                	if (dbMajor.getName().equalsIgnoreCase(newMajor.getName())) {
                		isMajorInDb = true;
                		break;
                	}
                }
                //save new major to db
                if (!isMajorInDb) {
                	majorsToAdd.add(newMajor);
                }
            }
            Iterable<Major> savedMajors = majorDAO.save(majorsToAdd);
        } catch (ParseException | IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    private void loadUniversities (String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            Iterable<University> iterableUniversitiesInDb = universityDAO.findAll();
            List<University> universitiesInDb = new ArrayList<>();
            iterableUniversitiesInDb.forEach(universitiesInDb::add);
            List<University> universitiesToAdd = new ArrayList<>();

            for (Object universityObject : arr) {
                JSONObject university = (JSONObject) universityObject;
                String newUniversityName = (String) university.get("name");
                University newUniversity = new University(newUniversityName);
                boolean isUniversityInDb = false;
                for (University dbUniversity : universitiesInDb) {
                	if (dbUniversity.getName().equalsIgnoreCase(newUniversity.getName())) {
                		isUniversityInDb = true;
                		break;
                	}
                }
                //save new university to db
                if (!isUniversityInDb) {
                	universitiesToAdd.add(newUniversity);
                }
            }
            Iterable<University> savedUniversities = universityDAO.save(universitiesToAdd);
        } catch (ParseException | IOException e) {
            LOG.warn(e.getMessage());
        }
    }

    private void loadIndustries (String fileName) throws IOException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray arr = (JSONArray) jsonParser.parse(new FileReader(fileName));

            Iterable<Industry> iterableIndustriesInDb = industryDAO.findAll();
            List<Industry> industriesInDb = new ArrayList<>();
            iterableIndustriesInDb.forEach(industriesInDb::add);
            List<Industry> industriesToAdd = new ArrayList<>();

            for (Object industryObject : arr) {
                JSONObject industry = (JSONObject) industryObject;
                String newIndustryName = (String) industry.get("name");
                Industry newIndustry = new Industry(newIndustryName);
                boolean isIndustryInDb = false;
                for (Industry dbIndustry : industriesInDb) {
                	if (dbIndustry.getName().equalsIgnoreCase(newIndustry.getName())) {
                		isIndustryInDb = true;
                		break;
                	}
                }
                //save newLocation to db
                if (!isIndustryInDb) {
                	industriesToAdd.add(newIndustry);
                }
            }
            Iterable<Industry> savedIndustries = industryDAO.save(industriesToAdd);
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
                String newSkillType = (String) skill.get("type");

                Skill newSkill = new Skill(newSkillName,0,newSkillType);

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

        stud.setPreferredCompanySizes(new HashSet<>());

        HashSet<Skill> skills=new HashSet<>();
        for(int i=0;i<15;i++)
            skills.add(possibleSkills.get(faker.number().numberBetween(0,possibleSkills.size()-1)));
        stud.setSkills(skills);
        stud.setGpa(3.00);

        return stud;
    }

    private Recruiter newTestRecruiter(Faker faker, User user, com.avalanche.tmcs.company.Company comp){
        Recruiter rec = new Recruiter();
        rec.setFirstName(faker.name().firstName());
        rec.setLastName(faker.name().lastName());
        rec.setEmail(rec.getFirstName().toLowerCase()+"@"+comp.getEmailSuffix());
        rec.setCompany(comp);
        rec.setUser(user);
        rec.setPhoneNumber(faker.phoneNumber().phoneNumber());

        return rec;
    }

    private com.avalanche.tmcs.company.Company.Size randomSize(Faker faker){
        List<com.avalanche.tmcs.company.Company.Size> sizes= Arrays.asList(com.avalanche.tmcs.company.Company.Size.values());
        return sizes.get(faker.number().numberBetween(0,sizes.size()));
    }

    class PortfoliumSkillsUpdater extends TimerTask{

		@Override
		public void run() {
	        try {
	        	List<Skill> savedSkills = new ArrayList<>();
	            List<Skill> skillsToSave = new ArrayList<>();
	            skillDAO.findAll().forEach(savedSkills::add);

	            String portfoliumSkillsResponse = "";
	            JSONParser jsonParser = new JSONParser();
	            JSONArray arr = (JSONArray) jsonParser.parse(portfoliumSkillsResponse);
	            for (Object skillObject : arr) {
	                JSONObject skill = (JSONObject) skillObject;
	                String newSkillName = (String) skill.get("name");
                    String newSkillType = (String) skill.get("type");

	                Skill newSkill = new Skill(newSkillName, 0, newSkillType);

	                // ensure there aren't any duplicates
	                boolean dbContainsSkill = false;
	                for (Skill savedSkill : savedSkills) {
	                	if (savedSkill.getName().equalsIgnoreCase(newSkill.getName())) {
	                		dbContainsSkill = true;
	                		break;
	                	}
	                }
	                if (!dbContainsSkill) {
	                	skillsToSave.add(newSkill);
	                }
	            }

	            skillDAO.save(skillsToSave);
	        } catch (ParseException e) {
	            LOG.warn("Unable to parse/retrieve trending skills from Portfolium", e);
	        }
		}

    }
}