package com.projectIndustriel.server.interfaces;

import com.projectIndustriel.server.dto.SessionDTO;
import com.projectIndustriel.server.dto.SignatureDTO;
import com.projectIndustriel.server.entities.Session;

import java.util.List;
import java.util.UUID;

public interface SessionServiceable {

    List<SignatureDTO> getSignatures();
    void addSignature(SignatureDTO signatureDTO);

    List<SessionDTO> getSessions();
//    {
//        Set<SignatureDTO> signatures = Collections.emptySet();
//        return List.of(new SessionDTO(
//                UUID.randomUUID(),
//                "defaultSessionName",
//                null,
//                System.currentTimeMillis() / 1000L,
//                System.currentTimeMillis() / 1000L,
//                "12345",
//                true,
//                signatures
//        ));
//    }

    void addSession(SessionDTO sessionDTO);

    void openSession(UUID sessionId);

    void closeSession(UUID sessionId);

    List<SessionDTO> toDTO(List<Session> sessions);
}