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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;

        return id == skill.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
