package com.example.WeatherInfoForPincode.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WeatherInfoForPincode.entity.WeatherData;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByPincodeAndDate(String pincode, LocalDate date);
    Optional<WeatherData> findByPincode(String pincode);
}
