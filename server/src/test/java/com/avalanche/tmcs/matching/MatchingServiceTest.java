package com.avalanche.tmcs.matching;

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

    List<JobPosting> recommended;
    List<JobPosting> required;
    Set<Student> students;

    @Before
    public void setUp() {
        recommended = new ArrayList<>();
        required = new ArrayList<>();
        students = new HashSet<>();

        MatchDAO matchDAO = makeMockMatchDAO();
        StudentDAO studentDAO = makeMockStudentDAO();
        JobPostingDAO jobPostingDAO = makeMockJobPostingDAO();
        matchingService = new MatchingService(matchDAO, studentDAO, jobPostingDAO);
    }

    @Test
    public void testBuildMatchesList_allGood() {
        Student chompsky = new Student();
        chompsky.setFirstName("Noam");
        chompsky.setEmail("universal_grammar@trees.org");

        Map<JobPosting, MatchingService.MatchedSkillsCount> postMap = new HashMap<>();
        JobPosting posting = new JobPosting();
        posting.setPositionTitle("Position C");
        posting.setUrl("https://drive.google.com/drive/u/1/my-drive");
        posting.setRequiredSkills(new HashSet<>());

        Skill skill = new Skill();
        skill.setName("Bash");
        posting.getRequiredSkills().add(skill);

        skill = new Skill();
        skill.setName("Linux");
        posting.getRequiredSkills().add(skill);

        skill = new Skill();
        skill.setName("C");
        posting.getRequiredSkills().add(skill);

        posting.setRecommendedSkills(new HashSet<>());

        skill = new Skill();
        skill.setName("Perl");
        posting.getRecommendedSkills().add(skill);

        MatchingService.MatchedSkillsCount skillsCount = new MatchingService.MatchedSkillsCount();
        skillsCount.requiredSkillsCount = 3;
        skillsCount.recommendedSkillsCount = 1;

        postMap.put(posting, skillsCount);

        List<Match> matches = matchingService.buildMatchesList(chompsky, postMap);

        Assert.assertEquals(1, matches.size());

        Match match = matches.get(0);
        float expectedMatchStrength = skillsCount.requiredSkillsCount * 0.8f / posting.getRequiredSkills().size() + skillsCount.recommendedSkillsCount * 0.2f / posting.getRecommendedSkills().size();

        Assert.assertEquals(chompsky, match.getStudent());
        Assert.assertEquals(expectedMatchStrength, match.getMatchStrength(), 0.01);
    }

    @Test
    public void testBuildMatchesList_multipleSkills() {
        Student chompsky = new Student();
        chompsky.setFirstName("Noam");
        chompsky.setEmail("universal_grammar@trees.org");
        chompsky.setSkills(new HashSet<>());

        Map<JobPosting, MatchingService.MatchedSkillsCount> postMap = new HashMap<>();
        JobPosting posting = new JobPosting();
        posting.setPositionTitle("Position C");
        posting.setUrl("https://drive.google.com/drive/u/1/my-drive");
        posting.setRequiredSkills(new HashSet<>());

        Skill skill = new Skill();
        skill.setName("Bash");
        posting.getRequiredSkills().add(skill);
        chompsky.getSkills().add(skill);

        skill = new Skill();
        skill.setName("Linux");
        posting.getRequiredSkills().add(skill);
        chompsky.getSkills().add(skill);

        skill = new Skill();
        skill.setName("C");
        posting.getRequiredSkills().add(skill);
        chompsky.getSkills().add(skill);

        posting.setRecommendedSkills(new HashSet<>());

        skill = new Skill();
        skill.setName("Perl");
        posting.getRecommendedSkills().add(skill);

        MatchingService.MatchedSkillsCount skillsCount = new MatchingService.MatchedSkillsCount();
        skillsCount.requiredSkillsCount = 3;
        skillsCount.recommendedSkillsCount = 1;

        postMap.put(posting, skillsCount);

        List<Match> matches = matchingService.buildMatchesList(chompsky, postMap);

        Assert.assertEquals(1, matches.size());

        Match match = matches.get(0);
        float expectedMatchStrength = skillsCount.requiredSkillsCount * 0.8f / posting.getRequiredSkills().size() + skillsCount.recommendedSkillsCount * 0.2f / posting.getRecommendedSkills().size();

        Assert.assertEquals(chompsky, match.getStudent());
        Assert.assertEquals(expectedMatchStrength, match.getMatchStrength(), 0.01);
    }

    @Test
    public void testBuildMatches_noJobs() {
        Student chompsky = new Student();
        chompsky.setFirstName("Noam");
        chompsky.setEmail("universal_grammar@trees.org");

        Map<JobPosting, MatchingService.MatchedSkillsCount> postMap = new HashMap<>();

        List<Match> matches = matchingService.buildMatchesList(chompsky, postMap);

        Assert.assertEquals(0, matches.size());
    }

    @Test
    public void testCountJobPostingsThatRecommendSkill() {
        Map<JobPosting, MatchingService.MatchedSkillsCount> matchCount = new HashMap<>();
        Skill skill = new Skill();
        skill.setName("Linux");

        matchingService.countJobPostingsThatRecommendSkill(matchCount, skill);

        Assert.assertEquals(2, matchCount.size());

        JobPosting first = recommended.get(0);
        Assert.assertTrue(matchCount.containsKey(first));

        MatchingService.MatchedSkillsCount skillsCount = matchCount.get(first);
        Assert.assertEquals(1, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(0, skillsCount.requiredSkillsCount);

        JobPosting second = recommended.get(1);
        Assert.assertTrue(matchCount.containsKey(second));

        skillsCount = matchCount.get(second);
        Assert.assertEquals(1, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(0, skillsCount.requiredSkillsCount);
    }

    @Test
    public void testJobPostingsThatRequireSkill() {
        Map<JobPosting, MatchingService.MatchedSkillsCount> matchCount = new HashMap<>();
        Skill skill = new Skill();
        skill.setName("Linux");

        matchingService.countJobPostingsThatRequireSkill(matchCount, skill);

        Assert.assertEquals(2, matchCount.size());

        JobPosting first = required.get(0);
        Assert.assertTrue(matchCount.containsKey(first));

        MatchingService.MatchedSkillsCount skillsCount = matchCount.get(first);
        Assert.assertEquals(0, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(1, skillsCount.requiredSkillsCount);

        JobPosting second = required.get(1);
        Assert.assertTrue(matchCount.containsKey(second));

        skillsCount = matchCount.get(second);
        Assert.assertEquals(0, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(1, skillsCount.requiredSkillsCount);
    }

    @Test
    public void testJobPostingsThatRequireAndRecommendSkill() {
        Map<JobPosting, MatchingService.MatchedSkillsCount> matchCount = new HashMap<>();
        Skill skill = new Skill();
        skill.setName("Linux");

        matchingService.countJobPostingsThatRequireSkill(matchCount, skill);
        matchingService.countJobPostingsThatRecommendSkill(matchCount, skill);

        Assert.assertEquals(3, matchCount.size());

        JobPosting first = required.get(0);
        Assert.assertTrue(matchCount.containsKey(first));

        MatchingService.MatchedSkillsCount skillsCount = matchCount.get(first);
        Assert.assertEquals(1, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(1, skillsCount.requiredSkillsCount);

        JobPosting second = required.get(1);
        Assert.assertTrue(matchCount.containsKey(second));

        skillsCount = matchCount.get(second);
        Assert.assertEquals(0, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(1, skillsCount.requiredSkillsCount);

        JobPosting third = recommended.get(0);
        Assert.assertTrue(matchCount.containsKey(third));

        skillsCount = matchCount.get(third);
        Assert.assertEquals(1, skillsCount.recommendedSkillsCount);
        Assert.assertEquals(0, skillsCount.requiredSkillsCount);
    }

    @Test
    public void testCountStudentsWithSkillsInList() {
        List<Skill> skills = new ArrayList<>();
        Skill skill = new Skill();
        skill.setName("Seizing the means of production");
        skills.add(skill);

        Map<Student, Integer> counts = matchingService.countStudentsWithSkillInList(skills);

        Assert.assertEquals(2, counts.size());

        for(Student stu : students) {
            Assert.assertTrue(counts.containsKey(stu));
            Assert.assertEquals(1, (int)counts.get(stu));
        }
    }

    private MatchDAO makeMockMatchDAO() {
        return mock(MatchDAO.class);
    }

    private StudentDAO makeMockStudentDAO() {
        setupStudents();

        StudentDAO studentDAO = mock(StudentDAO.class);
        when(studentDAO.findAllBySkillsContains(any())).thenReturn(students);

        return studentDAO;
    }

    private void setupStudents() {
        Student marx = new Student();
        marx.setFirstName("Karl");
        marx.setEmail("manifest@proles.org");
        students.add(marx);

        Student engels = new Student();
        engels.setFirstName("Frederick");
        engels.setEmail("email@email.email");
        students.add(engels);
    }

    private JobPostingDAO makeMockJobPostingDAO() {
        setupJobPostings();

        JobPostingDAO jobPostingDAO = mock(JobPostingDAO.class);
        when(jobPostingDAO.findAllByRecommendedSkillsContains(any())).thenReturn(recommended);
        when(jobPostingDAO.findAllByRequiredSkillsContains(any())).thenReturn(required);

        return jobPostingDAO;
    }

    private void setupJobPostings() {
        JobPosting post = new JobPosting();
        post.setPositionTitle("Post a");
        post.setUrl("https://drive.google.com/drive/u/1/my-drive");
        recommended.add(post);

        post = new JobPosting();
        post.setPositionTitle("Post B");
        post.setUrl("https://drive.google.com/drive/u/1/my-drive");
        recommended.add(post);
        required.add(post);

        post = new JobPosting();
        post.setPositionTitle("Post C");
        post.setUrl("https://drive.google.com/drive/u/1/my-drive");
        required.add(post);
    }
}
