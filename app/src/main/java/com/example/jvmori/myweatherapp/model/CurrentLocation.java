package com.example.jvmori.myweatherapp.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.jvmori.myweatherapp.utils.OnLocationRetrieve;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation implements LocationListener {

    private Context context;
    private OnLocationRetrieve callback;
    private Activity activity;
    private LocationManager locationManager;

    public CurrentLocation(Context context, Activity activity, OnLocationRetrieve callback) {
        this.context = context;
        this.callback = callback;
        this.activity = activity;
        this.locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3600, 5000, this);
        }

        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLoc != null && this.callback != null)
            callback.OnLocationChanged(getCity(lastLoc));
    }

    @Override
    public void onLocationChanged(Location location) {
       String city = getCity(location);
       if (this.callback != null)
           callback.OnLocationChanged(city);
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

    public String getCity(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0)
                return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
