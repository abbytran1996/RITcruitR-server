package com.avalanche.tmcs.matching;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Ryan Hochmuth on 4/28/2018.
 * <p>
 */
@Entity
public class MatchPresentationLink {
    private long id;

    private String title;

    private String link;

    private Match match;
    
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
    
    @NotNull
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "match_id")
    @JsonIgnore
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
