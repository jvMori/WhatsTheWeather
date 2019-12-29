package com.example.jvmori.myweatherapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.ProviderStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class LifecycleBoundLocationManager implements
        LifecycleObserver,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationProvider {
    public static final int REQUEST_CHECK_SETTINGS = 10;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Activity activity;

    private MutableLiveData<Resource<Location>> _deviceLocation = new MutableLiveData<>();
    private MutableLiveData<ProviderStatus> _providerStatus = new MutableLiveData<>();
    GoogleApiClient mGoogleApiClient;

    @Override
    public LiveData<Resource<Location>> deviceLocation() {
        return _deviceLocation;
    }

    @Override
    public LiveData<ProviderStatus> providerStatus() {
        return _providerStatus;
    }

    public LifecycleBoundLocationManager(
            Activity activity,
            FusedLocationProviderClient fusedLocationProviderClient,
            LifecycleOwner lifecycleOwner,
            LocationRequest locationRequest
    ) {
        this.activity = activity;
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        this.locationRequest = locationRequest;
        lifecycleOwner.getLifecycle().addObserver(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()){
                    _deviceLocation.setValue(Resource.success(location));
                }
            }
        };

         mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void getLastKnownLocation() {
        if (checkPermission()) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location ->
                            _deviceLocation.setValue(Resource.success(location)))
                    .addOnFailureListener(error -> {
                        _deviceLocation.setValue(Resource.error(error.getMessage(), null));
                        if (error instanceof ResolvableApiException) {
                            try {
                                ResolvableApiException exception = (ResolvableApiException) error;
                                exception.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                                _providerStatus.setValue(ProviderStatus.disabled);
                            } catch (IntentSender.SendIntentException sendEx) {
                                //ignoring
                            }
                        }
                    });
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(){
        mGoogleApiClient.connect();
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void startLocationUpdates() {
        if (mGoogleApiClient.isConnected())
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(){
        mGoogleApiClient.disconnect();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
