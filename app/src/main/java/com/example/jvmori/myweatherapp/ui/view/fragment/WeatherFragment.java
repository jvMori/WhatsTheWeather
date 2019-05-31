package com.example.jvmori.myweatherapp.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.application.WeatherApplication;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.di.component.WeatherApplicationComponent;
import com.example.jvmori.myweatherapp.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements MainActivity.ISetWeather {

    private View view;
    private TextView mainTemp, feelsLike, desc, humidity, pressure, city;
    private ImageView ivIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private ILoadImage iLoadImage;
    WeatherViewModel viewModel;

    @Override
    public void setWeatherParameters(WeatherParameters weatherParameters) {
        fetchWeather(weatherParameters);
    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) context;
        if (context.getApplicationContext() instanceof WeatherApplication)
            iLoadImage = ((WeatherApplication) context.getApplicationContext()).imageLoader();
        mainActivity.SetISetWeather(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        bindView(view);
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        return view;
    }

    private void fetchWeather(WeatherParameters weatherParameters) {
        viewModel.fetchWeather(weatherParameters);
        viewModel.getWeather().observe(this, forecastEntry -> displayWeather(forecastEntry));
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
    }

    private void createRecyclerView(ForecastEntry forecastEntry) {
        forecastAdapter = new ForecastAdapter(forecastEntry.getForecast().mFutureWeather, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(forecastAdapter);
    }
}
