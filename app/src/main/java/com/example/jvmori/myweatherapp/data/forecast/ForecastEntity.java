package com.example.jvmori.myweatherapp.data.forecast;

import java.util.List;

public class ForecastEntity {
    private String dayOfWeek;
    private List<ForecastHourly> forecastHourlyList;

    public ForecastEntity(String dayOfWeek, List<ForecastHourly> forecastHourlyList) {
        this.dayOfWeek = dayOfWeek;
        this.forecastHourlyList = forecastHourlyList;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ForecastHourly> getForecastHourlyList() {
        return forecastHourlyList;
    }

    public void setForecastHourlyList(List<ForecastHourly> forecastHourlyList) {
        this.forecastHourlyList = forecastHourlyList;
    }
}
