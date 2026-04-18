package com.softwarebhayya.tmbd.controller;

import com.softwarebhayya.tmbd.model.Rating;
import com.softwarebhayya.tmbd.model.RatingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/public")
@Slf4j
public class publicController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${rating-service.url}")
    private String ratingServiceUrl;

    @PostMapping
    public ResponseEntity<Object> addRating(@RequestBody RatingRequest ratingRequest)
    {
        Rating rating;
        try{
            rating = restTemplate.postForObject(ratingServiceUrl, ratingRequest, Rating.class);
            return ResponseEntity.ok().body(rating);
        }catch (HttpStatusCodeException e)
        {
            log.error("Error adding rating: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

}
