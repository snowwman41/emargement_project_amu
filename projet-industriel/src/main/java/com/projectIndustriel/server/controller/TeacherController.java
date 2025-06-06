package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.ModuleDTO;
import com.projectIndustriel.server.dto.ModuleLazyDTO;
import com.projectIndustriel.server.dto.TeacherDTO;
import com.projectIndustriel.server.dto.TeacherLazyDTO;
import com.projectIndustriel.server.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class TeacherController {
    TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/create-teacher")
    public List<TeacherLazyDTO> createTeacher(@RequestBody TeacherDTO teacherDTO){
        teacherService.addTeacher(teacherDTO);
        return teacherService.getLazyTeachers();
    }

    @GetMapping("/teachers")
    public List<TeacherLazyDTO> getTeachers() {
        return teacherService.getLazyTeachers();
    }

    @GetMapping("/teachers/{userId}")
    public TeacherDTO getTeacher(@PathVariable String userId) {
        return teacherService.getTeacherDTO(userId);
    }

    @GetMapping("/teachers/{userId}/modules")
    public List<ModuleDTO> getModules(@PathVariable String userId) {
        return teacherService.getModules(userId);
    }



}
