package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionService {

    Long createSession(Long movieId, Long roomId, LocalDateTime startTime);

    Optional<Session> getSession(Long sessionId);

    Optional<Session> getSessionWithTickets(Long sessionId);

    List<Session> getSessionsInDate(LocalDate date);

    void removeSession(Long sessionId);
}
