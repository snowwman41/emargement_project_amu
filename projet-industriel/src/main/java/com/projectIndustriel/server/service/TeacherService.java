package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.ModuleDTO;
import com.projectIndustriel.server.dto.TeacherDTO;
import com.projectIndustriel.server.dto.TeacherLazyDTO;
import com.projectIndustriel.server.entities.Code;
import com.projectIndustriel.server.entities.Teacher;
import com.projectIndustriel.server.repositories.CodeRepository;
import com.projectIndustriel.server.repositories.TeacherRepository;
import org.springframework.stereotype.Service;
import com.projectIndustriel.server.entities.Module;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private static TeacherRepository teacherRepository;
    private static CodeRepository codeRepository;

    public TeacherService(TeacherRepository teacherRepository,
                          CodeRepository codeRepository) {
        this.teacherRepository = teacherRepository;
        this.codeRepository = codeRepository;
    }

    public static void cleanDelete(String userId) throws IllegalArgumentException {
        Teacher teacher = getTeacher(userId);

        for (Module module : teacher.getModules()) {
            module.teachers.remove(teacher);
        }
        teacher.clearModules();

        teacherRepository.save(teacher);
        teacherRepository.delete(teacher);
    }

    public List<ModuleDTO> getModules(String userId) {
        return getTeacher(userId)
                .getModules().stream()
                .map(ModuleService::toDTO).toList();
    }

    public static Teacher getTeacher(String userId) {
        return teacherRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }

    public TeacherDTO getTeacherDTO(String userId) {
        return toDTO(getTeacher(userId));
    }

    public static TeacherDTO toDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.userId,
                ModuleService.toLazyDTO(teacher.getModules()),
                teacher.firstName,
                teacher.lastName,
                teacher.email,
                //TODO verify that code isn't null
                CodeService.toDTO(getCode(teacher.userId))
        );
    }

    private static Code getCode(String userId) {
        //TODO handle exeption when person already has a code
        return codeRepository.findByTeacher_UserId(userId).orElse(null);
    }

    public static TeacherLazyDTO toLazyDTO(Teacher teacher) {
        return new TeacherLazyDTO(
                teacher.userId,
                teacher.firstName,
                teacher.lastName,
                teacher.email
        );
    }

    public static Set<TeacherLazyDTO> toLazyDTO(Set<Teacher> teachers) {
        return teachers.stream()
                .map(TeacherService::toLazyDTO)
                .collect(Collectors.toSet());
    }

    public Teacher toEntity(TeacherDTO teacherDTO) {
        return new Teacher(
                teacherDTO.userId(),
                teacherDTO.firstName(),
                teacherDTO.lastName(),
                teacherDTO.email()
        );
    }

    public List<TeacherLazyDTO> getLazyTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherService::toLazyDTO)
                .toList();
    }

    public boolean addTeacher(TeacherDTO teacherDTO) {
        try {
            getTeacher(teacherDTO.userId());
            return false;
        }catch (IllegalArgumentException e) {
            teacherRepository.save(toEntity(teacherDTO));
            return true;
        }
    }

}
