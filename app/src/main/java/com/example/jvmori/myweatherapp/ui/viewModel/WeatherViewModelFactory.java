package com.example.jvmori.myweatherapp.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public WeatherViewModel create(@NonNull Class modelClass) {
        return new WeatherViewModel();
    }
}
