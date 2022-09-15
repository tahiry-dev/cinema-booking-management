package com.example.cinemamanagement.repository;

import com.example.cinemamanagement.model.Session;
import com.example.cinemamanagement.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Iterable<Ticket> findAllBySession(Session session);
    Long countBySession(Session session);
}
