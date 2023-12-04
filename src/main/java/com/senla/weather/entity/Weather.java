package com.senla.weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "weather", schema = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "temperature")
    private double temperature;

    @Column(name = "wind_speed")
    private double windSpeed;

    @Column(name = "pressure")
    private double pressure;

    @Column(name = "humidity")
    private int humidity;

    @Column(name = "condition")
    private String condition;

    @Column(name = "location")
    private String location;

    @JsonIgnore
    @Column(name = "date")
    public LocalDate date;

    @JsonProperty("current")
    private void unpackCurrent(Map<String, Object> current) {
        this.temperature = (double) current.get("temp_c");
        this.windSpeed = (double) current.get("wind_mph");
        this.pressure = (double) current.get("pressure_mb");
        this.humidity = (int) current.get("humidity");
        this.condition = (String) ((Map) current.get("condition")).get("text");


    }

    @JsonProperty("location")
    private void unpackLocation(Map<String, Object> location) {
        this.location = (String) location.get("name");
        String date = ((String) location.get("localtime")).substring(0, 10);
        this.date = LocalDate.parse(date);
        // this.date = (Date) calendar;
    }
}
