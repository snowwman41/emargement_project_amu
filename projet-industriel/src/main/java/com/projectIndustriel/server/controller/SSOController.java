package com.projectIndustriel.server.controller;
import com.projectIndustriel.server.dto.SSODTO;
import com.projectIndustriel.server.service.SSOService;
import com.projectIndustriel.server.service.SSOServiceInterface;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class SSOController {
    private final String validationService = "http://localhost:8080/auth/cas/validate";
    private final SSOServiceInterface ssoService;

    public SSOController(SSOService ssoService) {
        this.ssoService = ssoService;
    }


    @GetMapping("/cas")
    public ResponseEntity<Void> getSSOLogin() {
        return ssoService.getSSOLogin();
    }

    @GetMapping(path = "/cas/validate",produces = MediaType.APPLICATION_JSON_VALUE)
    public SSODTO auth(@RequestParam String ticket) throws JAXBException, IOException {
        //receive data after validation
        return ssoService.validateTicket(ticket);
    }

   // TODO: Implement token verification
    @PostMapping("/token/verify")
    public Boolean tokenVerify(){
        return isTokenValid();
    }

    private Boolean isTokenValid(){
        // TODO: Check Jwt token validation, if valid bypass the SSO
        return false;
    }

}
