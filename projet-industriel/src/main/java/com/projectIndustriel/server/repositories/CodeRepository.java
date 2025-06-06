package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CodeRepository extends JpaRepository<Code, UUID> {
    Optional<Code> findByTeacher_UserId(String userId);
}
