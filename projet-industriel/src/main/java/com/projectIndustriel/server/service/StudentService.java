package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.*;
import com.projectIndustriel.server.entities.*;
import com.projectIndustriel.server.entities.Module;
import com.projectIndustriel.server.repositories.SignatureRepository;
import com.projectIndustriel.server.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static StudentRepository studentRepository;
    private final SignatureRepository signatureRepository;

    public StudentService(StudentRepository studentRepository,
                          SignatureRepository signatureRepository) {
        this.studentRepository = studentRepository;
        this.signatureRepository = signatureRepository;
    }

    public StudentDTO getStudentDTO(String userId) throws IllegalArgumentException {
        return toDTO(getStudent(userId));
    }

    public static Student getStudent(String userId) throws IllegalArgumentException {
        return studentRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    public List<ModuleLazyDTO> getModules(String userId) throws Exception {
        return getStudent(userId)
                .getModules()
                .stream()
                .map(ModuleService::toLazyDTO).toList();
    }

    public static StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.userId,
                SpecialityService.toLazyDTO(student.specialities),
                student.firstName,
                student.lastName,
                student.email
        );
    }

    public static StudentLazyDTO toLazyDTO(Student student) {
        return new StudentLazyDTO(
                student.userId,
                student.firstName,
                student.lastName,
                student.email
        );
    }

    public static Set<StudentLazyDTO> toLazyDTO(Set<Student> students) {
        return students.stream()
                .map(StudentService::toLazyDTO)
                .collect(Collectors.toSet());
    }

    public Student toEntity(StudentDTO studentDTO) {
        return new Student(
                studentDTO.userId(),
                studentDTO.firstName(),
                studentDTO.lastName(),
                studentDTO.email()
        );
    }

    public List<StudentLazyDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentService::toLazyDTO)
                .toList();
    }

    public void addStudent(StudentDTO studentDTO) {
        if (studentRepository.findById(studentDTO.userId()).isEmpty())
            studentRepository.save(toEntity(studentDTO));

    }

    private Set<Module> getModuleEntitiesOfStudent(String userId) throws IllegalArgumentException {
        Student student = getStudent(userId);
        Set<Module> modules = new HashSet<>();
        for(Speciality spec : student.specialities){
            modules.addAll(spec.modules);
        }
        return modules;
    }

    public Set<ModuleLazyDTO> getModulesOfStudent(String userId) throws IllegalArgumentException {
        return ModuleService.toLazyDTO(getModuleEntitiesOfStudent(userId));
    }

    private Set<Session> getSessionEntitiesOfStudent(String userId) throws IllegalArgumentException {
        //TODO maybe consider directly requesting sessions via SQL command
        Set<Session> sessions = new HashSet<>();
        for(Module mod : getModuleEntitiesOfStudent(userId))
            sessions.addAll(mod.sessions);
        return sessions;
    }

    public Set<SessionLazyDTO> getActiveSessionsOfStudent(String userId) throws IllegalArgumentException {
        return SessionService.toLazyDTO(getSessionEntitiesOfStudent(userId)
                .stream()
                .filter(s -> s.active)
                .collect(Collectors.toSet()));
    }

    public List<Boolean> studentSignedToSessions(String userId, List<SessionLazyDTO> sessions) {
        return sessions.stream()
                .map(session -> signatureRepository.findBySession_IdAndStudent_UserId(session.sessionId(), userId).isPresent())
                .toList();
    }

    public Set<StudentDTO> getStudentsAdmin() {
        return studentRepository.findAll()
                .stream().map(StudentService::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<SpecialityLazyDTO> getSpecialitiesOfStudent(String userId) {
        return getStudent(userId).specialities
                .stream()
                .map(SpecialityService::toLazyDTO)
                .collect(Collectors.toSet());
    }

    public static void cleanDelete(String userId) throws IllegalArgumentException {
        Student student = getStudent(userId);

        for (Speciality speciality : student.specialities) {
            speciality.students.remove(student);
        }
        student.specialities.clear();

        for (Signature signature : student.signatures) {
            signature.student = null;
        }
        student.signatures.clear();

        studentRepository.save(student);
        studentRepository.delete(student);
    }
}
