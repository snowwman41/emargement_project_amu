package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.SSODTO;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface SSOServiceInterface {
    ResponseEntity<Void> getSSOLogin();

    SSODTO validateTicket(String ticket) throws IOException;
}
