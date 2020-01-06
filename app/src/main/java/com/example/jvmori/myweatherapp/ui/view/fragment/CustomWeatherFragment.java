package com.example.jvmori.myweatherapp.ui.view.fragment;

import android.view.View;

public class CustomWeatherFragment extends BaseWeatherFragment {

    public String city;

    @Override
    public void onStart() {
        super.onStart();
        binding.successView.locationIcon.setVisibility(View.GONE);
        currentWeatherViewModel.fetchWeatherForCity(city);
    }
}
