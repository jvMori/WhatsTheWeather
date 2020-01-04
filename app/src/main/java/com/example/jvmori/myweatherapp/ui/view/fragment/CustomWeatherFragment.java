package com.example.jvmori.myweatherapp.ui.view.fragment;

public class CustomWeatherFragment extends BaseWeatherFragment {


    @Override
    public void onStart() {
        super.onStart();
        currentWeatherViewModel.observeCityAndFetchWeather();
    }
}
