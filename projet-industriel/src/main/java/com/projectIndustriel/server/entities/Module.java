package com.projectIndustriel.server.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID moduleId;
    public String moduleName;
    @ManyToOne
    @JoinColumn(name = "speciality_id")
    public Speciality speciality;

    @ManyToMany(mappedBy = "modules")
    public Set<Teacher> teachers;

    @OneToMany(mappedBy = "module")
    public Set<Session> sessions;


    public Module() {}
    public Module(String moduleName, Speciality speciality) {
        this.moduleName = moduleName;
        this.speciality = speciality;
        this.teachers = new HashSet<>();
        this.sessions = new HashSet<Session>();
    }

    public void assignTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
}
