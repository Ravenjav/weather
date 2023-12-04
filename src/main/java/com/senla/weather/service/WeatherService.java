package com.senla.weather.service;

import com.senla.weather.entity.Weather;
import com.senla.weather.repository.WeatherRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Data
@Slf4j
@EnableScheduling
@EnableCaching
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public void saveWeather(Weather weather) {
        weatherRepository.save(weather);
        log.info("saving weather by id:{}", weather.getId());
    }

    @Cacheable(value = "currentWeatherCache", key = "'staticKey'")
    public Weather getCurrentWeather() {
        log.info("getting current weather");
        return weatherRepository.findTopByOrderByIdDesc();
    }

    public List<Weather> getAllDailyWeathers(LocalDate today) {
        return weatherRepository.findAllByDate(today);
    }

    public ResponseEntity<?> takeWeather() {
        Weather weather = getCurrentWeather();
        if (weather == null) {
            log.info("no data in database");
            return new ResponseEntity<>("data base exception", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }
}
