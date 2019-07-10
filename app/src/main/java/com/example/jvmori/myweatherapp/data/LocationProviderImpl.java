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

public class LocationProviderImpl implements LocationProvider {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private MutableLiveData<Resource<Location>> _deviceLocation = new MutableLiveData<>();
    private MutableLiveData<ProviderStatus> _providerStatus = new MutableLiveData<>();

    private Activity activity;

    @Override
    public LiveData<Resource<Location>> deviceLocation() {
        return _deviceLocation;
    }

    @Override
    public LiveData<ProviderStatus> providerStatus() {
        return _providerStatus;
    }

    @Override
    public void startListening() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3600, 5000, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            _deviceLocation.setValue(Resource.success(location));
        }
    }

    @Override
    public void CheckLocation() {
        _deviceLocation.setValue(Resource.loading(null));
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
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
                _deviceLocation.setValue(Resource.error(s +" not enabled", null));
                Log.i("Weather", "disable");
            }
        };

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        startListening();
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
