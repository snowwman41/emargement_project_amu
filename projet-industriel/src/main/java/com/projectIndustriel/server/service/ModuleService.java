package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.*;
import com.projectIndustriel.server.entities.Module;
import com.projectIndustriel.server.entities.Speciality;
import com.projectIndustriel.server.entities.Teacher;
import com.projectIndustriel.server.repositories.ModuleRepository;
import com.projectIndustriel.server.repositories.SpecialityRepository;
import com.projectIndustriel.server.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final TeacherRepository teacherRepository;
    private static ModuleRepository moduleRepository;
    private final SpecialityRepository specialityRepository;

    public ModuleService(ModuleRepository moduleRepository,
                         TeacherRepository teacherRepository,
                         SpecialityRepository specialityRepository) {
        this.moduleRepository = moduleRepository;
        this.teacherRepository = teacherRepository;
        this.specialityRepository = specialityRepository;
    }

    public void addModule(ModuleLazyDTO moduleDTO) throws Exception {
        Set<Module> modules = moduleRepository.findBySpecialitySpecialityId(moduleDTO.specialityId());
        for (Module module : modules)
            if (module.moduleName.equals(moduleDTO.moduleName()))
                throw new Exception(String.format("Module %s already exists", moduleDTO.moduleName()));
        Module entity = new Module(
                moduleDTO.moduleName(),
                SpecialityService.getSpeciality(moduleDTO.specialityId()));
        moduleRepository.save(entity);
    }

    public ModuleDTO getModuleDTO(UUID moduleId) {
        return toDTO(Objects.requireNonNull(moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"))));
    }

    public static Module getModule(UUID moduleId) {
        return Objects.requireNonNull(moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found")));
    }

    public List<ModuleAdminDTO> getModules() {
        return moduleRepository.findAll()
                .stream()
                .map(ModuleService::toAdminDTO)
                .toList();
    }

    public static ModuleDTO toDTO(Module module) {
        return new ModuleDTO(
                module.moduleId,
                module.moduleName,
                SpecialityService.toLazyDTO(module.speciality),
                TeacherService.toLazyDTO(module.teachers),
                SessionService.toLazyDTO(module.sessions)
        );
    }
    public static ModuleLazyDTO toLazyDTO(Module module) {
        return new ModuleLazyDTO(
                module.moduleId,
                module.moduleName,
                module.speciality.specialityId
        );
    }
    public static ModuleAdminDTO toAdminDTO(Module module) {
        return new ModuleAdminDTO(
                module.moduleId,
                module.moduleName,
                SpecialityService.toLazyDTO(module.speciality)
        );
    }

    public static Set<ModuleLazyDTO> toLazyDTO(Set<Module> modules) {
        return modules.stream().map(ModuleService::toLazyDTO).collect(Collectors.toSet());
    }

    public void assignTeacher(UUID moduleId, String teacherId) {
        Module module = getModule(moduleId);
        Teacher teacher = TeacherService.getTeacher(teacherId);
        if (module != null && teacher != null) {
            module.assignTeacher(teacher);
            teacher.assignModule(module);
            moduleRepository.save(module);
            teacherRepository.save(teacher);
        }

    }

    public Set<TeacherLazyDTO> getTeachersOfModule(UUID moduleId) {
        return TeacherService.toLazyDTO(moduleRepository.findById(moduleId).orElseThrow(()-> new IllegalArgumentException("Module not found")).teachers);
    }

    public void deleteModule(UUID moduleId) {
        Module module = cleanModule(moduleId);
        moduleRepository.delete(module);
    }

    private Module cleanModule(UUID moduleId){
        Module module = getModule(moduleId);

        for (Teacher teacher : module.teachers) {
            teacher.getModules().remove(module);
        }
        module.teachers.clear();

        moduleRepository.save(module);
        moduleRepository.delete(module);

        return module;
    }

    public void decoupleTeacherModule(UUID moduleId, String userId) {
        Module module = getModule(moduleId);
        module.teachers.removeIf(teacher -> teacher.userId.equals(userId));
        moduleRepository.save(module);
        Teacher teacher = teacherRepository.findById(userId).orElseThrow();
        teacher.getModules().remove(module);
        teacherRepository.save(teacher);
    }

    public void reassignModule(UUID moduleId, String specialityId) {
        Module module = getModule(moduleId);

        Speciality oldSpeciality = SpecialityService.getSpeciality(module.speciality.specialityId);
        System.out.println("old speciality:"+ oldSpeciality.specialityName);
        Speciality newSpeciality = SpecialityService.getSpeciality(UUID.fromString(specialityId));
        System.out.println("new speciality:"+ newSpeciality.specialityName);
        module.speciality = null;
        oldSpeciality.modules.remove(module);

        module.speciality = newSpeciality;
        newSpeciality.modules.add(module);

        System.out.println(module.speciality.specialityName);
        moduleRepository.save(module);
        specialityRepository.save(oldSpeciality);
    }

    public Set<StudentLazyDTO> getStudentsOfModule(UUID moduleId) {
        Module module = getModule(moduleId);
        return module.speciality.students
                .stream().map(StudentService::toLazyDTO)
                .collect(Collectors.toSet());
    }
}
