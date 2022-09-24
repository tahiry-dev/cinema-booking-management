package com.example.cinemamanagement;

import com.example.cinemamanagement.model.*;
import com.example.cinemamanagement.service.ScreeningService;
import com.example.cinemamanagement.service.MovieService;
import com.example.cinemamanagement.service.SessionService;
import com.example.cinemamanagement.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class CinemaRunner implements CommandLineRunner {
    private final static Logger LOG = LoggerFactory.getLogger(CinemaRunner.class);

    private ScreeningService screeningService;
    private MovieService movieService;
    private SessionService sessionService;
    private TicketService ticketService;

    public CinemaRunner(ScreeningService screeningService, MovieService movieService, SessionService sessionService, TicketService ticketService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.sessionService = sessionService;
        this.ticketService = ticketService;
    }

    @Override
    public void run(String... args) throws Exception {
        movieServiceInvocations();
        sessionServiceInvocations();
        ticketServiceInvocations();
        screeningServiceInvocations();
    }

    private void movieServiceInvocations(){
        Page<Movie> allMovies = movieService.getAllMovies(PageRequest.of(1,3, Sort.by("title")));
        LOG.info("1. All Movies. Total Elements={}, Total Pages={} ", allMovies.getTotalElements(), allMovies.getTotalPages());
        allMovies.get().forEach(movie -> LOG.info( "  {}", movie));

        Page<Movie> moviesByPartOfTitle = movieService.getMovieByPartOfTitle("Gry", Pageable.unpaged());
        LOG.info("2. MoviesByPartOfTittle. Total element = {}, Total Pages ={}", moviesByPartOfTitle.getTotalElements(), moviesByPartOfTitle.getTotalPages());
        moviesByPartOfTitle.get().forEach(movie -> LOG.info("  {}", movie));

        Page<Movie> comedyMovies = movieService.getMoviesInCategory(Genres.COMEDY, Pageable.unpaged());
        LOG.info("3. Comedy movies. Total elements= {}, Total pages= {}.", comedyMovies.getTotalElements(), comedyMovies.getTotalPages());
        comedyMovies.get().forEach(movie -> LOG.info("  {}", movie));

        Movie movieWithId7 = movieService.getMovie(7L).get();
        LOG.info("4. Movie with id 7 - {}", movieWithId7);
        movieService.updateMovie(7L, movieWithId7.getTitle(), Genres.DRAMA, 140, movieWithId7.getDescription(),18, "/images/none.png");;

        Movie movieWithId7AfterUpdate = movieService.getMovie(7L).get();
        LOG.info("5. Movie with id 7 after update: {}", movieWithId7AfterUpdate);

        Long newMovieId = movieService.createMovie("Jak wytresować smoka", Genres.FAMILY, 104, "" +
                "Witamy w krainie smoków", 12,"/images/smok.png" );
        Movie movie = movieService.getMovie(newMovieId).get();
        LOG.info("6. New movie: {}", movie);
    }

    private void sessionServiceInvocations(){
        List<Session> sessionsIn02052019 = sessionService.getSessionsInDate(LocalDate.of(2019,5,2));
        LOG.info("7. Session in 2 May 2019, ammount of sessions {} ",sessionsIn02052019.size() );
        sessionsIn02052019.forEach(session -> LOG.info("  {}", session));

        Session sessionWithId15 = sessionService.getSession(15L).get();
        LOG.info("8. Session with id 5: {}, movie title: {}, room: {}", sessionWithId15, sessionWithId15.getMovie().getTitle(), sessionWithId15.getRoom().getName());

        Session sessionWithId5WithTickets = sessionService.getSession(15L).get();
        LOG.info("9. Session wit id 5 witch tickets: {}, movie title: {}, room: {}", sessionWithId5WithTickets, sessionWithId5WithTickets.getMovie().getTitle(),
                sessionWithId5WithTickets.getRoom().getName());
        sessionWithId5WithTickets.getTickets().forEach(ticket -> LOG.info("  {}", ticket));

        Long newSessionId = sessionService.createSession(11L, 4L, LocalDateTime.of(2019,4, 12,10,30));
        Session newSession = sessionService.getSession(newSessionId).get();
        LOG.info("10. New session: {}", newSession);
        sessionService.removeSession(16L);
    }

    private void ticketServiceInvocations(){
        List<Ticket> ticketsOnSessionWithId15 = ticketService.getAllTicketsForSession(15L);
        LOG.info("11. Tickets on session 15, ammount of tickets: {}", ticketsOnSessionWithId15.size());
        ticketsOnSessionWithId15.forEach(ticket -> LOG.info("  {}", ticket));

        Long newTicketId = ticketService.newTicket(15L, "r2m5", new BigDecimal("22.50"));
        LOG.info("12. New ticket id: {}", newTicketId);

        ticketService.cancelTicket(18L);

        List<Ticket> ticketsOnSessionWithId15AfterUpdates = ticketService.getAllTicketsForSession(15L);
        LOG.info("13. Tickets on session 15 after update. amount of tickets: {}", ticketsOnSessionWithId15AfterUpdates.size());
        ticketsOnSessionWithId15AfterUpdates.forEach(ticket -> LOG.info("  {}", ticket));

    }

    private void screeningServiceInvocations(){
        Long newScreeningId = screeningService.createScreening("Screening horrorów", LocalDateTime.of(2018,10,30, 20,0), Arrays.asList(6L, 8L, 9L));
        Screening newScreening = screeningService.getScreening(newScreeningId).get();
        LOG.info("14. New Screening: {}", newScreening);
        newScreening.getMovies().forEach(movie -> LOG.info("  {}", movie));

        List<Screening> screenings = screeningService.getAllScreenings();
        LOG.info("15. Screening, amount of screenings: {}", screenings.size());
        screenings.forEach(screening -> LOG.info("  {}", screening));

        screeningService.removeScreening(newScreeningId);
    }

}
