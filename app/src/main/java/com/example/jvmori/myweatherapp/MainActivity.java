package com.example.jvmori.myweatherapp;


import android.os.Bundle;
import android.widget.ImageView;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {

    ImageView ivSearch;
    SwipeRefreshLayout swipeRefreshLayout;
    public static WeatherViewModel viewModel;
    private LocationViewModel locationViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(WeatherViewModel.class);
        getWeatherForLocation();
    }

    private void getWeatherForLocation() {
        locationViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(LocationViewModel.class);
        locationViewModel.setLocationProviderActivity(this);
        locationViewModel.CheckLocation();
        locationViewModel.getDeviceLocation().observe(this, location -> {
            String city = locationViewModel.getCity(location, this);
            String loc = location.getLatitude() + "," + location.getLongitude();
            WeatherParameters weatherParameters = new WeatherParameters(
                    loc,
                    city,
                    true,
                    Const.FORECAST_DAYS
            );
            viewModel.fetchWeather(weatherParameters);
        });
    }

    private void bindView() {
        ivSearch = findViewById(R.id.ivSearch);
    }

}

