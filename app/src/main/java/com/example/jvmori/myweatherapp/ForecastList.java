package com.example.jvmori.myweatherapp;

import android.app.Application;

import com.example.jvmori.myweatherapp.data.Forecast;

import java.util.ArrayList;

public class ForecastList extends Application
{
    public static ArrayList<Forecast> forecasts;

    @Override
    public void onCreate() {
        super.onCreate();
        this.forecasts = new ArrayList<>();
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
    }
}
