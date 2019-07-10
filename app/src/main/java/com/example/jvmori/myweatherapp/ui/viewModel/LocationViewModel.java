package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.ProviderStatus;
import com.example.jvmori.myweatherapp.ui.Resource;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class LocationViewModel  extends ViewModel {

    private LocationProvider locationProvider;

    @Inject
    public LocationViewModel(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
    }

    public void CheckLocation(){
        locationProvider.CheckLocation();
    }

    public LiveData<Resource<Location>> getDeviceLocation(){
        return locationProvider.deviceLocation();
    }

    public  LiveData<ProviderStatus> getProviderStatus(){
        return locationProvider.providerStatus();
    }


    public  String getCity(Location location, Context context){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0)
                return addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setLocationProviderActivity(Activity activity){
        locationProvider.setActivity(activity);
    }
}

