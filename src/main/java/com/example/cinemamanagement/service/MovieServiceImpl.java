package com.example.cinemamanagement.service;

import com.example.cinemamanagement.exception.EntityDoesNotExistsException;
import com.example.cinemamanagement.model.Genres;
import com.example.cinemamanagement.model.Movie;
import com.example.cinemamanagement.model.Poster;
import com.example.cinemamanagement.repository.MovieRepository;
import com.example.cinemamanagement.repository.PosterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private PosterRepository posterRepository;

    @Override
    @Transactional
    public Long createMovie(String title, Genres category, Integer length, String description, Integer requiredAge, String posterFilePath) {
        Movie movie = new Movie(null, title, category, length, description, requiredAge);
        movieRepository.save(movie);
        if(posterFilePath!=null) createPoster(movie, posterFilePath);
        return movie.getId();
    }

    private void createPoster(Movie movie, String posterFilePath){
        Poster poster = new Poster(null, posterFilePath);
        poster.setMovie(movie);
        posterRepository.save(poster);
    }


    @Override
    public Optional<Movie> getMovie(Long movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public Page<Movie> getMoviesInCategory(Genres category, Pageable pageable) {
        return movieRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Movie> getMovieByPartOfTitle(String partOfTitle, Pageable pageable) {
        return movieRepository.findByTitleContaining(partOfTitle, pageable);
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void updateMovie(Long movieId, String title, Genres category, Integer length, String description, Integer requiredAge, String posterFilePath) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        Movie movie = movieOptional.get();
        movie.setTitle(title);
        movie.setCategory(category);
        movie.setLength(length);
        movie.setDescription(description);
        movie.setRequiredAge(requiredAge);

        movieRepository.save(movie);

        Optional<Poster> posterOptional = posterRepository.findByMovie(movie);
        if (posterOptional.isPresent()){
            Poster poster = posterOptional.get();
            if(posterFilePath!=null){
                poster.setFilePath(posterFilePath);
                posterRepository.save(poster);
            }
            else {
                posterRepository.delete(poster);
            }
        }else if (posterFilePath!=null){
            createPoster(movie, posterFilePath);
        }
    }

    @Override
    @Transactional
    public void removeMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        Poster poster = posterRepository.findByMovie(movie).get();
        movieRepository.deleteById(movieId);
        posterRepository.delete(poster);

    }
}
