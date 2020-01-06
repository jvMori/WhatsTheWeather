package com.example.jvmori.myweatherapp.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.WeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.databinding.MainWeatherLayoutBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.ui.view.adapters.RecyclerViewOnItemTouchListener;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BaseWeatherFragment extends DaggerFragment {

    protected MainWeatherLayoutBinding binding;
    CurrentWeatherViewModel currentWeatherViewModel;
    @Inject
    ViewModelProviderFactory factory;

    public BaseWeatherFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentWeatherViewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_weather_layout, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        observeWeatherAndUpdateView();
    }

    private void observeWeatherAndUpdateView() {
        currentWeatherViewModel.getCurrentWeather().observe(this, weather -> {
            if (weather.status == Resource.Status.LOADING) {
                loadingView();
            } else if (weather.status == Resource.Status.SUCCESS) {
                successView(weather);
            } else if (weather.status == Resource.Status.ERROR) {
                errorView();
            }
        });
    }

    private void errorView() {
        binding.loading.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.VISIBLE);
    }

    private void loadingView() {
        binding.loading.setVisibility(View.VISIBLE);
    }

    private void successView(Resource<WeatherUI> weather) {
        binding.loading.setVisibility(View.GONE);
        binding.errorLayout.setVisibility(View.GONE);
        binding.setCurrentWeatherData(weather.data);
        assert weather.data != null;
        createForecastView(weather.data.getForecastEntityList());
    }

    private void createForecastView(List<ForecastEntity> forecastEntityList) {
        ForecastAdapter adapter = new ForecastAdapter(forecastEntityList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        binding.successView.forecastRecyclerView.setAdapter(adapter);
        binding.successView.forecastRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemTouchListener());
        binding.successView.forecastRecyclerView.setLayoutManager(linearLayoutManager);
    }
}
