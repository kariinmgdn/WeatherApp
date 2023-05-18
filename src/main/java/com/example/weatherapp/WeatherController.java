package com.example.weatherapp;

import com.example.weatherapp.dto.weather.HistoricalAnalysis;
import com.example.weatherapp.domain.Weather;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public Weather getWeather(HttpServletRequest request) {
        return service.getWeather(request);
    }

    @GetMapping("/historicalAnalysis")
    public HistoricalAnalysis getHistoricalAnalysis(@RequestParam(value = "ip", required = false) String ip,
                                                    @RequestParam(value = "latitude", required = false) Double latitude,
                                                    @RequestParam(value = "longitude", required = false) Double longitude) {
        if (ip != null && !ip.isBlank()) {
            return service.getWeatherHistoricalAnalysisByIp(ip);
        } else if (latitude != null && !latitude.isNaN() && longitude != null && !longitude.isNaN()) {
            return service.getWeatherHistoricalAnalysisByLatAndLong(latitude, longitude);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
