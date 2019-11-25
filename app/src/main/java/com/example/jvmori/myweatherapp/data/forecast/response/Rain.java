package com.example.jvmori.myweatherapp.data.forecast.response;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @ColumnInfo(name = "prediction")
    private Double prediction;

    public Rain(Double prediction) {
        this.prediction = prediction;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }
}
