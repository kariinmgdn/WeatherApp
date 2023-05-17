package com.example.weatherapp;

import com.example.weatherapp.domain.Api;
import com.example.weatherapp.domain.HistoricalAnalysis;
import com.example.weatherapp.domain.HttpUtils;
import com.example.weatherapp.domain.Weather;
import com.example.weatherapp.dto.location.Geo;
import com.example.weatherapp.dto.weather.CurrentWeather;
import com.example.weatherapp.dto.weather.WeatherResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.ToDoubleFunction;

@Service
public class WeatherService {

    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    @Cacheable("weather")
    public Weather getWeather(HttpServletRequest request) {
        String ipAddress = HttpUtils.getRequestIP(request);
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = Api.getIpAddress().getIp();
        }

        Geo location = Api.getLocation(ipAddress).getData().getGeo();

        double a = formatDouble(location.getLatitude(), 2);
        double b = formatDouble(location.getLongitude(), 2);

        WeatherResponse weatherResponse = Api.getCurrentWeather(a, b);
        CurrentWeather currentWeather = weatherResponse.getCurrent_weather();

        return repository.save(new Weather(
                ipAddress,
                weatherResponse.getLatitude(),
                weatherResponse.getLongitude(),
                currentWeather.getTemperature(),
                currentWeather.getWindspeed(),
                currentWeather.getWinddirection(),
                currentWeather.getWeathercode()));
    }

    public HistoricalAnalysis getWeatherHistoricalAnalysisByIp(String ip) {
        List<Weather> weatherList = repository.getWeatherByIp(ip);
        return getHistoricalAnalysis(weatherList);
    }

    public HistoricalAnalysis getWeatherHistoricalAnalysisByLatAndLong(double latitude,
                                                                       double longitude) {
        List<Weather> weatherList = repository
                .getWeatherByLatitudeAndLongitude(latitude, longitude);
        return getHistoricalAnalysis(weatherList);
    }

    private HistoricalAnalysis getHistoricalAnalysis(List<Weather> weatherList) {

        DoubleSummaryStatistics temperatureStatistics = weatherList
                .stream()
                .mapToDouble(Weather::getTemperature)
                .summaryStatistics();

        double averageWindSpeed = getAverage(weatherList, Weather::getWindSpeed);
        double averageWindDirection = getAverage(weatherList, Weather::getWindDirection);
        double averageWeatherCode = getAverage(weatherList, Weather::getWeatherCode);

        return new HistoricalAnalysis(
                formatDouble(temperatureStatistics.getAverage(), 1),
                formatDouble(temperatureStatistics.getMin(), 1),
                formatDouble(temperatureStatistics.getMax(), 1),
                formatDouble(averageWindSpeed, 1),
                formatDouble(averageWindDirection, 1),
                formatDouble(averageWeatherCode, 1));
    }

    private double getAverage(List<Weather> list,
                              ToDoubleFunction<? super Weather> function) {
        return list.stream().mapToDouble(function).average().orElse(0);
    }

    private double formatDouble(Double d, int scale) {
        return new BigDecimal(d.toString())
                .setScale(scale, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
