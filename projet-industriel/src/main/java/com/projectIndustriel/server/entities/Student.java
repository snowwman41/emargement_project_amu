package com.projectIndustriel.server.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {
    @ManyToMany
    @JoinTable(
            name = "assigned_specialities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    public Set<Speciality> specialities;

    @OneToMany(mappedBy = "student")
    public Set<Signature> signatures;

    public Student() {
    }

    public Student(String userId, String firstName, String lastName, String email) {
        super(userId, firstName, lastName, email);
        this.specialities = new HashSet<>();
        this.signatures = new HashSet<>();
    }

    public void assignSpeciality(Speciality speciality) {
        this.specialities.add(speciality);
    }

    @Override
    public Set<Module> getModules() {
        Set<Module> modules = new HashSet<>();
        for (Speciality speciality : this.specialities) {
            modules.addAll(speciality.modules);
        }
        return modules;
    }

    @Override
    public String getRole() {return "Student";}

    @Override
    public void cleanAll(){
        for (Speciality speciality: this.specialities) {
            speciality.students.remove(this);
        }
        specialities.clear();
    }
}
