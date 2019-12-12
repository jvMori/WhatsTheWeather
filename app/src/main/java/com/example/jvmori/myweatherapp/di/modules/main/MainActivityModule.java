package com.example.jvmori.myweatherapp.di.modules.main;

import android.content.Context;
import android.location.LocationManager;
import android.widget.ProgressBar;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.LocationProviderImpl;
import com.example.jvmori.myweatherapp.data.forecast.ForecastDao;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.LifecycleBoundLocationManager;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItem;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItemOnSwipe;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.sql.PreparedStatement;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

//    @Provides
//    @MainActivityScope
//    public DeleteLocationItem provideDeleteLocationItem(WeatherViewModel weatherViewModel, LocationAdapter locationAdapter) {
//        return new DeleteLocationItemOnSwipe(weatherViewModel, locationAdapter);
//    }

    @Provides
    @MainActivityScope
    public LocationAdapter locationAdapter(){
        return new LocationAdapter();
    }

    @Provides
    @MainActivityScope
    public LocationManager provideLocationManager(MainActivity mainActivity){
        return (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @MainActivityScope
    public LocationRequest provideLocationRequest(){
        return new LocationRequest()
                   .setInterval(5000)
                    .setFastestInterval(5000)
                   .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @MainActivityScope
    @Provides
    public LocationProvider provideLocation(LocationManager locationManager, MainActivity mainActivity){
        return new LocationProviderImpl(
                mainActivity,
                locationManager,
                3600,
                500
        );
    }
}
