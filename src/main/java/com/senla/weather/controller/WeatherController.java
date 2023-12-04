package com.senla.weather.controller;

import com.senla.weather.entity.Interval;
import com.senla.weather.service.TemperatureService;
import com.senla.weather.service.WeatherService;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class WeatherController {

    private final WeatherService weatherService;

    private final TemperatureService temperatureService;

    @GetMapping(value = "/takeWeather")
    public ResponseEntity<?> getWeather() {
        return weatherService.takeWeather();
    }

    @PostMapping(value = "/takeAvgTemperature",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> take(@RequestBody Interval interval) {
        return temperatureService.getAvgTemperature(interval);
    }
}
