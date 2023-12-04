package com.senla.weather.repository;

import com.senla.weather.entity.AverageTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AverageTemperatureRepository extends JpaRepository<AverageTemperature, LocalDate> {
    List<AverageTemperature> findByDateBetween(LocalDate from, LocalDate to);
}
