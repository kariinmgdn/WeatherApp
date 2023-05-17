package com.example.weatherapp.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CurrentWeather {
    private double temperature;
    private double windspeed;
    private double winddirection;
    private int weathercode;
}
