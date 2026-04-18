package com.softwarebhayya.tmdb.repo;

import com.softwarebhayya.tmdb.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    public Rating findByName(String name);

    List<Rating> findAllByAvgRatingBetween(double min, double max);
}
