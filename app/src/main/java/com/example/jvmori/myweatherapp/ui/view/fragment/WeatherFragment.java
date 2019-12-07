package com.example.jvmori.myweatherapp.ui.view.fragment;


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

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.databinding.MainWeatherLayoutBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.forecast.ForecastViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.ui.view.customViews.ConditionInfo;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

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

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    ILoadImage iLoadImage;

    private CurrentWeatherViewModel currentWeatherViewModel;
    private ForecastViewModel forecastViewModel;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeatherViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(CurrentWeatherViewModel.class);
        forecastViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ForecastViewModel.class);
        currentWeatherViewModel.fetchCurrentWeather("Krakow");
        forecastViewModel.fetchForecast("Krakow");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainWeatherLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_weather_layout, container, false);
        binding.setLifecycleOwner(this);
        binding.setCurrentWeatherStatus(currentWeatherViewModel.getCurrentWeather());
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       currentWeatherViewModel.getCurrentWeather().observe(this, weather -> {
           if (weather.status == Resource.Status.LOADING){
               Log.i("WEATHER", "loading");
           } else if(weather.status == Resource.Status.SUCCESS){
               Log.i("WEATHER", weather.data.toString());
           } else if (weather.status == Resource.Status.ERROR){
               Log.i("WEATHER", weather.message);
           }
       });
       forecastViewModel.getForecast.observe(this, forecastsResource -> {
           if (forecastsResource.status == Resource.Status.LOADING){

           } else if(forecastsResource.status == Resource.Status.SUCCESS){
               Log.i("WEATHER", forecastsResource.data.toString());
           } else if (forecastsResource.status == Resource.Status.ERROR){
               Log.i("WEATHER", forecastsResource.message);
           }
       });
//        weatherView.setVisibility(View.GONE);
//        navigateToSearchListener();
//        getWeatherParams();
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            fetchWeather(this.getContext());
//        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //createWeatherViewModel();
        //fetchWeather(this.getContext());
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
