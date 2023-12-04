package com.senla.weather.service;

import com.senla.weather.entity.AverageTemperature;
import com.senla.weather.entity.Weather;
import com.senla.weather.exception.OutsideApiException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@EnableScheduling
@Service
@Log4j2
@EnableCaching
public class AsinAppService {
    private final TemperatureService temperatureService;

    private final WeatherService weatherService;

    private final WeatherAPIService weatherAPIService;


    private static final int TIME = 60;

    @Scheduled(fixedRate = TIME, timeUnit = TimeUnit.SECONDS)
    @CacheEvict(value = "currentWeatherCache", key = "'staticKey'")
    void takeNewWeather() {
        Weather currentWeather = null;
        try {
            currentWeather = weatherAPIService.takeCurrentWeather();
        } catch (OutsideApiException e) {
            log.warn("outside api exception");
            return;
        }
        log.info("updating current weather");
        weatherService.saveWeather(currentWeather);
    }

    @Scheduled(cron = "45 59 23 * * * ")
    @CacheEvict(value = "avgTemperature")
    void startNewDay(){
        log.info("start new day");
        LocalDate currentDate = LocalDate.now();
        List<Weather> dailyWeather =  weatherService.getAllDailyWeathers(currentDate);
        if (dailyWeather.size() > 0){
            double avgTemperature = 0;
            for (int i = 0; i < dailyWeather.size(); i++){
                avgTemperature += dailyWeather.get(i).getTemperature();
            }
            avgTemperature /= dailyWeather.size();
            AverageTemperature averageTemperature = new AverageTemperature(currentDate, avgTemperature);
            log.info("saving new average temperature on{}", currentDate);
            temperatureService.saveTemperature(averageTemperature);
        }
        else{
            log.info("no data for adding new avg temperature on {}", currentDate);
        }
    }
}
