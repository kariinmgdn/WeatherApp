package com.example.weatherapp.external;

import com.example.weatherapp.dto.location.LocationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LocationRestService {

    public LocationResponse getLocation(String ipAddress) {
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
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
