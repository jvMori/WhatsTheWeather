package com.example.jvmori.myweatherapp.data;

import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.jvmori.myweatherapp.ui.Resource;

public interface LocationProvider {
    LiveData<Resource<Location>> deviceLocation();
    LiveData<ProviderStatus> providerStatus();

    void getLastKnownLocation();
}
