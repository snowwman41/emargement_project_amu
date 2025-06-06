package com.projectIndustriel.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.projectIndustriel.server.config.RestTemplateConfig;
import com.projectIndustriel.server.dto.SSODTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Service
public class SSOService implements SSOServiceInterface {
    private final String validationService = "http://localhost:8080/auth/cas/validate";
    private final RestTemplateConfig restTemplateConfig;

    public SSOService(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }
    @Override
    public ResponseEntity<Void> getSSOLogin() {
        final String amuSSO = "https://ident.univ-amu.fr/cas/login";

        String validationUrl = UriComponentsBuilder
                .fromUriString(amuSSO)
                .queryParam("service", validationService)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", validationUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    @Override
    public SSODTO validateTicket(String ticket) throws IOException {
        String validationUrl = UriComponentsBuilder
                .fromUriString("https://ident.univ-amu.fr/cas/serviceValidate")
                .queryParam("ticket", ticket)
                .queryParam("service", validationService)
                .toUriString();

        ResponseEntity<String> response = restTemplateConfig.restTemplate().getForEntity(validationUrl, String.class);
        String responseBody = response.getBody();

        if (responseBody == null || responseBody.contains("authenticationFailure")) {
            System.out.println(responseBody);
            return null;
        }

        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = xmlMapper.readTree(responseBody.getBytes());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(node);

        return objectMapper.readValue(jsonString, SSODTO.class);
    }


}
