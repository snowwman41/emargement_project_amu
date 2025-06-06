package com.projectIndustriel.server.dto;

import java.util.Set;
import java.util.UUID;

public record SessionDTO(
        UUID sessionId,
        String sessionName,
        ModuleLazyDTO module,
        long startTime,
        long endTime,
        boolean active,
        Set<SignatureDTO> signatures
) {}

