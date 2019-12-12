package com.example.jvmori.myweatherapp.ui;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;


public class LifecycleBoundLocationManager implements LifecycleObserver
{
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    public LifecycleBoundLocationManager(
            FusedLocationProviderClient fusedLocationProviderClient,
            LifecycleOwner lifecycleOwner,
            LocationCallback locationCallback,
            LocationRequest locationRequest
    ) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        this.locationCallback = locationCallback;
        this.locationRequest = locationRequest;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void startLocationUpdates(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void removeLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
