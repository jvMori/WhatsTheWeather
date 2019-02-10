package com.example.jvmori.myweatherapp.view;

import android.content.Context;

import com.example.jvmori.myweatherapp.architectureComponents.ui.view.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SlidePagerAdapter extends FragmentStatePagerAdapter
{
    private Context ctx;
    private List<WeatherFragment> fragments;

    public SlidePagerAdapter(Context ctx, FragmentManager fm, List<WeatherFragment> fragments) {
        super(fm);
        this.ctx = ctx;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
       return fragments.get(i);
    }

    @Override
    public int getCount() {
       return fragments.size();
    }
}
