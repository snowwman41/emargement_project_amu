package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByModule_moduleId(UUID moduleId);
}
