package com.example.cinemamanagement.service;


import com.example.cinemamanagement.exception.BusinessException;
import com.example.cinemamanagement.exception.EntityDoesNotExistsException;
import com.example.cinemamanagement.model.Session;
import com.example.cinemamanagement.model.Ticket;
import com.example.cinemamanagement.repository.SessionRepository;
import com.example.cinemamanagement.repository.TicketRepository;
import com.example.cinemamanagement.utility.IterableUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private SessionRepository sessionRepository;


    @Override
    public Long newTicket(Long sessionId, String seat, BigDecimal price) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if (!sessionOptional.isPresent()) throw new EntityDoesNotExistsException("Session, id=" + sessionId);

        Long amountOfTicketsInSession = ticketRepository.countBySession(sessionOptional.get());
        if (amountOfTicketsInSession==sessionOptional.get().getRoom().getCapacity()) throw new BusinessException("All tickets have been sold out. SessionId="+ sessionId);

        Ticket ticket = new Ticket(null, seat, price);
        sessionOptional.get().addTicket(ticket);

        ticketRepository.save(ticket);
        return ticket.getId();
    }

    @Override
    public List<Ticket> getAllTicketsForSession(Long sessionId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);

        Iterable<Ticket> tickets = ticketRepository.findAllBySession(sessionOptional.get());
        return IterableUtils.iterableToList(tickets);
    }

    @Override
    public void cancelTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}

