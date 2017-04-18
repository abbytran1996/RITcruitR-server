package com.avalanche.tmcs.matching;

import com.avalanche.tmcs.students.Student;
import com.avalanche.tmcs.students.StudentModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author David Dubois
 * @since 17-Apr-17.
 */
@Entity
@Table(name="skills")
public class SkillModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    //@ManyToMany(mappedBy = "student")
    //private List<StudentModel> student;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<StudentModel> getStudent() {
        return student;
    }

    public void setStudent(List<StudentModel> student) {
        this.student = student;
    }*/
}
