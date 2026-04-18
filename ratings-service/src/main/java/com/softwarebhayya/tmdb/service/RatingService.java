package com.softwarebhayya.tmdb.service;

import com.softwarebhayya.tmdb.model.Rating;
import com.softwarebhayya.tmdb.repo.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public Rating updateAverage(String name, double stars){
        Rating rating = ratingRepository.findByName(name);
        if(rating == null){
            rating = new Rating();
            rating.setName(name);
            rating.setAvgRating(stars);
            rating.setCount(1);
        }else {
            int count = rating.getCount();
            double newAverage = (rating.getAvgRating() * rating.getCount() + stars) / (rating.getCount() + 1);
            rating.setAvgRating(newAverage);
            rating.setCount(++count);
        }
       return ratingRepository.save(rating);

    }

    public Rating fetchRating(String name){
        Rating rating = ratingRepository.findByName(name);
        if(rating == null){
            throw new RuntimeException("Movie not found with name " + name);
        }
        return rating;
    }

}
