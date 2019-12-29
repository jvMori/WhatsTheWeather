package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.databinding.MainWeatherLayoutBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.forecast.ForecastViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    ILoadImage iLoadImage;

    private LocationViewModel locationViewModel;
    private CurrentWeatherViewModel currentWeatherViewModel;
    private ForecastViewModel forecastViewModel;
    private MainWeatherLayoutBinding binding;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeatherViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(CurrentWeatherViewModel.class);
        forecastViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ForecastViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            locationViewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(LocationViewModel.class);
            observeLocationChanges();
        }
    }

    private void observeLocationChanges() {
        ((MainActivity) Objects.requireNonNull(getActivity())).lifecycleBoundLocationManager.deviceLocation().observe(this, location -> {
            if (location != null && location.status == Resource.Status.SUCCESS && location.data != null) {
                currentWeatherViewModel.fetchCurrentWeatherByGeographic(location.data);
                forecastViewModel.fetchForecastByGeo(location.data);
            }
        });
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
        observeCurrentWeatherAndUpdateView();
        observeForecastAndUpdateView();
    }

    private void observeCurrentWeatherAndUpdateView() {
        currentWeatherViewModel.getCurrentWeather().observe(this, weather -> {
            if (weather.status == Resource.Status.SUCCESS) {
                binding.setCurrentWeatherData(weather.data);
            }
        });
    }

    private void observeForecastAndUpdateView() {
        forecastViewModel.getForecast.observe(this, forecastsResource -> {
            if (forecastsResource.status == Resource.Status.SUCCESS){
                assert forecastsResource.data != null;
                createForecastView(forecastsResource.data);
            }}
        );
    }

    private void createForecastView(List<ForecastEntity> forecastEntityList) {
        ForecastAdapter adapter = new ForecastAdapter(forecastEntityList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        binding.successView.forecastRecyclerView.setAdapter(adapter);
        binding.successView.forecastRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void navigateToSearchListener() {
        NavDirections directions = WeatherFragmentDirections.actionWeatherFragmentToSearchFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }
}
