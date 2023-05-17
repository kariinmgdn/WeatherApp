package com.example.weatherapp.dto.location;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Geo {

    private String host;
    private Double latitude;
    private Double longitude;
}
