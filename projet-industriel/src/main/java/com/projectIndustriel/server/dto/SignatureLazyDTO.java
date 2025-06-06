package com.projectIndustriel.server.dto;

import com.projectIndustriel.server.utils.CodeType;

import java.util.UUID;

public record SignatureLazyDTO(
        UUID id,
        String studentId,
        UUID sessionId,
        String verificationCode,
        CodeType codeType
) {
}

