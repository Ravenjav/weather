package com.senla.weather.repository;

import com.senla.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Weather findTopByOrderByIdDesc();

    List<Weather> findAllByDate(LocalDate date);
}
