package com.avalanche.tmcs.job_posting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Ryan Hochmuth on 4/21/2018.
 * <p>
 */
@Entity
public class JobPresentationLink {
    private long id;

    private String title;

    private String link;

    private JobPosting job;
    
    private String description = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    public JobPosting getJob() {
        return job;
    }

    public void setJob(JobPosting job) {
        this.job = job;
    }
    
    @NotNull
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }

    /*
        Returns true if this element is in the given set of JobPresentationLinks.
     */
    public boolean isInSet(Set<JobPresentationLink> linkSet) {
        for (JobPresentationLink inLink : linkSet) {
            if (inLink.id == this.id) {
                return true;
            }
        }

        return false;
    }
}
