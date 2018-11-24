package com.example.jvmori.myweatherapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Locations implements Serializable
{
    private String id;
    private CurrentWeather currentWeather;
    private ArrayList<Forecast> forecasts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(ArrayList<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
