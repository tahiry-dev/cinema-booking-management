package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Movie;
import com.example.cinemamanagement.model.Room;
import com.example.cinemamanagement.model.Session;
import com.example.cinemamanagement.repository.MovieRepository;
import com.example.cinemamanagement.repository.RoomRepository;
import com.example.cinemamanagement.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SessionServiceImpl implements  SessionService {

    private SessionRepository sessionRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;

    @Override
    public Long createSession(Long movieId, Long roomId, LocalDateTime startTime) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        Optional<Room> roomOptional = roomRepository.findById(roomId);

        Session session = new Session(null, startTime);
        session.setRoom(roomOptional.get());
        session.setMovie(movieOptional.get());

        sessionRepository.save(session);
        return session.getId();
    }

    @Override
    public Optional<Session> getSession(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    public Optional<Session> getSessionWithTickets(Long sessionId) {
        return sessionRepository.readById(sessionId);
    }

    @Override
    public List<Session> getSessionsInDate(LocalDate date) {
        return sessionRepository.findAllByStartDate(Date.valueOf(date));
    }

    @Override
    public void removeSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}


