package com.example.jvmori.myweatherapp.ui.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.LocationProvider;

import javax.inject.Inject;

public class LocationViewModel  extends ViewModel {

    private LocationProvider locationProvider;

    @Inject
    public LocationViewModel(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
    }
}

