package com.example.weatherapp.external;

import com.example.weatherapp.dto.weather.WeatherResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WeatherRestService {

    public WeatherResponse getCurrentWeather(Double latitude, Double longitude) {
        try {
            return WebClient
                    .builder()
                    .baseUrl("https://api.open-meteo.com/v1/forecast")
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current_weather", true)
                            .queryParam("forecast_days", 1)
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
