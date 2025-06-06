package com.projectIndustriel.server.dto;

import java.util.Set;

public record UserDTO(
        String userId,
        String firstName,
        String lastName,
        String email,
        String role
) {
}
