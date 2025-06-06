package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Module;
import com.projectIndustriel.server.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    List<Module> findModulesByUserId(String userId);
}
