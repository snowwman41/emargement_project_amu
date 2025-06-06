package com.projectIndustriel.server.dto;

import java.util.Set;

public record StudentAdminDTO(
        String userId,
        Set<SpecialityLazyDTO> specialities,
        String firstName,
        String lastName,
        String email
) {
}
