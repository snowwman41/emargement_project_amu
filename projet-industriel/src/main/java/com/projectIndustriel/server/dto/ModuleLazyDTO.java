package com.projectIndustriel.server.dto;

import java.util.UUID;

public record ModuleLazyDTO(
        UUID moduleId,
        String moduleName,
        UUID specialityId
) {
}
