package com.example.WeatherInfoForPincode.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pincode;
    private double latitude;
    private double longitude;
    private double temperature;
    private int humidity;
    private String weatherDescription;

    @Column(name = "date")
    private LocalDate date;

   }
