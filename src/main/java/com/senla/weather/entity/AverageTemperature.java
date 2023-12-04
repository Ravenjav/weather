package com.senla.weather.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "average_temperature")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AverageTemperature {
    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "temperature")
    private double avgTemperature;
}
