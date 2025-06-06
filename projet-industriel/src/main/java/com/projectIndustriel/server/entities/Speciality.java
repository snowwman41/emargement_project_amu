package com.projectIndustriel.server.entities;

import com.projectIndustriel.server.dto.SpecialityDTO;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "specialities")
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID specialityId;
    @Column(unique = true)
    public String specialityName;
    @OneToMany(mappedBy = "speciality")
    public Set<Module> modules;
    @ManyToMany(mappedBy = "specialities")
    public Set<Student> students;

    public Speciality() {}
    public Speciality(String specialityName) {
        this.specialityName = specialityName;
        this.modules = new HashSet<>();
        this.students = new HashSet<>();
    }

    public boolean equals(SpecialityDTO dto){
        return this.specialityName.equals(dto.specialityName());
    }

    public void addStudent(Student student){
        students.add(student);
    }
}


