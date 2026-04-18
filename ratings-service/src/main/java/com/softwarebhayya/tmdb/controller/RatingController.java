package com.softwarebhayya.tmdb.controller;

import com.softwarebhayya.tmdb.model.Rating;
import com.softwarebhayya.tmdb.model.RatingRequest;
import com.softwarebhayya.tmdb.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/{name}")
    public ResponseEntity<Rating> getAllRatings(@PathVariable String name) {
        Rating rating = ratingService.fetchRating(name);
        log.info("Returning rating for movie {}", name);
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<Rating> updateRating(@RequestBody RatingRequest request) {
        log.info("returning new average for movie {}", request.getName());
      Rating rating  = ratingService.updateAverage(request.getName(), request.getStars());
        return ResponseEntity.ok(rating);
    }


}
