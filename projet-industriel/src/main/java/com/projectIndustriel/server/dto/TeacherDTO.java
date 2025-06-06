package com.projectIndustriel.server.dto;

import java.util.Set;

public record TeacherDTO (
        String userId,
        Set<ModuleLazyDTO> modules,
        String firstName,
        String lastName,
        String email,
        CodeDTO code
) {
}
