package com.example.weatherapp.external;

import com.example.weatherapp.dto.ipAddress.IpAddress;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IpAddressRestService {

    public IpAddress getIpAddress() {
        try {
            return WebClient
                    .create("https://api.ipify.org?format=json")
                    .get()
                    .retrieve()
                    .bodyToMono(IpAddress.class)
                    .log()
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
