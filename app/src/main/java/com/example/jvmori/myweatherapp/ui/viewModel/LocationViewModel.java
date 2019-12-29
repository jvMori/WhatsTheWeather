package com.example.jvmori.myweatherapp.ui.viewModel;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.ProviderStatus;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.view.fragment.LocationServiceDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LocationViewModel  extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public LocationViewModel() {
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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

