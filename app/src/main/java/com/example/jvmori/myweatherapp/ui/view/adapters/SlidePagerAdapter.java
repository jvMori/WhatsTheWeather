package com.example.jvmori.myweatherapp.ui.view.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jvmori.myweatherapp.ui.view.fragment.CustomWeatherFragment;
import com.example.jvmori.myweatherapp.ui.view.fragment.GeoWeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class SlidePagerAdapter extends FragmentStateAdapter
{
    private List<String> cities;

    public SlidePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> cities) {
        super(fragmentManager, lifecycle);
        this.cities = cities;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new GeoWeatherFragment();
        }
        else {
            CustomWeatherFragment customWeatherFragment = new CustomWeatherFragment();
            customWeatherFragment.city = cities.get(position);
            return customWeatherFragment;
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
