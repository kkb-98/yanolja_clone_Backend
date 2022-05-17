package com.example.demo.src.accommodation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccommodationTop {
    private String accName;
    private String category;
    private String location;
    private int reserveCount;
}
