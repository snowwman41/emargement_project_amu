package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.ModuleDTO;
import com.projectIndustriel.server.dto.SpecialityDTO;
import com.projectIndustriel.server.dto.SpecialityLazyDTO;
import com.projectIndustriel.server.dto.StudentLazyDTO;
import com.projectIndustriel.server.service.SpecialityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class SpecialityController {

    SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @PostMapping("/create-speciality")
    public SpecialityDTO createSpeciality(@RequestBody SpecialityDTO specialityDTO) {
        return specialityService.addSpeciality(specialityDTO);
    }

    @GetMapping("/specialities")
    public List<SpecialityLazyDTO> getSpecialities() {
        //TODO: LazyLoading
        return specialityService.getSpecialities();
    }

    @GetMapping("/specialities/{specialityId}")
    public SpecialityDTO getSpeciality(@PathVariable UUID specialityId) {
        return specialityService.getSpecialityDTO(specialityId);
    }

    @PostMapping("/specialities/assign")
    public SpecialityDTO addStudentToSpeciality(@RequestBody Map<String, String> specialityStudent) {
        specialityService.addStudent(UUID.fromString(specialityStudent.get("specialityId")), specialityStudent.get("userId"));
        return specialityService.getSpecialityDTO(UUID.fromString(specialityStudent.get("specialityId")));
    }

    @PostMapping("/specialities/decouple")
    public void decoupleStudentFromSpeciality(@RequestBody Map<String, String> specialityStudent) {
        specialityService.decoupleStudent(UUID.fromString(specialityStudent.get("specialityId")), specialityStudent.get("userId"));
    }

    @GetMapping("/specialities/{specialityId}/students")
    public Set<StudentLazyDTO> getStudentsOfSpeciality(@PathVariable UUID specialityId) {
        return specialityService.getStudentsOfSpeciality(specialityId);
    }

    @GetMapping("/specialities/{specialityId}/modules")
    public Set<ModuleDTO> getModulesOfSpeciality(@PathVariable UUID specialityId) {
        return specialityService.getModulesOfSpeciality(specialityId);
    }

    @DeleteMapping("/delete-speciality/{id}")
    public void deleteSpeciality(@PathVariable String id) {
        specialityService.delete(UUID.fromString(id));
    }
}
