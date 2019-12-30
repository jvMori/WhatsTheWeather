package com.example.jvmori.myweatherapp.data;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;

import java.util.List;

public class WeatherUI {
    private CurrentWeatherUI currentWeatherUI;
    private List<ForecastEntity> forecastEntityList;

    public WeatherUI(CurrentWeatherUI currentWeatherUI, List<ForecastEntity> forecastEntityList) {
        this.currentWeatherUI = currentWeatherUI;
        this.forecastEntityList = forecastEntityList;
    }

    public CurrentWeatherUI getCurrentWeatherUI() {
        return currentWeatherUI;
    }

    public void setCurrentWeatherUI(CurrentWeatherUI currentWeatherUI) {
        this.currentWeatherUI = currentWeatherUI;
    }

    public List<ForecastEntity> getForecastEntityList() {
        return forecastEntityList;
    }

    public void setForecastEntityList(List<ForecastEntity> forecastEntityList) {
        this.forecastEntityList = forecastEntityList;
    }
}
