package com.projectIndustriel.server.dto;

import java.util.UUID;

public record CodeDTO(
        UUID codeId,
        String readableCode,
        String qrCode,
        String beaconId,
        TeacherLazyDTO teacher
) {
}
