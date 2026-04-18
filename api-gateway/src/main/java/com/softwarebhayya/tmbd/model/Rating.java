package com.softwarebhayya.tmbd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {


    private Long id;
    private Double avgRating;
    private String name;
    private int count;
}
