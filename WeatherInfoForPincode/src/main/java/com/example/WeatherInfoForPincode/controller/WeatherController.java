package com.example.WeatherInfoForPincode.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.WeatherInfoForPincode.entity.WeatherData;
import com.example.WeatherInfoForPincode.model.ApiResponse;
import com.example.WeatherInfoForPincode.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/pincode/{pincode}/date/{date}")
    public ResponseEntity<ApiResponse<WeatherData>> getWeatherByPincode(
            @PathVariable String pincode,
            @PathVariable String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate forDate = LocalDate.parse(date, formatter);
            logger.info("Fetching weather data for pincode: {} and date: {}", pincode, forDate);

            WeatherData weatherData = weatherService.getWeatherByPincode(pincode, forDate);
            return ResponseEntity.ok(new ApiResponse<>(weatherData, "Weather data fetched successfully.", true));
        } catch (DateTimeParseException e) {
            logger.error("Date parsing error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "Invalid date format. Please use dd-MM-yyyy.", false));
        } catch (RuntimeException e) {
            logger.error("Runtime error while fetching weather data: {}", e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(null, "Error fetching weather data: " + e.getMessage(), false));
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(null, "An unexpected error occurred: " + e.getMessage(), false));
        }
    }
}
