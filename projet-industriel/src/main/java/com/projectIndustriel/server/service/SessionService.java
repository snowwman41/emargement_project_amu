package com.projectIndustriel.server.service;

import com.projectIndustriel.server.dto.CodeDTO;
import com.projectIndustriel.server.dto.SessionDTO;
import com.projectIndustriel.server.dto.SessionLazyDTO;
import com.projectIndustriel.server.entities.*;
import com.projectIndustriel.server.entities.Module;
import com.projectIndustriel.server.repositories.SessionRepository;
import com.projectIndustriel.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private static SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(
            SessionRepository sessionRepository,
            UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public void addSession(SessionDTO sessionDTO) {
        sessionRepository.save(toEntity(sessionDTO));
    }

    public void closeSession(UUID sessionId) {
        Session session = getSession(sessionId);
        session.deactivate();
        sessionRepository.save(session);
    }

    public Set<SessionLazyDTO> getSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(SessionService::toLazyDTO)
                .collect(Collectors.toSet());
    }

    private static SessionDTO toDTO(Session session){
        return new SessionDTO(
                session.id,
                session.sessionName,
                ModuleService.toLazyDTO(session.module),
                session.startTime,
                session.endTime,
                session.active,
                SignatureService.toDTO(session.signatures)
        );
    }

    public static Set<SessionLazyDTO> toLazyDTO(Set<Session> sessions){
        return sessions.stream().map(SessionService::toLazyDTO).collect(Collectors.toSet());
    }

    private static SessionLazyDTO toLazyDTO(Session session){
        return new SessionLazyDTO(
                session.id,
                session.sessionName,
                session.module.moduleId,
                session.startTime,
                session.endTime,
                session.active
        );
    }

    private Session toEntity(SessionDTO sessionDTO){
        return new Session(
                sessionDTO.sessionName(),
                ModuleService.getModule(sessionDTO.module().moduleId()),
                sessionDTO.startTime(),
                sessionDTO.endTime()
        );
    }

    public SessionDTO getSessionDTO(UUID id){
        return toDTO(getSession(id));
    }

    public static Session getSession(UUID id){
        return Objects.requireNonNull(sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found")));
    }

    public void openSession(UUID sessionId){
        Session session = getSession(sessionId);
        session.activate();
        sessionRepository.save(session);
    }

public Set<SessionLazyDTO> getSessionsOfUserOfDate(String userId, String date) {
    User entity = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    Set<SessionLazyDTO> sessions = new HashSet<>();
    for (Module module : entity.getModules())
        sessions.addAll(module.sessions
                .stream()
                .filter(s -> s.isOnDate(date))
                .map(SessionService::toLazyDTO)
                .collect(Collectors.toSet()));
    return sessions;
}

    public List<SessionLazyDTO> getSessionsOfModule(UUID moduleId) {
        return sessionRepository.findByModule_moduleId(moduleId)
                .stream().map(SessionService::toLazyDTO).toList();
    }

    public Set<CodeDTO> getCode(UUID sessionId) {
        Set<Code> codes = new HashSet<>();
        for (Teacher teacher : getSession(sessionId).module.teachers)
            if(CodeService.getCode(teacher.userId).isPresent())
                codes.add(CodeService.getCode(teacher.userId).get());
        return codes.stream().map(CodeService::toDTO).collect(Collectors.toSet());
    }

    public void deleteSession(UUID sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
