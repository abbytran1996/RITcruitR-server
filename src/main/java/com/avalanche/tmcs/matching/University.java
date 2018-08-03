package com.avalanche.tmcs.matching;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class University {
	
	private long id;
	private String name;
	
	public University() {}
	
	public University(String name) {
		this.name = name;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
	
    public void setId(long id) {
        this.id = id;
    }
    
    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
