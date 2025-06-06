package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.CodeDTO;
import com.projectIndustriel.server.dto.TeacherDTO;
import com.projectIndustriel.server.service.CodeService;
import com.projectIndustriel.server.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CodeController {

    TeacherService teacherService;
    CodeService codeService;

    public CodeController(TeacherService teacherService, CodeService codeService) {
        this.teacherService = teacherService;
        this.codeService = codeService;
    }

    @PostMapping("/codes/assign")
    public TeacherDTO assignCode(@RequestBody Map<String, String> codeTeacher) {
        System.out.println(codeTeacher);
        codeService.assignCode(UUID.fromString(codeTeacher.get("codeId")), codeTeacher.get("userId"));
        return teacherService.getTeacherDTO(codeTeacher.get("userId"));
    }

    @PostMapping("/decouple-code/{userId}")
    public void decoupleCode(@PathVariable String userId) {
        codeService.decoupleCode(userId);
    }

    @PostMapping("/create-code")
    public Set<CodeDTO> createCode(@RequestBody String beaconId) throws SQLIntegrityConstraintViolationException {
        codeService.createCode(beaconId);
        return codeService.getCodes();
    }

    @GetMapping("/codes")
    public Set<CodeDTO> getCodes() {
        return codeService.getCodes();
    }

    @GetMapping("/codes/{userId}")
    public CodeDTO getCodeOfTeacher(@PathVariable String userId) {
        return codeService.getCodeDTO(userId);
    }

    @PostMapping("/codes/{codeId}/update")
    public CodeDTO updateCode(@PathVariable UUID codeId) {
        return codeService.updateCode(codeId);
    }

    @GetMapping("/teachers/{userId}/hasCode")
    public boolean getHasCode(@PathVariable String userId) {
        return codeService.getHasCode(userId);
    }

    @DeleteMapping("/delete-code/{codeId}")
    public void deleteCode(@PathVariable String codeId) {
        codeService.delete(UUID.fromString(codeId));
    }

    //TODO activate code (teacherid)that return code
}
