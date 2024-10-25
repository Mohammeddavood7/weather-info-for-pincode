package com.example.WeatherInfoForPincode.model;

import java.util.List;

public class WeatherApiResponse {
    private Main main;
    private List<Weather> weather;

    public Main getMain() { return main; }
    public List<Weather> getWeather() { return weather; }

    public static class Main {
        private double temp;
        private int humidity;

        public double getTemp() { return temp; }
        public int getHumidity() { return humidity; }
    }

    public static class Weather {
        private String description;

        public String getDescription() { return description; }
    }
}
