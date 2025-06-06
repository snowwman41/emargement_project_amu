package com.projectIndustriel.server.dto;

import java.util.List;

// Root class
public record SSODTO(
        AuthenticationSuccess authenticationSuccess
) {}


record AuthenticationSuccess(
        String user,
        Attributes attributes
) {}


record Attributes(
        String amuComposante,
        String coGroup,
        String mail,
        List<String> eduPersonAffiliation,
        String displayName,
        String givenName,
        String amuCampus,
        String supannEtuAnneeInscription,
        String amuDateValidation,
        String supannEntiteAffectation,
        String uid,
        String eduPersonPrimaryAffiliation,
        String supannEtuEtape,
        String supannCivilite,
        String eduPersonPrincipalName,
        List<String> memberOf,
        String sn
) {}


