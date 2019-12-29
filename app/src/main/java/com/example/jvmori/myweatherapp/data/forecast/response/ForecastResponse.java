package com.example.jvmori.myweatherapp.data.forecast.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("cod")
    private int cod;
    @SerializedName("message")
    private Double message;
    @SerializedName("cnt")
    private Double cnt;
    @SerializedName("list")
    private List<Forecast> forecast;
    @SerializedName("city")
    private City city;

    public ForecastResponse(int cod, Double message, Double cnt, List<Forecast> forecast, City city) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.forecast = forecast;
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Double getCnt() {
        return cnt;
    }

    public void setCnt(Double cnt) {
        this.cnt = cnt;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
