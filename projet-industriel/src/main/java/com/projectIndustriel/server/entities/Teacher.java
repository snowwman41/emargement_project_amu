package com.projectIndustriel.server.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany
    @JoinTable(
            name = "assigned_modules",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    private Set<Module> modules;

    public Teacher() {
    }

    public Teacher(String userId, String firstName, String lastName, String email) {
        super(userId, firstName, lastName, email);
        this.modules = new HashSet<>();
    }

    public void assignModule(Module module) {
        this.modules.add(module);
    }

    @Override
    public Set<Module> getModules() {
        return modules;
    }

    @Override
    public void cleanAll(){
        modules.clear();
    }

    @Override
    public String getRole() {return "Teacher";}

    public void clearModules() {
        this.modules.clear();
    }
}
