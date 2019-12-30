package com.example.jvmori.myweatherapp.data.forecast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastEntity {

    private String cityName;
    private String dayOfWeek;
    private String iconUrl;
    private String maxTemp;
    private String minTemp;

    public ForecastEntity(String dayOfWeek, String iconUrl, String maxTemp, String minTemp) {
        this.dayOfWeek = dayOfWeek;
        this.iconUrl = iconUrl;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public String getFullIconUrl() {
        return Const.baseIconUrl + iconUrl + "@2x.png";
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
