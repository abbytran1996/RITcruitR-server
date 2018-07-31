package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.job_posting.JobPostingDAO;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ddubois
 * @since 4/24/17.
 */
public class MatchingServiceTest {
    private MatchingService matchingService;

    private List<JobPosting> allJobPostings = makeJobPostings();
    private Set<Student> allStudents = makeStudents();

    private Set<Skill> requiredSkills = makeRequiredSkills();
    private Set<Skill> recommendedSkills = makeRequiredSkills();

    @Before
    public void setUp() {
        MatchDAO matchDAO = makeMockMatchDAO();
        StudentDAO studentDAO = makeMockStudentDAO();
        JobPostingDAO jobPostingDAO = makeMockJobPostingDAO();
        matchingService = new MatchingService(matchDAO, studentDAO, jobPostingDAO);

        requiredSkills = makeRequiredSkills();
        recommendedSkills = makeRecommendedSkills();
        allJobPostings = makeJobPostings();
        allStudents = makeStudents();
    }

    @Test
    public void testBuildMatch_multipleSkills() {
        Set<Skill> allSkills = new HashSet<>();
        allSkills.addAll(requiredSkills);
        allSkills.addAll(recommendedSkills);
        Student sampleStudent = allStudents.iterator().next();
        sampleStudent.setSkills(allSkills);

        JobPosting sampleJobPosting = allJobPostings.get(0);
        sampleJobPosting.setRequiredSkills(requiredSkills);
        sampleJobPosting.setRecommendedSkills(recommendedSkills);

        Optional<Match> match = MatchingService.generateMatchForStudentAndJob(sampleStudent, sampleJobPosting);
        Assert.assertTrue(match.isPresent());
    }

    @Test
    public void testBuildMatch_choosyStudent() {
        Set<Skill> allSkills = new HashSet<>();
        allSkills.addAll(recommendedSkills);
        allSkills.addAll(requiredSkills);
        Student sampleStudent = allStudents.iterator().next();
        sampleStudent.setSkills(allSkills);
        Set<Company.Size> preferredCompanySize = new HashSet<>();
        preferredCompanySize.add(Company.Size.LARGE);
        sampleStudent.setPreferredCompanySizes(preferredCompanySize);
        sampleStudent.setPreferredCompanySizeWeight(0.8);

        JobPosting sampleJobPosting = allJobPostings.get(0);

        Optional<Match> match = MatchingService.generateMatchForStudentAndJob(sampleStudent, sampleJobPosting);
        Assert.assertFalse(match.isPresent());
    }

    @Test
    public void testBuildMatch_badStudent() {
        JobPosting sampleJobPosting = allJobPostings.get(0);
        Student sampleStudent = allStudents.iterator().next();
        sampleStudent.setGpa(1.5);

        Optional<Match> match = MatchingService.generateMatchForStudentAndJob(sampleStudent, sampleJobPosting);
        Assert.assertFalse(match.isPresent());
    }

    @Test
    public void testBuildMatches_buildMatchesForStudent() {
        Student sampleStudent = allStudents.iterator().next();
        List<Match> matches = matchingService.generateMatchesForStudent(sampleStudent);
        Assert.assertFalse(matches.isEmpty());
    }

    @Test
    public void testBuildMatches_buildMatchesForJob() {
        JobPosting post = allJobPostings.get(2);
        List<Match> matches = matchingService.generateMatchesForJob(post);
        Assert.assertFalse(matches.isEmpty());
    }

    private MatchDAO makeMockMatchDAO() {
        return mock(MatchDAO.class);
    }

    private StudentDAO makeMockStudentDAO() {
        StudentDAO studentDAO = mock(StudentDAO.class);
        when(studentDAO.findAll()).thenReturn(makeStudents());
        return studentDAO;
    }

    private JobPostingDAO makeMockJobPostingDAO() {
        JobPostingDAO jobPostingDAO = mock(JobPostingDAO.class);
        when(jobPostingDAO.findAllByStatus(JobPosting.Status.ACTIVE)).thenReturn(makeJobPostings());
        return jobPostingDAO;
    }

    private static Set<Student> makeStudents() {
        Set<Student> allStudents = new HashSet<>();
        Student marx = new Student();
        marx.setFirstName("Karl");
        marx.setEmail("marx@iso.org");
        marx.setGpa(1.5);
        allStudents.add(marx);

        Student hayek = new Student();
        hayek.setFirstName("Frederich");
        hayek.setEmail("hayek@mises.org");
        hayek.setGpa(4.0);
        allStudents.add(hayek);
        return allStudents;
    }

    private static Set<Skill> makeRequiredSkills(){
        Set<Skill> requiredSkills = new HashSet<>();
        requiredSkills.add(new Skill("Bash"));
        requiredSkills.add(new Skill("C"));
        return requiredSkills;
    }

    private static Set<Skill> makeRecommendedSkills(){
        Set<Skill> recommendedSkills = new HashSet<>();
        recommendedSkills.add(new Skill("Linux"));
        recommendedSkills.add(new Skill("Perl"));
        return recommendedSkills;
    }

    private static List<JobPosting> makeJobPostings() {
        Set<Skill> requiredSkills = makeRequiredSkills();
        Set<Skill> recommendedSkills = makeRecommendedSkills();
        Set<String> locations = new HashSet<>();
        locations.add("Boston, MA");
        List<JobPosting> allJobPostings = new ArrayList<>();

        JobPosting post = new JobPosting();
        Company company = new Company();
        company.setCompanyName("Company A");
        company.setSize(Company.Size.MEDIUM);
        post.setCompany(company);
        post.setPositionTitle("Post A");
        post.setLocations(locations);
        post.setMatchThreshold(0.8);
        post.setRequiredSkills(requiredSkills);
        post.setRecommendedSkills(recommendedSkills);
        allJobPostings.add(post);

        post = new JobPosting();
        post.setPositionTitle("Post B");
        post.setMatchThreshold(0.5);
        company.setSize(Company.Size.HUGE);
        company.setCompanyName("Company B");
        post.setCompany(company);
        post.setRequiredSkills(requiredSkills);
        post.setRecommendedSkills(recommendedSkills);
        allJobPostings.add(post);

        post = new JobPosting();
        post.setPositionTitle("Post C");
        company.setCompanyName("Company C");
        post.setCompany(company);
        post.setMatchThreshold(0.3);
        post.setRecommendedSkills(requiredSkills);
        post.setCompany(company);
        allJobPostings.add(post);

        post = new JobPosting();
        post.setPositionTitle("Post D");
        company.setCompanyName("Company D");
        post.setCompany(company);
        post.setMatchThreshold(0.1);
        post.setRecommendedSkills(requiredSkills);
        post.setCompany(company);
        allJobPostings.add(post);

        return allJobPostings;
    }
}
