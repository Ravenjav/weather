package com.senla.weather.service;

import com.senla.weather.entity.AverageTemperature;
import com.senla.weather.entity.Interval;
import com.senla.weather.exception.TemperatureException;
import com.senla.weather.repository.AverageTemperatureRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
@Log4j2
@EnableCaching
public class TemperatureService {

    private final AverageTemperatureRepository averageTemperatureRepository;

    public List<AverageTemperature> findAllInBetween(LocalDate from, LocalDate to) throws TemperatureException {
        if (from.toString().compareTo(to.toString()) > 0) {
            log.info("incorrect input date");
            throw new TemperatureException("end date is less than start date");
        }
        List<AverageTemperature> averageTemperatures = averageTemperatureRepository.
                findByDateBetween(from, to);
        return averageTemperatures;
    }

    public void saveTemperature(AverageTemperature averageTemperature) {
        log.info("saving new avg temperature");
        averageTemperatureRepository.save(averageTemperature);
    }

    @Cacheable(value = "avgTemperature")
    public ResponseEntity<?> getAvgTemperature(Interval interval) {
        List<AverageTemperature> temperatures = null;
        try {
            log.info("searching for dates in the interval");
            temperatures = findAllInBetween(interval.getFrom(), interval.getTo());
        } catch (TemperatureException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (temperatures != null && temperatures.size() > 0) {
            log.info("successful dates return");
            return new ResponseEntity<>(temperatures, HttpStatus.OK);
        }
        log.error("no dates in this interval");
        return new ResponseEntity<>("no info about these dates", HttpStatus.NOT_FOUND);
    }
}
