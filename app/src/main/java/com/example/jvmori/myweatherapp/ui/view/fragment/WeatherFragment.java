package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.ui.view.customViews.ConditionInfo;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends DaggerFragment {

    private View view;
    private TextView mainTemp, feelsLike, desc, city;
    private ConditionInfo humidity, pressure, wind, visibility;
    private ImageView ivIcon, searchIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private WeatherViewModel viewModel;
    private Toolbar toolbar;
    private Group weatherView;
    private ConstraintLayout loading;
    private WeatherParameters weatherParameters = null;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    ILoadImage iLoadImage;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loading.setVisibility(View.VISIBLE);
        weatherView.setVisibility(View.GONE);
        navigateToSearchListener();
        getWeatherParams();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createWeatherViewModel();
        fetchWeatherWhenSearched();
    }

    private void getWeatherParams() {
        if (getArguments() != null)
            weatherParameters = WeatherFragmentArgs.fromBundle(getArguments()).getWeatherParam();
    }

    private void createWeatherViewModel() {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            viewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(WeatherViewModel.class);
        }
    }

    private void fetchWeatherWhenSearched() {
        if (weatherParameters != null)
            viewModel.fetchWeather(weatherParameters);
        viewModel.getWeather().observe(getViewLifecycleOwner(), forecastEntry ->
                {
                    switch (forecastEntry.status){
                        case LOADING:
                            loading.setVisibility(View.VISIBLE);
                            weatherView.setVisibility(View.GONE);
                            break;
                        case SUCCESS:
                            displayWeather(forecastEntry.data);
                            loading.setVisibility(View.GONE);
                            weatherView.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            loading.setVisibility(View.GONE);
                            break;
                    }
                }
        );
    }

    private void displayWeather(ForecastEntry forecastEntry) {
        if (forecastEntry != null) {
            createCurrentWeatherUi(forecastEntry);
        }
    }

    private void createCurrentWeatherUi(ForecastEntry forecastEntry) {
        CurrentWeather currentWeather = forecastEntry.getCurrentWeather();
        String cityAndCountry = String.format("%s, %s",
                forecastEntry.getLocation().getName(),
                forecastEntry.getLocation().getCountry());
        String description = currentWeather.mCondition.getText();
        String feelslike = "Feels like: " + currentWeather.mFeelslikeC.intValue() + "°C";
        String humidityTxt = currentWeather.mHumidity.toString() + " %";
        String temp = currentWeather.mTempC.intValue() + "°C";
        String prec = currentWeather.mPrecipMm + " mm/km";
        String windTxt = currentWeather.mWindKph + " kph";
        String pressureTxt = currentWeather.mPressureMb.intValue() + " hPa";


        city.setText(cityAndCountry);
        desc.setText(description);
        mainTemp.setText(temp);
        feelsLike.setText(feelslike);
        humidity.setValue(humidityTxt);
        visibility.setValue(prec);
        wind.setValue(windTxt);
        pressure.setValue(pressureTxt);
        String url = "http:" + currentWeather.mCondition.getIcon();
        iLoadImage.loadImage(url, ivIcon);
        createRecyclerView(forecastEntry);
    }

    private void bindView(View view) {
        mainTemp = view.findViewById(R.id.tvMainCurrTemp);
        feelsLike = view.findViewById(R.id.feelsTemp);
        pressure = view.findViewById(R.id.Pressure);
        ivIcon = view.findViewById(R.id.ivMainIcon);
        desc = view.findViewById(R.id.tvDescriptionMain);
        city = view.findViewById(R.id.locationTextView);
        recyclerView = view.findViewById(R.id.RecyclerViewList);
        searchIcon = view.findViewById(R.id.navigateToSearch);
        toolbar = view.findViewById(R.id.toolbar);
        weatherView = view.findViewById(R.id.view);
        loading = view.findViewById(R.id.loading);
        humidity = view.findViewById(R.id.Humidity);
        pressure = view.findViewById(R.id.Pressure);
        wind = view.findViewById(R.id.Wind);
        visibility = view.findViewById(R.id.Visibility);
    }

    private void createRecyclerView(ForecastEntry forecastEntry) {
        forecastAdapter = new ForecastAdapter(forecastEntry.getForecast().mFutureWeather, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(forecastAdapter);
    }

    private void navigateToSearchListener() {
        searchIcon.setOnClickListener(v -> {
            NavDirections directions = WeatherFragmentDirections.actionWeatherFragmentToSearchFragment();
            NavHostFragment.findNavController(this).navigate(directions);
        });
    }
}
