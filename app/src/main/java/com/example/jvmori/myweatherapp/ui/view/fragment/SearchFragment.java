package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.databinding.SearchFragmentBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DaggerFragment {

    private SearchFragmentBinding binding;
    @Inject
    LocationAdapter locationAdapter;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private CurrentWeatherViewModel currentWeatherViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentWeatherViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(CurrentWeatherViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        currentWeatherViewModel.fetchAllWeather();
        currentWeatherViewModel.getAllWeather().observe(this, resultt -> {
            if (resultt.status == Resource.Status.LOADING){

            }
            else if (resultt.status == Resource.Status.SUCCESS){
                Log.i("data", resultt.data.toString());
            }
            else if (resultt.status == Resource.Status.ERROR){

            }
        });
    }
}
