package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Movie;
import com.example.cinemamanagement.model.Screening;
import com.example.cinemamanagement.repository.MovieRepository;
import com.example.cinemamanagement.repository.ScreeningRepository;
import com.example.cinemamanagement.utility.IterableUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService{
    private ScreeningRepository screeningRepository ;
    private MovieRepository movieRepository;


    @Override
    public Long createScreening(String name, LocalDateTime startDate, List<Long> movieIds) {
        List<Movie> movies = new ArrayList<>();
        for (Long movieId: movieIds
        ) {
            Optional<Movie> movieOptional = movieRepository.findById(movieId);
            movies.add(movieOptional.get());
        }
        Screening screening = new Screening(null, name, startDate);
        screening.setMovies(movies);
        for (Movie movie: movies
        ) {
            movie.getScreenings().add(screening);
        }
        screeningRepository.save(screening);
        return screening.getId();
    }

    @Override
    public Optional<Screening> getScreening(Long marathonId) {
        return screeningRepository.findById(marathonId);
    }

    @Override
    public List<Screening> getAllScreenings() {
        return IterableUtils.iterableToList(screeningRepository.findAll());
    }

    @Override
    @Transactional
    public void removeScreening(Long screeningId) {
        screeningRepository.deleteById(screeningId);
    }
}

