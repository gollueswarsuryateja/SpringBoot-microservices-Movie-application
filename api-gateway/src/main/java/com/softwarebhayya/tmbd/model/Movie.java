package com.softwarebhayya.tmbd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Movie {

    private Long id;
    private String name;
    private String director;

    @ElementCollection
    private List<String> actors =  new ArrayList<>();

}
