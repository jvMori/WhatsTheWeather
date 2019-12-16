package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.example.jvmori.myweatherapp.data.current.response.Main;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.databinding.MainWeatherLayoutBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.forecast.ForecastViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.ui.view.customViews.ConditionInfo;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

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
         //currentWeatherViewModel.fetchCurrentWeather("Krakow");
        // forecastViewModel.fetchForecast("Krakow");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            locationViewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(LocationViewModel.class);
            ((MainActivity) getActivity()).lifecycleBoundLocationManager.deviceLocation().observe(this, location -> {
                if (location != null && location.status == Resource.Status.SUCCESS && location.data != null)
                    currentWeatherViewModel.fetchCurrentWeatherByGeographic(location.data);
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.main_weather_layout, container, false);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentWeatherViewModel.getCurrentWeather().observe(this, weather -> {
           // binding.setCurrentWeatherStatus(weather.status);
            if (weather.status == Resource.Status.SUCCESS) {
                binding.setCurrentWeatherData(weather.data);
            }
        });
        forecastViewModel.getForecast.observe(this, forecastsResource -> {
            if (forecastsResource.status == Resource.Status.LOADING) {

            }
        });
//        navigateToSearchListener();
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//
//        });
    }

    private void navigateToSearchListener() {
        NavDirections directions = WeatherFragmentDirections.actionWeatherFragmentToSearchFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }
}
