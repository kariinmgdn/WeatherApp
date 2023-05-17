package com.example.weatherapp.domain;

import com.example.weatherapp.dto.ipAddress.IpAddress;
import com.example.weatherapp.dto.location.LocationResponse;
import com.example.weatherapp.dto.weather.WeatherResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;


public class Api {
    public static WeatherResponse getCurrentWeather(Double latitude, Double longitude) {
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static LocationResponse getLocation(String ipAddress) {
        try {
            return WebClient
                    .builder()
                    .baseUrl("https://tools.keycdn.com/geo.json")
                    .defaultHeader(HttpHeaders.USER_AGENT,
                            "keycdn-tools:https://localhost:8080")
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("host", ipAddress)
                            .build())
                    .retrieve()
                    .bodyToMono(LocationResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static IpAddress getIpAddress() {
        try {
            return WebClient
                    .create("https://api.ipify.org?format=json")
                    .get()
                    .retrieve()
                    .bodyToMono(IpAddress.class)
                    .log()
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
