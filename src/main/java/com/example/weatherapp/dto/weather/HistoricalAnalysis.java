package com.example.weatherapp.dto.weather;

public record HistoricalAnalysis(double averageTemperature,
                                 double minTemperature,
                                 double maxTemperature,
                                 double averageWindSpeed,
                                 double averageWindDirection,
                                 double averageWeatherCode) {

}
