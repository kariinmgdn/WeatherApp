package com.example.weatherapp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(weather.latitude, latitude) == 0
                && Double.compare(weather.longitude, longitude) == 0
                && Double.compare(weather.temperature, temperature) == 0
                && Double.compare(weather.windSpeed, windSpeed) == 0
                && Double.compare(weather.windDirection, windDirection) == 0
                && Double.compare(weather.weatherCode, weatherCode) == 0
                && Objects.equals(ip, weather.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, latitude, longitude, temperature, windSpeed, windDirection, weatherCode);
    }
}
