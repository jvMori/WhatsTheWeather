package com.example.jvmori.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.CurrentLocation;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends DaggerAppCompatActivity {

    ImageView ivSearch;
    SwipeRefreshLayout swipeRefreshLayout;

    public static String deviceLocation;
    public static WeatherViewModel viewModel;
    private LocationViewModel locationViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(WeatherViewModel.class);
        locationViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(LocationViewModel.class);
        locationViewModel.CheckLocation();
        locationViewModel.getDeviceLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                String loc = locationViewModel.getCityName(location);
                WeatherParameters weatherParameters = new WeatherParameters(
                        loc,
                        true,
                        Const.FORECAST_DAYS
                );
                viewModel.fetchRemote(weatherParameters);
            }
        });
    }

    private void bindView() {
        ivSearch = findViewById(R.id.ivSearch);
    }


}

