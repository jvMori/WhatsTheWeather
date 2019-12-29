package com.example.jvmori.myweatherapp.data.forecast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "forecast_table", primaryKeys = {"city_name", "longitude", "latitude"})
public class Forecasts {

    @ColumnInfo(name = "city_name")
    @NonNull
    private String cityName;

    @NonNull
    private String longitude;
    @NonNull
    private String latitude;

    private Long timestamp;

    private List<ForecastEntity> forecastList;

    public Forecasts(List<ForecastEntity> forecastList, @NonNull String cityName, @NonNull String longitude, @NonNull String latitude, Long timestamp) {
        this.forecastList = forecastList;
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull String longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull String latitude) {
        this.latitude = latitude;
    }
}
