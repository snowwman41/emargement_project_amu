package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.*;
import com.projectIndustriel.server.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create-student")
    public List<StudentLazyDTO> createStudent(@RequestBody StudentDTO studentDTO){
        studentService.addStudent(studentDTO);
        return getStudents();
    }

    @GetMapping("/students")
    public List<StudentLazyDTO> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/students/admin")
    public Set<StudentDTO> getStudentsAdmin() {
        return studentService.getStudentsAdmin();
    }

    @GetMapping("/students/{userId}")
    public StudentDTO getStudent(@PathVariable String userId) throws Exception {
        return studentService.getStudentDTO(userId);
    }

    @GetMapping("/students/{userId}/modules")
    public Set<ModuleLazyDTO> getModulesOfStudent(@PathVariable String userId) throws Exception {
        return studentService.getModulesOfStudent(userId);
    }

    @GetMapping("/students/{userId}/active-sessions")
    public Set<SessionLazyDTO> getActiveSessionsOfStudent(@PathVariable String userId) throws Exception {
        return studentService.getActiveSessionsOfStudent(userId);
    }

    @GetMapping("/students/{userId}/has-signed")
    public List<Boolean> studentSignedToSessions(@PathVariable String userId, @RequestBody List<SessionLazyDTO> sessions) {
        return studentService.studentSignedToSessions(userId, sessions);
    }

    @GetMapping("/students/{userId}/specialities")
    public Set<SpecialityLazyDTO> getSpecialitiesOfStudent(@PathVariable String userId) throws Exception {
        return studentService.getSpecialitiesOfStudent(userId);
    }
}
