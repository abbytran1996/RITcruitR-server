package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.students.StudentModel;

/**
 * Matches students and job postings
 *
 * @author David Dubois
 * @since 16-Apr-17.
 */
public class MatchingService {
    /**
     * Registeres the given student with the matching student
     * <p>This adds the student to the skills index and calculates the best matches for this student</p>
     * <p>The expectation is that this method will be called when a new student is registered</p>
     *
     * @param student The student to register
     */
    void resgisterStudent(StudentModel student) {

    }

    /**
     * Registers the given job posting with the matching service
     * <p>This adds the job posting to the skills index and calculates the best matches for this job posting</p>
     * <p>The expectation is that this method will be called when a job posting is registered</p>
     *
     * @param posting The job posting to register
     */
    // void registerJobPosting(JobPostingModel posting) {
    //      TODO: Uncomment when we have job postings
    // }
}
