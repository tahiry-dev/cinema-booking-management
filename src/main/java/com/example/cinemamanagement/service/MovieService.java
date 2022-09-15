package com.example.cinemamanagement.service;

import com.example.cinemamanagement.model.Genres;
import com.example.cinemamanagement.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieService {
    Long createMovie(String title, Genres category, Integer length, String description, Integer requiredAge, String posterFilePath);

    Optional<Movie> getMovie(Long movieId);

    Page<Movie> getMoviesInCategory(Genres category, Pageable pageable);

    Page<Movie> getMoviesInCateory(Genres category, Pageable pageable);

    Page<Movie> getMovieByPartOfTitle(String partOfTitle, Pageable pageable);

    Page<Movie> getAllMovies(Pageable pageable);

    void updateMovie(Long movieId, String title, Genres category, Integer length, String description, Integer requiredAge, String posterFilePath);

    void removeMovie(Long movieId);
}
