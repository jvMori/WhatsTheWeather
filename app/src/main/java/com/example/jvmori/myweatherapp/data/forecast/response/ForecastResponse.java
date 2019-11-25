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
}
