package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.students.Student;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URL;
import java.sql.Date;

/**
 * Represents a match between a student and a job posting
 *
 * @author David Dubois
 * @since 16-Apr-17.
 */
@Entity
@Table(name="matches")
public class Match {
    public enum ApplicationStatus {
        // The Match has been generated but the student hasn't interacted with it
        NEW,

        // The student has pressed the interested button AND neither the student nor the recruiter has rejected the
        // application
        IN_PROGRESS,

        // The student has been given an interview
        ACCEPTED,

        // Either the student or the recruiter has rejected this application
        REJECTED,

        // One of the parties took too long to respond
        TIMED_OUT
    }

    public enum CurrentPhase {
        NONE,
        PROBLEM_WAITING_FOR_STUDENT,
        PROBLEM_WAITING_FOR_RECRUITER,
        PRESENTATION,
        INTERVIEW
    }

    private long id;

    /* Data about the match */

    private Student student;

    private JobPosting job;

    private float matchStrength;

    /* Data from the student's application process */

    // Tag created by the recruiter to help organize students
    private String tag;

    private String studentProblemResponse;

    private URI studentPresentationLink;

    private Date timeLastUpdated;

    private ApplicationStatus applicationStatus = ApplicationStatus.NEW;

    private CurrentPhase currentPhase = CurrentPhase.NONE;

    public Match() {
        setLastUpdatedTimeToNow();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @NotNull
    @ManyToOne
    @JoinTable(name = "matches_students", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    public Student getStudent() {
        return student;
    }

    @NotNull
    public float getMatchStrength() {
        return matchStrength;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setMatchStrength(float matchStrength) {
        this.matchStrength = matchStrength;
    }

    @NotNull
    @ManyToOne
    @JoinTable(name = "matches_jobs", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
    public JobPosting getJob() {
        return job;
    }

    public void setJob(JobPosting job) {
        this.job = job;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
        if(tag != null) {
            setLastUpdatedTimeToNow();
        }
    }

    public String getStudentProblemResponse() {
        return studentProblemResponse;
    }

    public void setStudentProblemResponse(final String studentProblemResponse) {
        this.studentProblemResponse = studentProblemResponse;
        if(studentProblemResponse != null) {
            setApplicationStatus(ApplicationStatus.IN_PROGRESS);
            setCurrentPhase(CurrentPhase.PROBLEM_WAITING_FOR_RECRUITER);
            setLastUpdatedTimeToNow();
        }
    }

    public URI getStudentPresentationLink() {
        return studentPresentationLink;
    }

    public void setStudentPresentationLink(final URI studentPresentationLink) {
        this.studentPresentationLink = studentPresentationLink;
        if(studentPresentationLink != null) {
            setApplicationStatus(ApplicationStatus.IN_PROGRESS);
            setCurrentPhase(CurrentPhase.PRESENTATION);
            setLastUpdatedTimeToNow();
        }
    }

    @NotNull
    public Date getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(final Date timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    @NotNull
    // @Enumerated(EnumType.STRING)
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(final ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @NotNull
    // @Enumerated(EnumType.STRING)
    public CurrentPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(final CurrentPhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void setLastUpdatedTimeToNow() {
        // Yes, this is how Java Dates are now. I'm using the parent class to get the current time, then sending that
        // to the constructor of the child class. Yay Java dates!
        setTimeLastUpdated(new Date(new java.util.Date().getTime()));
    }
}
