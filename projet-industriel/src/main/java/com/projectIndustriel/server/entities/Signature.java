package com.projectIndustriel.server.entities;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "signatures")
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID signatureId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student student;
    @ManyToOne
    @JoinColumn(name = "session_id")
    public Session session;

    public Signature() {}

    public Signature(Student student, Session session) {
        this.student = student;
        this.session = session;
    }
}
