package com.softwarebhayya.tmbd.controller;


import com.softwarebhayya.tmbd.model.Movie;
import com.softwarebhayya.tmbd.model.MovieRating;
import com.softwarebhayya.tmbd.model.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${movie-service.url}")
    private String movieServiceUrl;

    @Value("${rating-service.url}")
    private String ratingServiceUrl;

    @PostMapping
    public ResponseEntity<Object> addMovie(@RequestBody Movie movie) {

        try {
            log.info("Adding movie: {}", movie);
            Movie savedMovie = restTemplate.postForObject(movieServiceUrl, movie, Movie.class);
            return ResponseEntity.ok().body(savedMovie);
        } catch (HttpStatusCodeException e) {
            log.error("Error adding movie: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).contentType(MediaType.APPLICATION_JSON).
                    body(e.getResponseBodyAsString());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {

        try {
            log.info("Updating movie: {}", id);
            restTemplate.put(movieServiceUrl + "/" + id, movie);
            return ResponseEntity.ok().build();
        } catch (HttpStatusCodeException e) {
            log.error("Error updating movie: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).contentType(MediaType.APPLICATION_JSON).
                    body(e.getResponseBodyAsString());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchMovieAndRating(@PathVariable Long id) {

        log.info("Getting movie: {}", id);
        Movie movie;
        try {
            movie = restTemplate.getForObject(movieServiceUrl + "/" + id, Movie.class);
        } catch (HttpStatusCodeException e) {
            log.error("Error fetching movie: {}", e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
            } else {
                return ResponseEntity.status(e.getStatusCode()).contentType(MediaType.APPLICATION_JSON).
                        body(e.getResponseBodyAsString());
            }
        }

        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }

        Rating rating;
        try {
            rating = restTemplate.getForObject(ratingServiceUrl + "/" + movie.getName(), Rating.class);
        } catch (HttpStatusCodeException e) {
            log.error("Error retrieving movie: {}", e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                rating = new Rating(null, 0.0, movie.getName(), 0);
            } else {
                rating = new Rating(null, 0.0, movie.getName(), 0);
            }
        } catch (RestClientException e) {
            log.error("Exception: {}", e.getMessage());
            rating = new Rating(null, 0.0, movie.getName(), 0);

        }

        MovieRating movieRating = new MovieRating();
        movieRating.setMovie(movie);
        movieRating.setRating(rating);
        return ResponseEntity.ok().body(movieRating);

    }

}



