package com.projectIndustriel.server.dto;

import java.util.UUID;

public record CodeLazyDTO(
        UUID codeId,
        String userId,
        String readableCode,
        String qrCode,
        String beaconId
) {
}
