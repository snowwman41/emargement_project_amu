package com.projectIndustriel.server.dto;

import java.util.UUID;

public record ModuleAdminDTO(
        UUID moduleId,
        String moduleName,
        SpecialityLazyDTO speciality
) {
}
