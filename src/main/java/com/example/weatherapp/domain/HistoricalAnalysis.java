package com.example.weatherapp.domain;

public record HistoricalAnalysis(double averageTemperature,
                                 double minTemperature,
                                 double maxTemperature,
                                 double averageWindSpeed,
                                 double averageWindDirection,
                                 double averageWeatherCode) {

}
