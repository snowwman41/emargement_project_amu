package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.*;
import com.projectIndustriel.server.service.ModuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class ModuleController {

    ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/create-module")
    public void createModule(@RequestBody ModuleLazyDTO moduleDTO) throws Exception {
        moduleService.addModule(moduleDTO);
    }

    @PostMapping("/create-modules")
    public void createModules(@RequestBody List<ModuleLazyDTO> moduleDTOS) throws Exception {
        for (ModuleLazyDTO moduleDTO : moduleDTOS) {
            moduleService.addModule(moduleDTO);
        }
    }

    @GetMapping("/modules")
    public List<ModuleAdminDTO> getModules() {
        return moduleService.getModules();
    }

    @GetMapping("/modules/{moduleId}")
    public ModuleDTO getModule(@PathVariable UUID moduleId) {
        return moduleService.getModuleDTO(moduleId);
    }

    @PostMapping("/modules/assign")
    public ModuleDTO assignTeacherToModule(@RequestBody Map<String, String> moduleTeacher) {
        moduleService.assignTeacher(UUID.fromString(moduleTeacher.get("moduleId")), moduleTeacher.get("userId"));
        return moduleService.getModuleDTO(UUID.fromString(moduleTeacher.get("moduleId")));
    }

    @PostMapping("/modules/reassign")
    public void reassignModule(@RequestBody Map<String, String> moduleSpeciality) {
        moduleService.reassignModule(UUID.fromString(moduleSpeciality.get("moduleId")), moduleSpeciality.get("newSpecialityId"));
    }

    @GetMapping("/modules/{moduleId}/teachers")
    public Set<TeacherLazyDTO> getTeachersOfModule(@PathVariable UUID moduleId) {
        return moduleService.getTeachersOfModule(moduleId);
    }

    @GetMapping("/modules/{moduleId}/students")
    public Set<StudentLazyDTO> getStudentsOfModule(@PathVariable UUID moduleId) {
        return moduleService.getStudentsOfModule(moduleId);
    }

    @PostMapping("/modules/remove-teacher")
    public void decoupleTeachersModule(@RequestBody Map<String, String> moduleTeacher) {
        System.out.println(moduleTeacher);
        moduleService.decoupleTeacherModule(UUID.fromString(moduleTeacher.get("moduleId")), moduleTeacher.get("userId"));
    }

    @DeleteMapping("/delete-module/{moduleId}")
    public void deleteModule(@PathVariable UUID moduleId) {
        moduleService.deleteModule(moduleId);
    }

}
