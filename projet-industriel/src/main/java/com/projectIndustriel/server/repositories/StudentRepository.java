package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
