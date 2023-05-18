package com.example.weatherapp;

import com.example.weatherapp.dto.weather.HistoricalAnalysis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @Mock
    WeatherService weatherService;
    @InjectMocks
    WeatherController weatherController;


    HistoricalAnalysis historicalAnalysis = new HistoricalAnalysis(16.1, 16.1,
            16.1, 9.5, 155.0, 0);

    @Test
    void testGetHistoricalAnalysisByIp() {
        String ipAddress = "19.199.123.156";
        Mockito.doAnswer(a -> historicalAnalysis).when(weatherService)
                .getWeatherHistoricalAnalysisByIp(ipAddress);
        Assertions.assertEquals(historicalAnalysis, weatherController
                .getHistoricalAnalysis(ipAddress, null, null));
    }

    @Test
    void testGetHistoricalAnalysisByLatAndLong() {
        double latitude = 37.71453;
        double longitude = -97.77939;
        Mockito.doAnswer(a -> historicalAnalysis).when(weatherService)
                .getWeatherHistoricalAnalysisByLatAndLong(latitude, longitude);
        Assertions.assertEquals(historicalAnalysis, weatherController
                .getHistoricalAnalysis(null, latitude, longitude));
    }

    @Test
    void testGetHistoricalAnalysisWithNoParams() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> weatherController.getHistoricalAnalysis(null, null, null));
        Assertions.assertEquals(HttpStatusCode.valueOf(400), exception.getStatusCode());
        ;
    }
}
