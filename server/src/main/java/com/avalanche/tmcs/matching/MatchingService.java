package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Matches students and job postings
 *
 * @author David Dubois
 * @since 16-Apr-17.
 */
@Service
public class MatchingService {
    private MatchDAO matchDAO;

    private StudentDAO studentDAO;

    @Autowired
    public MatchingService(MatchDAO matchDAO, StudentDAO studentDAO) {
        this.matchDAO = matchDAO;
        this.studentDAO = studentDAO;
    }

    /**
     * Registers the given student with the matching student
     * <p>This adds the student to the skills index and calculates the best matches for this student</p>
     * <p>The expectation is that this method will be called when a new student is registered</p>
     *
     * @param student The student to register
     */
    void resgisterStudent(Student student) {
        // TODO: Write more of this when JobPosting is more done
    }

    /**
     * Registers the given job posting with the matching service
     * <p>This adds the job posting to the skills index and calculates the best matches for this job posting</p>
     * <p>The expectation is that this method will be called when a job posting is registered</p>
     *
     * @param posting The job posting to register
     */
    void registerJobPosting(JobPosting posting) {
        //for(Skill skill : posting.get)
    }

    private class JobMatchMetedata {
        JobPosting job;

    }
}
