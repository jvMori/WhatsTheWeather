package com.example.jvmori.myweatherapp;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.jvmori.myweatherapp.data.CurrentWeather;
import com.example.jvmori.myweatherapp.data.Forecast;
import com.example.jvmori.myweatherapp.data.Locations;

import java.util.ArrayList;

public class ForecastList extends Application
{
    public static CurrentWeather currentWeather;
    public static ArrayList<Forecast> forecasts;
    public static ArrayList<Locations> locList;

    @Override
    public void onCreate() {
        super.onCreate();
        currentWeather = new CurrentWeather("Kraków", "20", "Mostly sunny", "10", "20");

        this.forecasts = new ArrayList<>();
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));
        forecasts.add(new Forecast("12 Nov 2018", "Mon", "26", "19", "Breezy"));

        locList = new ArrayList<>();
        locList.add(new Locations(0, currentWeather, forecasts));
        locList.add(new Locations(1, currentWeather, forecasts));
        locList.add(new Locations(2, currentWeather, forecasts));

    }
}
