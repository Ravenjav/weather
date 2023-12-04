package com.senla.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.weather.entity.Weather;
import com.senla.weather.exception.OutsideApiException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Data
class WeatherAPIService {
    private static final String API_URL = "https://weatherapi-com.p.rapidapi.com/current.json?q=Minsk";

    private static final String KEY = "27e87e6271msh2ae8968576ad9ffp11307djsne0eea677a8e3";
    private final ObjectMapper objectMapper;

    Weather takeCurrentWeather() throws OutsideApiException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("X-RapidAPI-Key", KEY)
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        Weather weather = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new OutsideApiException("runtime error");
        }
        try {
            weather = objectMapper.readValue(response.body(), Weather.class);
        } catch (JsonProcessingException e) {
            throw new OutsideApiException("json exception");
        }
        return weather;
    }
}
