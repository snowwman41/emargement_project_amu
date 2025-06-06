package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.SignatureDTO;
import com.projectIndustriel.server.dto.SignatureLazyDTO;
import com.projectIndustriel.server.entities.Code;
import com.projectIndustriel.server.entities.Session;
import com.projectIndustriel.server.entities.Signature;
import com.projectIndustriel.server.entities.Teacher;
import com.projectIndustriel.server.repositories.CodeRepository;
import com.projectIndustriel.server.repositories.SignatureRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class SignatureService {

    private final SignatureRepository signatureRepository;
    private final CodeRepository codeRepository;


    public SignatureService(
            SignatureRepository signatureRepository,
            CodeRepository codeRepository
    ) {
        this.signatureRepository = signatureRepository;
        this.codeRepository = codeRepository;
    }

    public void sign(SignatureLazyDTO signatureLazyDTO) throws Exception {
        if(isValid(signatureLazyDTO)) {
            signatureRepository.save(toEntity(signatureLazyDTO));
        }
    }

    public boolean isValid(SignatureLazyDTO signatureLazyDTO) throws Exception {
        Session session = SessionService.getSession(signatureLazyDTO.sessionId());

        if(!session.active)
            throw new Exception("Session isn't active!");

        for(Teacher teacher : session.module.teachers) {
            Code code = codeRepository.findByTeacher_UserId(teacher.userId).orElse(null);
                if (code != null && code.isValid(signatureLazyDTO.codeType(), signatureLazyDTO.verificationCode()))
                    return true;
        }
        throw new IllegalArgumentException("Code is not valid!");
    }

    private Signature toEntity(SignatureLazyDTO signatureLazyDTO){
        Session session = SessionService.getSession(signatureLazyDTO.sessionId());
        return new Signature(
                StudentService.getStudent(signatureLazyDTO.studentId()),
                session);
    }

    public List<SignatureDTO> getSignatures() {
        return signatureRepository.findAll()
                .stream()
                .map(SignatureService::toDTO)
                .toList();
    }

    private static SignatureDTO toDTO(Signature signature) {
        return new SignatureDTO(
                signature.signatureId,
                StudentService.toLazyDTO(signature.student),
                signature.session.id,
                null,
                null
        );
    }

    protected static Set<SignatureDTO> toDTO(Set<Signature> signatures) {
        return signatures.stream().map(SignatureService::toDTO).collect(Collectors.toSet());
    }

}
