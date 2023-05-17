package com.example.weatherapp;

import com.example.weatherapp.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> getWeatherByIp(String ip);

    List<Weather> getWeatherByLatitudeAndLongitude(double latitude,
                                                   double longitude);
}
