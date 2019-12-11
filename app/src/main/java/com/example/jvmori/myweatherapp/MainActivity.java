package com.example.jvmori.myweatherapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;

import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.view.fragment.LocationServiceDialog;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements LocationServiceDialog.IClickable {

    ImageView ivSearch;
    private LocationViewModel locationViewModel;
    private LocationServiceDialog locationServiceDialog;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationViewModel.requestLocationUpdates();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(LocationViewModel.class);
        setContentView(R.layout.activity_main);

        bindView();
        locationViewModel.requestLocationUpdates();
        observeProviderStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fetchDeviceLocation();
    }

    private void observeProviderStatus() {
        locationViewModel.getProviderStatus().observe(this, providerStatus -> {
            switch (providerStatus){
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

    private void fetchDeviceLocation() {
        locationViewModel.getDeviceLocation().observe(this, location -> {
            switch(location.status){
                case SUCCESS:
                    onSuccess(location);
                    break;
                case ERROR:
                    onError();
                    break;
            }
        });
    }

    private void onSuccess(Resource<Location> location) {
        if (location.data != null){
            String city = locationViewModel.getCity(location.data, this);
            String loc = location.data.getLatitude() + "," + location.data.getLongitude();
            WeatherParameters weatherParameters = new WeatherParameters(
                    loc,
                    city,
                    true,
                    Const.FORECAST_DAYS
            );
        }
    }
    private void onError(){
        fetchDefaultWeather();
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
        fetchDefaultWeather();
    }

    private void fetchDefaultWeather() {
        WeatherParameters weatherParameters = new WeatherParameters(
                "Gdynia",
                "Gdynia",
                false,
                Const.FORECAST_DAYS
        );
    }
}

