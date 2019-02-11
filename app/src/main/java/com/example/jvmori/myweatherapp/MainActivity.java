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
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jvmori.myweatherapp.architectureComponents.data.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.WeatherFragment;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.data.WeatherData;
import com.example.jvmori.myweatherapp.model.CurrentLocation;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.Contains;
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.view.SlidePagerAdapter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
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
    LinearLayout layoutDots;
    int mDotCount;
    ImageView[] dots;
    ImageView ivSearch, ivMarker;
    TextView tvLocalization;
    LocationManager locationManager;
    LocationListener locationListener;
    SwipeRefreshLayout swipeRefreshLayout;
    LifecycleOwner lifecycleOwner;

    public static String deviceLocation;
    private List<WeatherFragment> weathers;

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

        lifecycleOwner = (LifecycleOwner) this;
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        tvLocalization = findViewById(R.id.tvLocalization);
        viewPager = findViewById(R.id.ViewPager);
        layoutDots = findViewById(R.id.layoutDots);
        ivSearch = findViewById(R.id.ivSearch);
        ivMarker = findViewById(R.id.ivMarker);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity(view);
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
        //getWeatherFromDb();

    }

    private void getWeatherFromDb() {
        CurrentWeatherViewModel currentWeatherViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
        currentWeatherViewModel.getAllWeather().observe(this, new Observer<List<CurrentWeather>>() {
            @Override
            public void onChanged(List<CurrentWeather> currentWeathers) {
                for (CurrentWeather currentWeather : currentWeathers) {
                    WeatherParameters weatherParameters = new WeatherParameters(
                            currentWeather.getLocation(),
                            currentWeather.isDeviceLocation(),
                            currentWeather.getFetchTime()
                    );
                    weatherFragmentsAdapter(weatherParameters);
                }
            }
        });

    }

    private void UpdateCurrentWeather() {
        final int currentItem = viewPager.getCurrentItem();
        String currentLocation = locations.get(currentItem).getId();
        WeatherData weatherData = new WeatherData();
        weatherData.getResponse(new WeatherAsyncResponse() {
            @Override
            public void processFinished(Locations locationData) {
                locations.set(currentItem, locationData);
                slidePagerAdapter.notifyDataSetChanged();
            }
        }, new OnErrorResponse() {
            @Override
            public void displayErrorMessage(String message) {
                Toast.makeText(MainActivity.this, "Wrong City Name!", Toast.LENGTH_SHORT).show();
            }
        }, currentLocation);
    }

    private void UpdateWeather() {
        tempLoc = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            tempLoc.add(locations.get(i).getId());
        }
        locations.clear();
        for (String loc : tempLoc) {
            getWeather(loc);
        }
    }

    private boolean ShouldBeWeatherDataUpdate() {
        Calendar calendar = Calendar.getInstance();
        long currTime = calendar.getTimeInMillis();
        return currTime - locations.get(0).getUpdateTime() > 3600000;
    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3600, 10000, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                //deviceLocation = CurrentLocation.getCity(location, this);
                deviceLocation = location.getLatitude() + "," + location.getLongitude();
                //weatherFragmentsAdapter();
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
                        ZonedDateTime.now().minusHours(1)
                );
                weatherFragmentsAdapter(weatherParameters);
                getWeatherFromDb();
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

    private void FindWeatherForLocation(Location location, Context context) {
        String city = CurrentLocation.getCity(location, context);

        if (locations.size() > 0 && city != null && city == locations.get(0).getId())
            return;

        if (city != null && locations.size() == 0)
            getWeather(city);

    }

    private void getWeather(final String cityName) {
        WeatherData weatherData = new WeatherData();
        weatherData.getResponse(new WeatherAsyncResponse() {
            @Override
            public void processFinished(Locations locationData) {
                if (Contains.containsName(locations, locationData.getId()) == -1) {
                    locations.add(locationData);
                    // if (tempLoc != null && locations.size() == tempLoc.size())
                    //SetData(locations);
                    // else if (tempLoc == null)
                    // SetData(locations);
                }
            }
        }, new OnErrorResponse() {
            @Override
            public void displayErrorMessage(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, cityName);
    }


    private void SearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void weatherFragmentsAdapter(WeatherParameters weatherParameters) {
        if (weathers.size() == 0)
            createFragmentAndUpdateAdapter(weatherParameters);
        else {
            for (WeatherFragment weatherFrag : weathers) {
                if (weatherFrag.getWeatherParameters().getLocation() != weatherParameters.getLocation()){
                    createFragmentAndUpdateAdapter(weatherParameters);
                }
            }
        }
    }
    private void createFragmentAndUpdateAdapter(WeatherParameters weatherParameters){
        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setWeatherParameters(weatherParameters);
        weathers.add(weatherFragment);
        slidePagerAdapter.notifyDataSetChanged();
    }

    private void SetupSlidePagerAdapter(List<WeatherFragment> data) {
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), data);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
        //setUiViewPager();
    }

    void setUiViewPager() {
        int oldDots = layoutDots.getChildCount();
        if (oldDots > 0) {
            for (int j = 0; j < oldDots; j++) {
                layoutDots.removeViewAt(j);
            }
        }

        mDotCount = slidePagerAdapter.getCount();
        dots = new ImageView[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.dotinactive);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dots[i].setLayoutParams(layoutParams);
            layoutParams.setMargins(5, 0, 5, 0);
            layoutParams.gravity = Gravity.CENTER;

            layoutDots.addView(dots[i], layoutParams);
        }
        dots[viewPager.getCurrentItem()].setImageResource(R.drawable.dotactive);
        changeActiveCityName(viewPager.getCurrentItem());
        geoLocMarkerVisibility(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(changeListener);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            changeActiveDot(i);
            changeActiveCityName(i);
            geoLocMarkerVisibility(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void changeActiveDot(int position) {
        for (int i = 0; i < mDotCount; i++) {
            dots[i].setImageResource(R.drawable.dotinactive);
        }
        dots[position].setImageResource(R.drawable.dotactive);
    }

    private void changeActiveCityName(int position) {
        tvLocalization.setText(locations.get(position).getId());
    }

    private void geoLocMarkerVisibility(int position) {
        if (position == 0)
            ivMarker.setVisibility(View.VISIBLE);
        else {
            ivMarker.setVisibility(View.INVISIBLE);
        }
    }


}

