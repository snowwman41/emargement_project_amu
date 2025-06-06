package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.ModuleDTO;
import com.projectIndustriel.server.dto.SpecialityDTO;
import com.projectIndustriel.server.dto.SpecialityLazyDTO;
import com.projectIndustriel.server.dto.StudentLazyDTO;
import com.projectIndustriel.server.entities.Speciality;
import com.projectIndustriel.server.entities.Student;
import com.projectIndustriel.server.repositories.ModuleRepository;
import com.projectIndustriel.server.repositories.SpecialityRepository;
import com.projectIndustriel.server.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpecialityService {

    private static SpecialityRepository specialityRepository;
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;

    public SpecialityService(SpecialityRepository specialityRepository,
                             StudentRepository studentRepository,
                             ModuleRepository moduleRepository) {
        this.specialityRepository = specialityRepository;
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
    }

    public static Set<SpecialityLazyDTO> toLazyDTO(Set<Speciality> specialities) {
        return specialities.stream().map(SpecialityService::toLazyDTO).collect(Collectors.toSet());
    }

    public static SpecialityLazyDTO toLazyDTO(Speciality speciality) {
        return new SpecialityLazyDTO(
                speciality.specialityId,
                speciality.specialityName
        );
    }

    public SpecialityDTO addSpeciality(SpecialityDTO specialityDTO) {
        List<Speciality> specialties = specialityRepository.findAll();
        for (Speciality speciality : specialties) {
            if (speciality.equals(specialityDTO))
                return toDTO(speciality);
        }
        Speciality newSpeciality = toEntity(specialityDTO);
        specialityRepository.save(newSpeciality);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getSpecialityDTO(newSpeciality.specialityId);
    }

    private Speciality toEntity(SpecialityDTO specialityDTO) {
        return new Speciality(
                specialityDTO.specialityName()
        );
    }

    public List<SpecialityLazyDTO> getSpecialities() {
        return toLazyDTO(specialityRepository.findAll());
    }

    public SpecialityDTO getSpecialityDTO(UUID specialityId) {
        return toDTO(getSpeciality(specialityId));
    }

    public static Speciality getSpeciality(UUID specialityId) {
        System.out.println(specialityRepository.findById(specialityId)
                .orElseThrow(() -> new IllegalArgumentException("Speciality not found.")).students);
        return specialityRepository.findById(specialityId)
                .orElseThrow(() -> new IllegalArgumentException("Speciality not found."));
    }

    public static SpecialityDTO toDTO(Speciality speciality) {
        return new SpecialityDTO(
                speciality.specialityId,
                speciality.specialityName,
                ModuleService.toLazyDTO(speciality.modules),
                StudentService.toLazyDTO(speciality.students)
//                speciality.students == null ? null : StudentService.toLazyDTO(speciality.students)
        );
    }

    private List<SpecialityLazyDTO> toLazyDTO(List<Speciality> specialities) {
        return specialities.stream().map(SpecialityService::toLazyDTO).toList();
    }

    public void addStudent(UUID specialityId, String studentId) {
        Speciality speciality = getSpeciality(specialityId);
        Student student = StudentService.getStudent(studentId);
        speciality.addStudent(student);
        student.assignSpeciality(speciality);
        specialityRepository.save(speciality);
        studentRepository.save(student);
    }

    public Set<StudentLazyDTO> getStudentsOfSpeciality(UUID specialityId) {
        return getSpeciality(specialityId)
                .students
                .stream()
                .map(StudentService::toLazyDTO)
                .collect(Collectors.toSet());
    }

    public void delete(UUID id) {
        specialityRepository.deleteById(id);
    }

    public Set<ModuleDTO> getModulesOfSpeciality(UUID specialityId) {
        return moduleRepository.findBySpecialitySpecialityId(specialityId)
                .stream().map(ModuleService :: toDTO)
                .collect(Collectors.toSet());
    }

    public void decoupleStudent(UUID specialityId, String userId) {
        Speciality speciality = getSpeciality(specialityId);
        Student student = StudentService.getStudent(userId);
        speciality.students.remove(student);
        student.specialities.remove(speciality);
        specialityRepository.save(speciality);
        studentRepository.save(student);
    }
}