package com.avalanche.tmcs.matching;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Ryan Hochmuth on 4/21/2018.
 * <p>
 */
@Entity
public class PresentationLink {
    private long id;

    private String title;

    private String link;
    
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof PresentationLink)) return false;
        PresentationLink pLink = (PresentationLink) o;
        return this.link.equals(pLink.link) && this.title.equals(pLink.title);
    }

    /*
        Returns true if this element is in the given set of JobPresentationLinks.
     */
    public boolean isInSet(Set<PresentationLink> linkSet) {
        for (PresentationLink inLink : linkSet) {
            if (inLink.id == this.id) {
                return true;
            }
        }

        return false;
    }
}
