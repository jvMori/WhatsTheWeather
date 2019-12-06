package com.example.jvmori.myweatherapp.data.forecast;

import java.util.List;

public class Forecasts {
    private List<ForecastEntity> forecastList;

    public Forecasts(List<ForecastEntity> forecastList) {
        this.forecastList = forecastList;
    }

    public List<ForecastEntity> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<ForecastEntity> forecastList) {
        this.forecastList = forecastList;
    }
}
