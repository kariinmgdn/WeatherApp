package com.example.weatherapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "Weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ip;
    private LocalDateTime time;
    private double latitude;
    private double longitude;
    private double temperature;
    @Column(name = "wind_speed")
    private double windSpeed;
    @Column(name = "wind_direction")
    private double windDirection;
    @Column(name = "weather_code")
    private double weatherCode;

    public Weather(String ip,
                   double latitude,
                   double longitude,
                   double temperature,
                   double windSpeed,
                   double windDirection,
                   double weatherCode) {
        this.ip = ip;
        this.time = LocalDateTime.now();
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherCode = weatherCode;
    }
}
