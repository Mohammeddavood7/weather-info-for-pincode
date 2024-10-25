package com.example.WeatherInfoForPincode.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.WeatherInfoForPincode.entity.WeatherData;
import com.example.WeatherInfoForPincode.model.GeocodingResponse;
import com.example.WeatherInfoForPincode.model.LatLong;
import com.example.WeatherInfoForPincode.model.WeatherApiResponse;
import com.example.WeatherInfoForPincode.repository.WeatherRepository;

@Service
public class WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Value("${openweathermap.api.key}")
    private String openWeatherApiKey;

    @Value("${google.geocoding.api.key}")
    private String googleGeocodingApiKey;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherData getWeatherByPincode(String pincode, LocalDate forDate) {
        Optional<WeatherData> savedWeatherData = weatherRepository.findByPincodeAndDate(pincode, forDate);

        if (savedWeatherData.isPresent()) {
            return savedWeatherData.get();
        }

        LatLong latLong = getLatLongFromPincode(pincode);
        WeatherData weatherData = fetchWeatherData(latLong);

        if (weatherData != null) {
            weatherData.setPincode(pincode);
            weatherData.setDate(forDate);
            weatherRepository.save(weatherData);
        }

        return weatherData;
    }

    private LatLong getLatLongFromPincode(String pincode) {
        String geocodingUrl = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", pincode, googleGeocodingApiKey);
        GeocodingResponse response = restTemplate.getForObject(geocodingUrl, GeocodingResponse.class);
        if (response != null && response.getResults().size() > 0) {
            double latitude = response.getResults().get(0).getGeometry().getLocation().getLat();
            double longitude = response.getResults().get(0).getGeometry().getLocation().getLng();
            return new LatLong(latitude, longitude);
        }
        throw new RuntimeException("Unable to fetch latitude and longitude for pincode: " + pincode);
    }

    private WeatherData fetchWeatherData(LatLong latLong) {
        String weatherApiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric",
                latLong.getLatitude(), latLong.getLongitude(), openWeatherApiKey);
        WeatherApiResponse weatherResponse = restTemplate.getForObject(weatherApiUrl, WeatherApiResponse.class);

        if (weatherResponse != null) {
            WeatherData weatherData = new WeatherData();
            weatherData.setLatitude(latLong.getLatitude());
            weatherData.setLongitude(latLong.getLongitude());
            weatherData.setTemperature(weatherResponse.getMain().getTemp());
            weatherData.setHumidity(weatherResponse.getMain().getHumidity());
            weatherData.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());
            return weatherData;
        }
        throw new RuntimeException("Unable to fetch weather data.");
    }
}
