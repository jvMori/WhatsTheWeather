package com.example.jvmori.myweatherapp.ui.view.fragment;

public class CustomWeatherFragment extends BaseWeatherFragment {

    public String city;

    @Override
    public void onStart() {
        super.onStart();
        currentWeatherViewModel.fetchCurrentWeather(city);
    }
}
