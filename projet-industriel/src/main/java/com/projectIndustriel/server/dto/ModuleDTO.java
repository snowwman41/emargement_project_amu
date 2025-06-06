package com.projectIndustriel.server.dto;

import java.util.Set;
import java.util.UUID;

public record ModuleDTO(
        UUID moduleId,
        String moduleName,
        SpecialityLazyDTO speciality,
        Set<TeacherLazyDTO> teachers,
        Set<SessionLazyDTO> sessions
) {
}
