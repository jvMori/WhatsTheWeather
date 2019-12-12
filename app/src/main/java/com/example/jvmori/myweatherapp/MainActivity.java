package com.example.jvmori.myweatherapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;

import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.ui.LifecycleBoundLocationManager;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.view.fragment.LocationServiceDialog;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements LocationServiceDialog.IClickable {

    ImageView ivSearch;
    private LocationViewModel locationViewModel;
    private LocationServiceDialog locationServiceDialog;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    LocationRequest locationRequest;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //locationViewModel.requestLocationUpdates();
            bindLocationManager();
        } else {
            handleDisabledLocationProvider();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(LocationViewModel.class);
        setContentView(R.layout.activity_main);

        bindView();
        if (hasLocationPermission()) {
            bindLocationManager();
            locationViewModel.requestLocationUpdates();
        } else
            requestPermissions();
        //locationViewModel.requestLocationUpdates();
        //observeProviderStatus();
    }

    private void bindLocationManager() {
        new LifecycleBoundLocationManager(
                new FusedLocationProviderClient(this),
                this,
                new LocationCallback(),
                locationRequest);
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void observeProviderStatus() {
        locationViewModel.getProviderStatus().observe(this, providerStatus -> {
            switch (providerStatus) {
                case disabled:
                    handleDisabledLocationProvider();
                    break;
                case enabled:
                    locationViewModel.requestLocationUpdates();
                    break;
            }
        });
    }

    private void handleDisabledLocationProvider() {
        Log.i("Description", "disable");
        locationServiceDialog = new LocationServiceDialog();
        locationServiceDialog.setiClickable(this);
        locationServiceDialog.show(getSupportFragmentManager(), "location");
    }

    private void bindView() {
        ivSearch = findViewById(R.id.ivSearch);
    }

    @Override
    public void onPositiveBtn() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onCancel() {
        //fetch default location weather
    }

}

