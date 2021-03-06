package com.avalanche.tmcs.job_posting;

import com.avalanche.tmcs.matching.PresentationLink;
import com.avalanche.tmcs.matching.PresentationLinkDAO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @autho Ryan Hochmuth
 * @since 4/21/2018
 * @author Abigail My Tran
 */
@Entity
public class JobPresentationLink {
    private long id;

    private String title;

    private String link;

    private JobPosting job;
    
    private String description = "";

    private PresentationLink presentationLink;

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

    @ManyToOne
    @JoinColumn(name = "presentationLink_id")
    @JsonIgnore
    public PresentationLink getPresentationLink() {
        return presentationLink;
    }

    public void setPresentationLink(PresentationLink presentationLink) {
        this.presentationLink = presentationLink;
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
