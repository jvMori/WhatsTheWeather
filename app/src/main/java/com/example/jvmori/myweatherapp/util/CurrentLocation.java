package com.example.jvmori.myweatherapp.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CurrentLocation {

    public static String getCity(Location location, Context context){
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

}
