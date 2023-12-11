package com.senla.weather.service;

import com.senla.weather.entity.Weather;
import com.senla.weather.repository.WeatherRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(AsinAppService.class)
@Log4j2
public class WeatherServiceTest {

    @Mock
    WeatherRepository weatherRepository;

    @BeforeClass
    public static void setup(){
        log.info("start WeatherServiceTests");
    }

    @AfterClass
    public static void after(){
        log.info("accept WeatherServiceTests");
    }


    @Test
    public void takeWeatherTestWithStatusNotFound(){
        when(weatherRepository.findTopByOrderByIdDesc()).thenReturn(null);
        WeatherService weatherService = new WeatherService(weatherRepository);
        Assert.assertEquals(new ResponseEntity<>("data base exception", HttpStatus.NOT_FOUND),
                weatherService.takeWeather());
    }

    @Test
    public void takeWeatherTestWithStatusOk(){
        Weather weather = new Weather();
        when(weatherRepository.findTopByOrderByIdDesc()).thenReturn(weather);
        WeatherService weatherService = new WeatherService(weatherRepository);
        Assert.assertEquals(new ResponseEntity<>(weather, HttpStatus.OK),
                weatherService.takeWeather());
    }
}
