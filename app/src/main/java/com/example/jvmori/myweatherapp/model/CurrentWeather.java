package com.example.jvmori.myweatherapp.model;

public class CurrentWeather
{
    private String city;
    private String currentTemp;
    private String description;
    private String minTemp;
    private String maxTemp;

    public CurrentWeather(String city, String currentTemp, String description, String minTemp, String maxTemp) {
        this.city = city;
        this.currentTemp = currentTemp;
        this.description = description;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }
}
