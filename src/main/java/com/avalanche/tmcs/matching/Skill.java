package com.avalanche.tmcs.matching;

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

    private int usage_score;

    private String type;

    public Skill(){}

    public Skill(String name, int usage_score, String type){
        this.name = name;
        this.usage_score = usage_score;
        this.type = type;
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

    public int getUsage_score() {
        return usage_score;
    }
/
    public void setUsage_score(int usage_score) {
        this.usage_score = usage_score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
