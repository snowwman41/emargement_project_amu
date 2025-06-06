package com.projectIndustriel.server.dto;

import java.util.Set;
import java.util.UUID;

public record SpecialityDTO(
        UUID id,
        String specialityName,
        Set<ModuleLazyDTO> modules,
        Set<StudentLazyDTO> students
) {
}
