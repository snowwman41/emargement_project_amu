package com.projectIndustriel.server.controller;

import com.projectIndustriel.server.dto.CodeDTO;
import com.projectIndustriel.server.dto.SessionDTO;
import com.projectIndustriel.server.dto.SessionLazyDTO;
import com.projectIndustriel.server.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class SessionController {

    SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/addSession")
    public List<SessionLazyDTO> addSession(@RequestBody SessionDTO sessionDTO) {
        sessionService.addSession(sessionDTO);
        return sessionService.getSessionsOfModule(sessionDTO.module().moduleId());
    }

    @PostMapping("/addSessionList")
    public Set<SessionLazyDTO> addSessionList(@RequestBody List<SessionDTO> sessions) {
        for (SessionDTO sessionDTO : sessions) {
            sessionService.addSession(sessionDTO);
        }
        return sessionService.getSessions();
    }

    @GetMapping("/openSession/{sessionId}")
    public SessionDTO openSession(@PathVariable UUID sessionId) {
        sessionService.openSession(sessionId);
        return sessionService.getSessionDTO(sessionId);
    }

    @GetMapping("/closeSession/{sessionId}")
    public SessionDTO closeSession(@PathVariable UUID sessionId) {
        sessionService.closeSession(sessionId);
        return sessionService.getSessionDTO(sessionId);
    }

    @GetMapping("/sessions")
    public Set<SessionLazyDTO> getSessions() {
        return sessionService.getSessions();
    }

    @GetMapping("/sessions/{sessionId}")
    public SessionDTO getSession(@PathVariable UUID sessionId) {
        return sessionService.getSessionDTO(sessionId);
    }

    @GetMapping("/sessions/{sessionId}/code")
    public Set<CodeDTO> getCode(@PathVariable UUID sessionId) {
        return sessionService.getCode(sessionId);
    }

    @PostMapping("/sessions/{sessionId}/delete")
    public void deleteSession(@PathVariable UUID sessionId) {
        sessionService.deleteSession(sessionId);
    }

    @GetMapping("/sessions/{userId}/{date}")
    public Set<SessionLazyDTO> getSessionsOfUserOnDate(@PathVariable String userId,
                                            @PathVariable String date) {
        return sessionService.getSessionsOfUserOfDate(userId, date);
    }
}
