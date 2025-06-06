package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SignatureRepository extends JpaRepository<Signature, UUID> {
    Optional<Signature> findBySession_IdAndStudent_UserId(UUID sessionId, String studentId);
}
