package com.example.jvmori.myweatherapp.model;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.ForecastAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocWeatherFrag extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    private ArrayList<Forecast> forecasts;

    public LocWeatherFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loc_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RecyclerViewList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new ForecastAdapter(getForecasts(), getContext());
        recyclerView.setAdapter(myAdapter);
    }

    public ArrayList<Forecast> getForecasts(){
        return forecasts;
    }

    public void setForecasts(ArrayList<Forecast> forecasts){
        this.forecasts = forecasts;
    }

}
