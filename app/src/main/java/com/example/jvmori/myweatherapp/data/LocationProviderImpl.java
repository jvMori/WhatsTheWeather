package com.example.jvmori.myweatherapp.data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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

import javax.inject.Inject;

public class LocationProviderImpl implements LocationProvider, LocationListener {

    private LocationManager locationManager;
    private MutableLiveData<Resource<Location>> _deviceLocation = new MutableLiveData<>();
    private MutableLiveData<ProviderStatus> _providerStatus = new MutableLiveData<>();

    private Activity activity;
    private int minUpdatesTime;
    private int minDistance;

    @Inject
    public LocationProviderImpl(
            Activity activity,
            LocationManager locationManager,
            int minUpdatesTime,
            int minDistance) {
        this.activity = activity;
        this.locationManager = locationManager;
        this.minUpdatesTime = minUpdatesTime;
        this.minDistance = minDistance;

        requestPermissions();
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
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minUpdatesTime, minDistance, this);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null)
                    _deviceLocation.setValue(Resource.success(location));
                else
                    _deviceLocation.setValue(Resource.error("Location not detected", null));
            } else {
                _providerStatus.setValue(ProviderStatus.disabled);
            }
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        _deviceLocation.setValue(Resource.success(location));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        _providerStatus.setValue(ProviderStatus.enabled);
    }

    @Override
    public void onProviderDisabled(String s) {
        _providerStatus.setValue(ProviderStatus.disabled);
        _deviceLocation.setValue(Resource.error(s + " not enabled", null));
        Log.i("Description", "disable");
    }

}
