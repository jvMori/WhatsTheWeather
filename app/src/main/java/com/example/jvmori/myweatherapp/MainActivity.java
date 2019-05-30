package com.example.jvmori.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.application.WeatherApplication;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.ui.view.activity.SearchActivity;
import com.example.jvmori.myweatherapp.ui.view.adapters.LocationAdapter;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.ui.view.fragment.WeatherFragment;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.SlidePagerAdapter;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity {

    private SlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;
    ImageView ivSearch, ivMarker;
    TextView tvLocalization;
    LocationManager locationManager;
    LocationListener locationListener;
    SwipeRefreshLayout swipeRefreshLayout;
    LifecycleOwner lifecycleOwner;
    public static String deviceLocation;
    public static List<WeatherFragment> weathers;
    private TabLayout tabLayout;
    private ILoadImage iLoadImage;
    private WeatherViewModel weatherViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        bindView();

        int position = getIntent().getIntExtra("position", -1);
        if (position == -1) {
             //CheckLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        weathers = new ArrayList<>();
        createSlidePagerAdapter(weathers);
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setWeatherParameters(new WeatherParameters("Kleparz", false, "7"));
        weatherFragment.setImageLoader(iLoadImage);
        weathers.add(weatherFragment);
        slidePagerAdapter.notifyDataSetChanged();
        //getWeatherFromDb();
    }

    private void bindView() {
        tabLayout = findViewById(R.id.tabLayout);
        lifecycleOwner = this;
        tvLocalization = findViewById(R.id.tvLocalization);
        viewPager = findViewById(R.id.ViewPager);
        ivSearch = findViewById(R.id.ivSearch);
        ivMarker = findViewById(R.id.ivMarker);
        iLoadImage = ((WeatherApplication) getApplication()).imageLoader();
        ivSearch.setOnClickListener((view) -> SearchActivity());
    }

    private void getWeatherFromDb() {
        weatherViewModel.allForecastsFromDb();
        weatherViewModel.getAllWeather().observe(this, weatherFromDb -> {
            createWeatherFragments(weatherFromDb);
            setCurrentViewPagerPosition();
        });
    }

    private void createWeatherFragments(List<ForecastEntry> weatherFromDb) {
        for (ForecastEntry currentWeather : weatherFromDb) {
            if (weathers.size() != weatherFromDb.size())
                createFragmentAndUpdateAdapter(currentWeather);
        }
    }

    private void setCurrentViewPagerPosition() {
        int position = getIntent().getIntExtra("position", 0);
        new Handler().post(() -> viewPager.setCurrentItem(position, true));
    }

    private void createFragmentAndUpdateAdapter(ForecastEntry forecastEntry) {
        WeatherFragment weatherFragment = createFragment(forecastEntry);
        weathers.add(weatherFragment);
        slidePagerAdapter.notifyDataSetChanged();
    }

    private WeatherFragment createFragment(ForecastEntry forecastEntry) {
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setForecastEntry(forecastEntry);
        weatherFragment.setImageLoader(iLoadImage);
        return weatherFragment;
    }

    private void getWeather(WeatherParameters parameters) {
        disposable.add(
                weatherViewModel.getWeather(parameters).subscribe(
                        this::displayWeather,
                        error -> Log.i("WEATHER", "Error while loading data")
                )
        );
    }

    private void displayWeather(ForecastEntry forecastEntry) {
        if (weathers != null && weathers.size() > 0) {
            weathers.set(0, createFragment(forecastEntry));
            slidePagerAdapter.notifyDataSetChanged();
        } else {
            createFragmentAndUpdateAdapter(forecastEntry);
        }
    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3600, 5000, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                deviceLocation = location.getLatitude() + "," + location.getLongitude();
            }
        }
    }

    private void CheckLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                deviceLocation = location.getLatitude() + "," + location.getLongitude();
                WeatherParameters weatherParameters = new WeatherParameters(
                        deviceLocation,
                        true,
                        Const.FORECAST_DAYS
                );
                //getWeather(weatherParameters);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startListening();
        }

    }

    private void SearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void createSlidePagerAdapter(List<WeatherFragment> data) {
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), data);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

