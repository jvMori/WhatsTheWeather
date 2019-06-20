package com.example.jvmori.myweatherapp.data;

import android.location.Location;

import androidx.lifecycle.LiveData;

public interface LocationProvider {
    LiveData<Location> deviceLocation();

    void CheckLocation();
}
