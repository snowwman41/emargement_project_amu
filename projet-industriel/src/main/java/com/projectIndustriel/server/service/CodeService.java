package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.CodeDTO;
import com.projectIndustriel.server.entities.Code;
import com.projectIndustriel.server.repositories.CodeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CodeService {

    private static CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public static CodeDTO toDTO(Code code){
        if (code == null) return null;
        return new CodeDTO(
                code.getCodeId(),
                code.getReadableCode(),
                code.getQrCode(),
                code.getBeaconId(),
                code.getTeacher() == null ? null : TeacherService.toLazyDTO(code.getTeacher())
        );
    }

    public Set<CodeDTO> getCodes() {
        return codeRepository.findAll().stream().map(CodeService::toDTO).collect(Collectors.toSet());
    }

    public CodeDTO getCodeDTO(String userId) {
        return toDTO(codeRepository.findByTeacher_UserId(userId)
                .orElse(null));
    }

    public static Optional<Code> getCode(String userId) {
        return codeRepository.findByTeacher_UserId(userId);
    }

    public void createCode(String beaconId){
        Code code = new Code(beaconId);
        codeRepository.save(code);
    }

    public void assignCode(UUID codeId, String userId) {
        System.out.println(codeId + " assigned to " + userId);
        Optional<Code> codeOld = getCode(userId);
        if(codeOld.isPresent()) decoupleCode(userId);
        Code code = codeRepository.findById(codeId)
                .orElseThrow(() -> new IllegalArgumentException("Code not found"));
        code.setTeacher(TeacherService.getTeacher(userId));
        codeRepository.save(code);
    }

    public CodeDTO updateCode(UUID codeId) {
        Code code = codeRepository.findById(codeId)
                .orElseThrow(() -> new IllegalArgumentException("Code not found"));
        code.updateCodes();
        codeRepository.save(code);
        return toDTO(code);
    }

    public boolean getHasCode(String userId) {
        //TODO not tested yet at all
        return codeRepository.findByTeacher_UserId(userId).isPresent();
    }

    public void delete(UUID codeId) {
        codeRepository.deleteById(codeId);
    }

    public void decoupleCode(String userId) {
        Code code = getCode(userId).orElseThrow(() -> new IllegalArgumentException("Code not found"));
        code.setTeacher(null);
        codeRepository.save(code);
    }
}
