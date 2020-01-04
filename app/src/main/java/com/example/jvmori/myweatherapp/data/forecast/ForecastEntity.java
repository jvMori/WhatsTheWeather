package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.util.Const;

import java.util.List;

public class ForecastEntity {
    private String dayOfWeek;
    private String iconUrl;
    private String maxTemp;
    private String minTemp;
    private List<ForecastDetails> forecastHourlyList;

    public ForecastEntity(String dayOfWeek, String iconUrl, String maxTemp, String minTemp, List<ForecastDetails> forecastHourlyList) {
        this.dayOfWeek = dayOfWeek;
        this.iconUrl = iconUrl;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.forecastHourlyList = forecastHourlyList;
    }

    public String getFullIconUrl() {
        return Const.baseIconUrl + iconUrl + "@2x.png";
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ForecastDetails> getForecastHourlyList() {
        return forecastHourlyList;
    }

    public void setForecastHourlyList(List<ForecastDetails> forecastHourlyList) {
        this.forecastHourlyList = forecastHourlyList;
    }
}
