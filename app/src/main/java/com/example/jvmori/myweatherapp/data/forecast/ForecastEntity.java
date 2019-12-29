package com.example.jvmori.myweatherapp.data.forecast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastEntity {

    private String cityName;
    private Long timestamp;
    private String iconUrl;
    private String maxTemp;
    private String minTemp;

    public ForecastEntity(Long timestamp, String iconUrl, String maxTemp, String minTemp) {
        this.timestamp = timestamp;
        this.iconUrl = iconUrl;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getDayOfWeek(){
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date(timestamp));
    }

    public Long getTime() {
        return timestamp;
    }

    public void setTime(Long time) {
        this.timestamp = time;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }
}
