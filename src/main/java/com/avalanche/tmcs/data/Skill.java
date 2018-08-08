package com.avalanche.tmcs.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Maxwell Hadley
 * @since 4/16/17.
 */
@Entity
@Table(name="skills")
public class Skill {

    private long id;

    private String name;
    private int count = 0;

    private int usage_score;

    public Skill(){}

    public Skill(String name){
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsageScore() {
        return usage_score;
    }

    public void setUsageScore(int usage_score) {
        this.usage_score = usage_score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;
        return name.equalsIgnoreCase(skill.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
