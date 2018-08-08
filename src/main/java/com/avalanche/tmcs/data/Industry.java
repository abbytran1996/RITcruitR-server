package com.avalanche.tmcs.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Industry {
	
	private long id;
	private String name;
	private int usageScore = 0;
	
	public Industry() {}
	
	public Industry(String name) {
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

    public int getUsageScore() {
        return usageScore;
    }

    public void setUsageScore(int usageScore) {
        this.usageScore = usageScore;
    }
}
