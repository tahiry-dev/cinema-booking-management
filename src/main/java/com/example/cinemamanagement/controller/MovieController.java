package com.example.cinemamanagement.controller;

import com.example.cinemamanagement.model.Genres;
import com.example.cinemamanagement.model.Movie;
import com.example.cinemamanagement.service.MovieService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/cinema/movie")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;


    @GetMapping("")
    public Stream<Movie> getAllMovies(){
        return movieService.getAllMovies(Pageable.unpaged()).get();
    }
    @GetMapping("/sorted")
    public Stream<Movie> getAllMoviesSorted(){
        return movieService.getAllMovies(PageRequest.of(1,3, Sort.by("title"))).get();
    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie){
        Long id = movieService.createMovie(movie.getTitle(), movie.getCategory(), movie.getLength(), movie.getDescription(), movie.getRequiredAge(),"path" );
        movie.setId(id);
        return movie;
    }

    @PostMapping("/add1")
    public Movie addMovie1(@RequestBody JsonNode jsonNode){
        Long idMovie = movieService.createMovie(jsonNode.get("title").asText(),
                Genres.valueOf(jsonNode.get("category").asText()),
                jsonNode.get("length").asInt(),
                jsonNode.get("description").asText(), jsonNode.get("requiredAge").asInt(),
                jsonNode.get("filePath").asText());
        Movie movie = movieService.getMovie(idMovie).get();

        return movie;
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") Long id){
        return movieService.getMovie(id).get();
    }

    @DeleteMapping("/del/{id}")
    public void removeMovie(@PathVariable("id") Long movieId){

        movieService.removeMovie(movieId);
    }

}
