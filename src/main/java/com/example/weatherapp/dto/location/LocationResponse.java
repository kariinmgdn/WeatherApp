package com.example.weatherapp.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocationResponse {

    private String status;
    private String description;
    private Data data;
}
