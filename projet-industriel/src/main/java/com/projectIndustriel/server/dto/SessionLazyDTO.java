package com.projectIndustriel.server.dto;

import java.util.UUID;

public record SessionLazyDTO(
        UUID sessionId,
        String sessionName,
        UUID moduleId,
        long startTime,
        long endTime,
        boolean active
) {}

