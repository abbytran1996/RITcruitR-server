package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.students.Student;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a match between a student and a job posting
 *
 * @author David Dubois
 * @since 16-Apr-17.
 */
@Entity
@Table(name="matches")
public class Match {
    private long id;

    private Student student;

    // TODO: Uncomment when job postings exist
    //@NotNull
    //private JobPosting job;

    private float matchStrength;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    @NotNull
    @OneToOne
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
}
