package com.example.weatherapp.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeatherResponse {
    private double latitude;
    private double longitude;
    private CurrentWeather current_weather;
}
