package com.projectIndustriel.server.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Role")
public abstract class User {
    @Id
    public String userId;
    public String firstName;
    public String lastName;
    @Column(unique = true)
    public String email;
    
    public User() {
    }

    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public abstract Set<Module> getModules();
    public abstract String getRole();
    public abstract void cleanAll();
}
