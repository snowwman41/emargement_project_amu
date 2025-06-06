package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
    Set<Module> findBySpecialitySpecialityId(UUID specialityId);
}
