package com.softwarebhayya.tmbd.model;

import lombok.Data;
import org.apache.catalina.User;
@Data
public class MovieRating {

    private Movie movie;
    private Rating rating;
}
