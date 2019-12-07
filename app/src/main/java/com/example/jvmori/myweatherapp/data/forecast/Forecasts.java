package com.example.jvmori.myweatherapp.data.forecast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "forecast_table")
public class Forecasts {

    @PrimaryKey
    @ColumnInfo(name = "city_name")
    @NonNull
    private String cityName;

    private List<ForecastEntity> forecastList;

    public Forecasts(List<ForecastEntity> forecastList, String cityName) {
        this.forecastList = forecastList;
        this.cityName = cityName;
    }

    public List<ForecastEntity> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<ForecastEntity> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }
}
