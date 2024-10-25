package com.example.WeatherInfoForPincode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.WeatherInfoForPincode.entity.WeatherData;
import com.example.WeatherInfoForPincode.service.WeatherService;

@SpringBootTest
public class WeatherAppTests {
    
    @Autowired
    private WeatherService weatherService;
    
    @Test
    public void testGetWeatherByPincode() {
        WeatherData weatherData = weatherService.getWeatherByPincode("411014", LocalDate.of(2020, 10, 15));
        assertNotNull(weatherData);
        assertEquals("411014", weatherData.getPincode());
    }
}
