package com.example.jvmori.myweatherapp.architectureComponents.ui.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.ForecastAdapter;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View view;
    private TextView mainTemp, feelsLike, desc, humidity, pressure, city;
    private LinearLayout errorLayout;
    private ImageView ivIcon;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private WeatherParameters weatherParameters;
    private CurrentWeatherViewModel viewModel;

    public void setWeatherParameters(WeatherParameters weatherParameters){
        this.weatherParameters = weatherParameters;
    }

    public WeatherParameters getWeatherParameters() {
        return weatherParameters;
    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        mainTemp = view.findViewById(R.id.tvMainCurrTemp);
        feelsLike = view.findViewById(R.id.feelsTemp);
        humidity = view.findViewById(R.id.Humidity);
        pressure = view.findViewById(R.id.Pressure);
        ivIcon = view.findViewById(R.id.ivMainIcon);
        desc = view.findViewById(R.id.tvDescriptionMain);
        errorLayout = view.findViewById(R.id.errorLayout);
        city = view.findViewById(R.id.locationTextView);
        recyclerView = view.findViewById(R.id.RecyclerViewList);
        return view;
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
        if (weatherParameters!= null)
            createView();
    }

    public void createView(){
        viewModel.getForecast(weatherParameters).observe(this, this::createCurrentWeatherUi);
    }
    private void createCurrentWeatherUi(ForecastEntry forecastEntry){
        weatherParameters.setLocation(forecastEntry.getLocation().getName());
        CurrentWeather currentWeather = forecastEntry.getCurrentWeather();

        String cityAndCountry = String.format("%s, %s",
                forecastEntry.getLocation().getName(),
                forecastEntry.getLocation().getCountry());
        String description = currentWeather.mCondition.getText();
        String feelslike = "Feels like: " + currentWeather.mFeelslikeC.toString() + "°";
        String humidityTxt= "Humidity: " + currentWeather.mHumidity.toString() + " %";
        String pressureTxt = currentWeather.mPressureMb.toString() + " hPa";
        String temp = currentWeather.mTempC.toString() + "°";

        city.setText(cityAndCountry);
        desc.setText(description);
        mainTemp.setText(temp);
        feelsLike.setText(feelslike);
        humidity.setText(humidityTxt);
        pressure.setText(pressureTxt);
        String url = "http:" + currentWeather.mCondition.getIcon();
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_11)
                .into(ivIcon);

        createRecyclerView(forecastEntry);
    }

    private void createRecyclerView(ForecastEntry forecastEntry){
        forecastAdapter = new ForecastAdapter(forecastEntry.getForecast().mFutureWeather,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(forecastAdapter);
    }
}
