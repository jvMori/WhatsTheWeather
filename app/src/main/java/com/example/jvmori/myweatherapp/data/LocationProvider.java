package com.example.jvmori.myweatherapp.data;

import android.app.Activity;
import android.location.Location;

import androidx.lifecycle.LiveData;

public interface LocationProvider {
    LiveData<Location> deviceLocation();

    void CheckLocation();

    void startListening();

    void setActivity(Activity activity);
}
