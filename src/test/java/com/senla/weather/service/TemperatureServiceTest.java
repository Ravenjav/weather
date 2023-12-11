package com.senla.weather.service;

import com.senla.weather.entity.AverageTemperature;
import com.senla.weather.entity.Interval;
import com.senla.weather.exception.ValidationException;
import com.senla.weather.repository.AverageTemperatureRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(AsinAppService.class)
@Log4j2
public class TemperatureServiceTest{
    @Mock
    private AverageTemperatureRepository averageTemperatureRepository;

    @BeforeClass
    public static void setup(){
        log.info("start TemperatureServiceTests");
    }

    @AfterClass
    public static void after(){
        log.info("accept TemperatureServiceTests");
    }

    @Test(expected = ValidationException.class)
    public void testFindAllInBetweenWithException() throws ValidationException {
        LocalDate from = LocalDate.parse("2015-08-09");
        LocalDate to = LocalDate.parse("2018-09-15");
        List<AverageTemperature> averageTemperatureList = new ArrayList<>();

        averageTemperatureList.add(new AverageTemperature(from, 7.5));

        when(averageTemperatureRepository.findByDateBetween(to, from))
                .thenReturn(averageTemperatureList);

        TemperatureService temperatureService = new TemperatureService(averageTemperatureRepository);

        Assert.assertArrayEquals(new List[]{averageTemperatureList},
                new List[]{temperatureService.findAllInBetween(to, from)});
    }

    @Test
    public void testFindAllInBetween() throws ValidationException {
        LocalDate from = LocalDate.parse("2015-08-09");
        LocalDate to = LocalDate.parse("2018-09-15");
        List<AverageTemperature> averageTemperatureList = new ArrayList<>();
        averageTemperatureList.add(new AverageTemperature(from, 7.5));

        when(averageTemperatureRepository.findByDateBetween(from, to)).thenReturn(averageTemperatureList);

        TemperatureService temperatureService = new TemperatureService(averageTemperatureRepository);

        Assert.assertArrayEquals(new List[]{averageTemperatureList},
                new List[]{temperatureService.findAllInBetween(from, to)});
    }

    @Test
    public void testGetAvgTemperatureWithStatusBadRequest() {
        LocalDate from = LocalDate.parse("2015-08-09");
        LocalDate to = LocalDate.parse("2018-09-15");
        Interval interval = new Interval(to, from);

        List<AverageTemperature> averageTemperatureList = new ArrayList<>();
        averageTemperatureList.add(new AverageTemperature(from, 7.5));

        when(averageTemperatureRepository.findByDateBetween(to, from)).thenReturn(averageTemperatureList);

        TemperatureService temperatureService = new TemperatureService(averageTemperatureRepository);

        Assert.assertEquals(new ResponseEntity<>(new ValidationException().getMessage(),
                HttpStatus.BAD_REQUEST), temperatureService.getAvgTemperature(interval));

    }

    @Test
    public void testGetAvgTemperatureWithStatusOk() {
        LocalDate from = LocalDate.parse("2015-08-09");
        LocalDate to = LocalDate.parse("2018-09-15");
        Interval interval = new Interval(from, to);

        List<AverageTemperature> averageTemperatureList = new ArrayList<>();
        averageTemperatureList.add(new AverageTemperature(from, 7.5));

        when(averageTemperatureRepository.findByDateBetween(from, to))
                .thenReturn(averageTemperatureList);

        TemperatureService temperatureService = new TemperatureService(averageTemperatureRepository);

        Assert.assertEquals(new ResponseEntity<>(averageTemperatureList, HttpStatus.OK),
                temperatureService.getAvgTemperature(interval));

    }

    @Test
    public void testGetAvgTemperatureWithStatusNotFound() {
        LocalDate from = LocalDate.parse("2015-08-09");
        LocalDate to = LocalDate.parse("2018-09-15");
        Interval interval = new Interval(from, to);
        List<AverageTemperature> averageTemperatureList = new ArrayList<>();

        when(averageTemperatureRepository.findByDateBetween(from, to)).thenReturn(averageTemperatureList);

        TemperatureService temperatureService = new TemperatureService(averageTemperatureRepository);

        Assert.assertEquals(new ResponseEntity<>("no info about these dates", HttpStatus.NOT_FOUND),
                temperatureService.getAvgTemperature(interval));

    }


}
