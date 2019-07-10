package com.example.jvmori.myweatherapp.data;

import android.app.Activity;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.jvmori.myweatherapp.ui.Resource;

public interface LocationProvider {
    LiveData<Resource<Location>> deviceLocation();

    void CheckLocation();

    void startListening();

    void setActivity(Activity activity);
}
