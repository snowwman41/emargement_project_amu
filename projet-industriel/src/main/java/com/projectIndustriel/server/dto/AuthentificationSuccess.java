package com.projectIndustriel.server.dto;

// Nested "authenticationSuccess" object
public record AuthentificationSuccess(
        String user,
        Attributes attributes
) {}