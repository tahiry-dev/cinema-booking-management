package com.example.cinemamanagement.repository;

import com.example.cinemamanagement.model.Genres;
import com.example.cinemamanagement.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Page<Movie> findByCategory(Genres genres, Pageable pageable);

    Page<Movie> findByTitleContaining(String partOfTitle, Pageable pageable);
}
