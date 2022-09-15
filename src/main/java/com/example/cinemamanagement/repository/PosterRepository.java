package com.example.cinemamanagement.repository;

import com.example.cinemamanagement.model.Movie;
import com.example.cinemamanagement.model.Poster;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PosterRepository extends CrudRepository<Poster, Long> {
    Optional<Poster> findByMovie(Movie movie);
}
