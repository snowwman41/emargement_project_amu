package com.projectIndustriel.server.dto;

// Root class
public record AuthentificationResponse(
        AuthenticationSuccess authenticationSuccess,
        AuthentificationFailure authentificationFailure
) {}

