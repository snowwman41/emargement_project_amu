package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Module;
import com.projectIndustriel.server.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
}
