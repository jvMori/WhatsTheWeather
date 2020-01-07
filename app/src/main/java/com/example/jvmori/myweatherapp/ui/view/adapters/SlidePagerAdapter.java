package com.example.jvmori.myweatherapp.ui.view.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jvmori.myweatherapp.ui.view.fragment.CustomWeatherFragment;
import com.example.jvmori.myweatherapp.ui.view.fragment.GeoWeatherFragment;

public class SlidePagerAdapter extends FragmentStateAdapter
{
    private int size;

    public SlidePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int size) {
        super(fragmentManager, lifecycle);
        this.size = size;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new GeoWeatherFragment();
        }
        else{
            CustomWeatherFragment customWeatherFragment = new CustomWeatherFragment();
            customWeatherFragment.city = "Gdynia";
            return customWeatherFragment;
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
