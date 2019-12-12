package com.example.jvmori.myweatherapp.data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jvmori.myweatherapp.ui.Resource;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

public class LocationProviderImpl implements LocationProvider {

    private MutableLiveData<Resource<Location>> _deviceLocation = new MutableLiveData<>();
    private MutableLiveData<ProviderStatus> _providerStatus = new MutableLiveData<>();

    private Activity activity;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Inject
    public LocationProviderImpl(Activity activity, FusedLocationProviderClient fusedLocationProviderClient) {
        this.activity = activity;
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    @Override
    public LiveData<Resource<Location>> deviceLocation() {
        return _deviceLocation;
    }

    @Override
    public LiveData<ProviderStatus> providerStatus() {
        return _providerStatus;
    }

    @Override
    public void requestLocationUpdates() {
        if (checkPermission()) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location ->
                            _deviceLocation.setValue(Resource.success(location)))
                    .addOnFailureListener(error -> {
                        _deviceLocation.setValue(Resource.error(error.getMessage(), null));
                        _providerStatus.setValue(ProviderStatus.disabled);
                    });
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
