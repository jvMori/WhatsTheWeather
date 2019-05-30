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
    //private LinearLayout errorLayout, progressBarLayout;
    private ImageView ivIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private WeatherParameters weatherParameters;
    public ILoadImage iLoadImage;
    private ForecastEntry forecastEntry;

    @Override
    public void setWeatherParameters(WeatherParameters weatherParameters){
        this.weatherParameters = weatherParameters;
        fetchWeather(weatherParameters);
    }

    public void setForecastEntry(ForecastEntry forecastEntry) {
        this.forecastEntry = forecastEntry;
    }

    public void setImageLoader(ILoadImage imageLoader) {
        this.iLoadImage = imageLoader;
    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity)context;
        if(context.getApplicationContext() instanceof WeatherApplication)
            iLoadImage = ((WeatherApplication) context.getApplicationContext()).imageLoader();
        mainActivity.SetISetWeather(this);
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
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //progressBarLayout.setVisibility(View.VISIBLE);
        fetchWeather(new WeatherParameters("Kleparz", false, "7"));
//        if(forecastEntry != null){
//            displayWeather(forecastEntry);
//            refreshWeather();
//        }
    }
    public void fetchWeather(WeatherParameters weatherParameters){
        WeatherViewModel viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        viewModel.fetchWeather(weatherParameters);
        viewModel.getWeather().observe(this, new Observer<ForecastEntry>() {
            @Override
            public void onChanged(ForecastEntry forecastEntry) {
                displayWeather(forecastEntry);
            }
        });
    }

    private void refreshWeather(){
        WeatherViewModel viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        viewModel.refreshWeather(forecastEntry);
        viewModel.getFreshWeather().observe(this,
                this::displayWeather
        );
    }

    private void displayWeather(ForecastEntry forecastEntry) {
        if (forecastEntry != null) {
            createCurrentWeatherUi(forecastEntry);
           // errorLayout.setVisibility(View.GONE);
            //progressBarLayout.setVisibility(View.GONE);
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
        //progressBarLayout.setVisibility(View.GONE);
    }

    private void bindView(View view) {
        mainTemp = view.findViewById(R.id.tvMainCurrTemp);
        feelsLike = view.findViewById(R.id.feelsTemp);
        humidity = view.findViewById(R.id.Humidity);
        pressure = view.findViewById(R.id.Pressure);
        ivIcon = view.findViewById(R.id.ivMainIcon);
        desc = view.findViewById(R.id.tvDescriptionMain);
        //errorLayout = view.findViewById(R.id.errorLayout);
        city = view.findViewById(R.id.locationTextView);
        recyclerView = view.findViewById(R.id.RecyclerViewList);
        //progressBarLayout = view.findViewById(R.id.progressBarLayout);
    }


    private void createRecyclerView(ForecastEntry forecastEntry) {
        forecastAdapter = new ForecastAdapter(forecastEntry.getForecast().mFutureWeather, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(forecastAdapter);
    }

}
