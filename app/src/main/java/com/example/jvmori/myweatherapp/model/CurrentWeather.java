package com.example.jvmori.myweatherapp.model;

import java.io.Serializable;

public class CurrentWeather implements Serializable
{
    private String city;
    private  String code;
    private String currentTemp;
    private String description;
    private String minTemp;
    private String maxTemp;

    public CurrentWeather(String city, String code, String currentTemp, String description, String minTemp, String maxTemp) {
        this.city = city;
        this.code = code;
        this.currentTemp = currentTemp;
        this.description = description;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
