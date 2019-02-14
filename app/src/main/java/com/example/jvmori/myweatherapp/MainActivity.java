package com.example.jvmori.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.activity.SearchActivity;
import com.example.jvmori.myweatherapp.architectureComponents.util.Const;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.fragment.WeatherFragment;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.SlidePagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {
    private MutableLiveData<List<CurrentWeather>> allWeather;

    public static ArrayList<Locations> locations;
    private static ArrayList<String> tempLoc;
    private SlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;
    int mDotCount;
    ImageView[] dots;
    ImageView ivSearch, ivMarker;
    TextView tvLocalization;
    LocationManager locationManager;
    LocationListener locationListener;
    SwipeRefreshLayout swipeRefreshLayout;
    LifecycleOwner lifecycleOwner;

    public static String deviceLocation;
    public static List<WeatherFragment> weathers;
    private TabLayout tabLayout;

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
        weathers = new ArrayList<>();

        tabLayout = findViewById(R.id.tabLayout);
        lifecycleOwner = (LifecycleOwner) this;
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        tvLocalization = findViewById(R.id.tvLocalization);
        viewPager = findViewById(R.id.ViewPager);
        ivSearch = findViewById(R.id.ivSearch);
        ivMarker = findViewById(R.id.ivMarker);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity();
            }
        });
        SetupSlidePagerAdapter(weathers);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //UpdateCurrentWeather();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        CheckLocation(this);
        getWeatherFromDb();

    }

    private void getWeatherFromDb() {
        CurrentWeatherViewModel currentWeatherViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
        currentWeatherViewModel.getWeather().observe(this, new Observer<List<CurrentWeatherEntry>>() {
            @Override
            public void onChanged(List<CurrentWeatherEntry> currentWeathers) {
                for (CurrentWeatherEntry currentWeather : currentWeathers) {
                    WeatherParameters weatherParameters = new WeatherParameters(
                            currentWeather.getLocation().getName(),
                            currentWeather.isDeviceLocation(),
                            Const.FORECAST_DAYS
                    );
                    createFragments(currentWeathers, weatherParameters);
                }
            }
        });

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

    private void CheckLocation(final Context context) {
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
                if (weathers.size() == 0)
                    createFragmentAndUpdateAdapter(weatherParameters);
                else
                    updateFragmentAndAdapter(weatherParameters);
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

    private void createFragmentAndUpdateAdapter(WeatherParameters weatherParameters) {
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setWeatherParameters(weatherParameters);
        weathers.add(weatherFragment);
        slidePagerAdapter.notifyDataSetChanged();
    }

    private void createFragments(List<CurrentWeatherEntry> currentWeathers, WeatherParameters weatherParameters ){
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setWeatherParameters(weatherParameters);
        if (weathers.size() != currentWeathers.size())
            weathers.add(weatherFragment);
        slidePagerAdapter.notifyDataSetChanged();
    }

    private void updateFragmentAndAdapter(WeatherParameters weatherParameters) {
        WeatherFragment fragment = (WeatherFragment) slidePagerAdapter.getItem(0);
        fragment.setWeatherParameters(weatherParameters);
        fragment.createView();
    }

    private void SetupSlidePagerAdapter(List<WeatherFragment> data) {
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), data);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
        tabLayout.setupWithViewPager(viewPager);
        //setUiViewPager();
    }

    void setUiViewPager() {
        geoLocMarkerVisibility(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(changeListener);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            geoLocMarkerVisibility(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void geoLocMarkerVisibility(int position) {
        if (position == 0)
            ivMarker.setVisibility(View.VISIBLE);
        else {
            ivMarker.setVisibility(View.INVISIBLE);
        }
    }

}

