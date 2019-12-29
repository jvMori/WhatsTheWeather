package com.example.jvmori.myweatherapp.data.forecast.response;

import androidx.room.ColumnInfo;

import com.example.jvmori.myweatherapp.data.current.response.Clouds;
import com.example.jvmori.myweatherapp.data.current.response.Description;
import com.example.jvmori.myweatherapp.data.current.response.Main;
import com.example.jvmori.myweatherapp.data.current.response.Wind;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    public Forecast(long timestamp, Main main, List<Description> descriptionList, Clouds clouds, Wind wind, Rain rain, Rain snow, String timeText) {
        this.timestamp = timestamp;
        this.main = main;
        this.descriptionList = descriptionList;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.timeText = timeText;
    }

    @SerializedName("dt")
    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private List<Description> descriptionList;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("rain")
    private Rain rain;

    @SerializedName("snow")
    private Rain snow;

    @SerializedName("dt_txt")
    private String timeText;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Rain getSnow() {
        return snow;
    }

    public void setSnow(Rain snow) {
        this.snow = snow;
    }
}
