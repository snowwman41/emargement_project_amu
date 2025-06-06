package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.SessionDTO;
import com.projectIndustriel.server.dto.SignatureDTO;
import com.projectIndustriel.server.dto.SignatureLazyDTO;
import com.projectIndustriel.server.service.SessionService;
import com.projectIndustriel.server.service.SignatureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SignatureController {

    SignatureService signatureService;
    SessionService sessionService;

    public SignatureController(SignatureService signatureService,
                               SessionService sessionService) {
        this.signatureService = signatureService;
        this.sessionService = sessionService;
    }


    @GetMapping("/")
    public String index() {return "index";}

    @GetMapping("/signatures") // "/" is the path that is
    public List<SignatureDTO> signature()    {
        return signatureService.getSignatures();
    }

    @PostMapping("/sign")
    public SessionDTO sign(@RequestBody SignatureLazyDTO signatureLazyDTO) throws Exception {
        //TODO needs to handle exception in case of session still closed
            signatureService.sign(signatureLazyDTO);
        return sessionService.getSessionDTO(signatureLazyDTO.sessionId());
    }

    @PostMapping("/signList")
    public List<SignatureDTO> signList(@RequestBody List<SignatureLazyDTO> signatureLazyDTOS) throws Exception {
        for (SignatureLazyDTO signatureLazyDTO : signatureLazyDTOS) {
            sign(signatureLazyDTO);
        }
        return signatureService.getSignatures();
    }


}
