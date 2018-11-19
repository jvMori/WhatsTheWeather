package com.example.jvmori.myweatherapp;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jvmori.myweatherapp.data.Locations;

import java.util.ArrayList;
import java.util.List;

public class SlidePagerAdapter extends FragmentStatePagerAdapter
{
    private Context ctx;
    private ArrayList<Locations> data;
    private Fragment[] fragments;

    public SlidePagerAdapter(Context ctx, FragmentManager fm,ArrayList<Locations> data) {
        super(fm);
        this.ctx = ctx;
        this.data = data;
        fragments = new Fragment[data.size()];
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        Locations item = data.get(i);

        LocWeatherFrag locWeatherFrag = new LocWeatherFrag();
        locWeatherFrag.setForecasts(item.getForecasts());
        fragment = locWeatherFrag;

        if (fragments[i] == null){
            fragments[i] = fragment;
        }

        return fragments[i];
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        return 0;
    }
}
