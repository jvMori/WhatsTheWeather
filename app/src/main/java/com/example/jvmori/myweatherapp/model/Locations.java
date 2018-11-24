package com.example.jvmori.myweatherapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Locations implements Serializable
{
    private int id;
    private CurrentWeather currentWeather;
    private ArrayList<Forecast> forecasts;

    public Locations(int id, CurrentWeather currentWeather, ArrayList<Forecast> forecasts) {
        this.id = id;
        this.currentWeather = currentWeather;
        this.forecasts = forecasts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
