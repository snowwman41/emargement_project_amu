package com.projectIndustriel.server.dto;

import com.projectIndustriel.server.utils.CodeType;

import java.util.UUID;

public record SignatureDTO(
        UUID id,
        StudentLazyDTO student,
        UUID sessionId,
        String verificationCode,
        CodeType codeType
) {
}

