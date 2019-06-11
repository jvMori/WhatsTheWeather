package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private TextView mainTemp, feelsLike, desc, humidity, pressure, city;
    private ImageView ivIcon, searchIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private WeatherViewModel viewModel;
    private Toolbar toolbar;

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
        navigateToSearch();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            viewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(WeatherViewModel.class);
        }
        viewModel.getWeather().observe(getViewLifecycleOwner(), forecastEntry ->
                displayWeather(forecastEntry)
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
        String feelslike = "Feels like: " + currentWeather.mFeelslikeC.toString() + "°";
        String humidityTxt = "Humidity: " + currentWeather.mHumidity.toString() + " %";
        String pressureTxt = currentWeather.mPressureMb.toString() + " hPa";
        String temp = currentWeather.mTempC.toString() + "°";

        city.setText(cityAndCountry);
        desc.setText(description);
        mainTemp.setText(temp);
        feelsLike.setText(feelslike);
        humidity.setText(humidityTxt);
        pressure.setText(pressureTxt);
        String url = "http:" + currentWeather.mCondition.getIcon();
        iLoadImage.loadImage(url, ivIcon);
        createRecyclerView(forecastEntry);
    }

    private void bindView(View view) {
        mainTemp = view.findViewById(R.id.tvMainCurrTemp);
        feelsLike = view.findViewById(R.id.feelsTemp);
        humidity = view.findViewById(R.id.Humidity);
        pressure = view.findViewById(R.id.Pressure);
        ivIcon = view.findViewById(R.id.ivMainIcon);
        desc = view.findViewById(R.id.tvDescriptionMain);
        city = view.findViewById(R.id.locationTextView);
        recyclerView = view.findViewById(R.id.RecyclerViewList);
        searchIcon = view.findViewById(R.id.navigateToSearch);
        toolbar = view.findViewById(R.id.toolbar);
    }

    private void createRecyclerView(ForecastEntry forecastEntry) {
        forecastAdapter = new ForecastAdapter(forecastEntry.getForecast().mFutureWeather, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(forecastAdapter);
    }

    private void navigateToSearch() {
        searchIcon.setOnClickListener(v -> {
            NavDirections directions = WeatherFragmentDirections.actionWeatherFragmentToSearchFragment();
            NavHostFragment.findNavController(this).navigate(directions);
        });
    }
}
