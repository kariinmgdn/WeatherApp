package com.example.weatherapp;

import com.example.weatherapp.domain.Weather;
import com.example.weatherapp.dto.location.Data;
import com.example.weatherapp.dto.location.Geo;
import com.example.weatherapp.dto.location.LocationResponse;
import com.example.weatherapp.dto.weather.CurrentWeather;
import com.example.weatherapp.dto.weather.HistoricalAnalysis;
import com.example.weatherapp.dto.weather.WeatherResponse;
import com.example.weatherapp.external.LocationRestService;
import com.example.weatherapp.external.WeatherRestService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @InjectMocks
    WeatherService weatherService;
    @Mock
    WeatherRepository weatherRepository;
    @Mock
    WeatherRestService weatherRestService;
    @Mock
    LocationRestService locationRestService;
    Geo geo = new Geo("19.199.123.156", 37.75, -97.82);
    Data data = new Data(geo);
    LocationResponse locationResponse = new LocationResponse(
            "success", "Data successfully received.", data);
    CurrentWeather currentWeather = new CurrentWeather(16.1, 9.5, 155.0, 0);
    WeatherResponse weatherResponse = new WeatherResponse(37.71453, -97.77939, currentWeather);
    HistoricalAnalysis expected = new HistoricalAnalysis(16.1, 16.1,
            16.1, 9.5, 155.0, 0);
    Weather weather = new Weather(
            1,
            "19.199.123.156",
            LocalDateTime.now(),
            weatherResponse.getLatitude(),
            weatherResponse.getLongitude(),
            currentWeather.getTemperature(),
            currentWeather.getWindspeed(),
            currentWeather.getWinddirection(),
            currentWeather.getWeathercode());
    Weather weather2 = new Weather(
            2,
            "19.199.123.156",
            LocalDateTime.now(),
            weatherResponse.getLatitude(),
            weatherResponse.getLongitude(),
            currentWeather.getTemperature(),
            currentWeather.getWindspeed(),
            currentWeather.getWinddirection(),
            currentWeather.getWeathercode());

    @Test
    void testGetWeather() {
        String ipAddress = "19.199.123.156";
        HttpServletRequest request = mock(HttpServletRequest.class);
        Mockito.doAnswer(a -> ipAddress).when(request).getHeader("X-Forwarded-For");

        Mockito.doAnswer(answer -> locationResponse).when(locationRestService).getLocation(ipAddress);
        Mockito.doAnswer(answer -> weatherResponse).when(weatherRestService)
                .getCurrentWeather(geo.getLatitude(), geo.getLongitude());
        Mockito.doAnswer(answer -> weather).when(weatherRepository)
                .save(new Weather(
                        ipAddress,
                        weatherResponse.getLatitude(),
                        weatherResponse.getLongitude(),
                        currentWeather.getTemperature(),
                        currentWeather.getWindspeed(),
                        currentWeather.getWinddirection(),
                        currentWeather.getWeathercode())
                );
        Weather result = weatherService.getWeather(request);
        Assertions.assertEquals(weather, result);
    }

    @Test
    void testGetWeatherHistoricalAnalysisByIp() {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherList.add(weather2);
        Mockito.doAnswer(a -> weatherList).when(weatherRepository).getWeatherByIp("19.199.123.156");
        HistoricalAnalysis result = weatherService.getWeatherHistoricalAnalysisByIp("19.199.123.156");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetWeatherHistoricalAnalysisByLatAndLong() {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherList.add(weather2);
        Mockito.doAnswer(a -> weatherList).when(weatherRepository)
                .getWeatherByLatitudeAndLongitude(37.71453, -97.77939);
        HistoricalAnalysis result = weatherService
                .getWeatherHistoricalAnalysisByLatAndLong(37.71453, -97.77939);
        Assertions.assertEquals(expected, result);
    }
}
