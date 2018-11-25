package com.example.jvmori.myweatherapp.utils;

import com.example.jvmori.myweatherapp.model.Locations;

import java.util.ArrayList;

public interface WeatherAsyncResponse
{
    void processFinished(Locations locationData);
}
