package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.databinding.SearchFragmentBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;

import java.util.List;

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
    private LocationAdapter adapter;

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
        observeAllWeather();
        currentWeatherViewModel.observeSearchCityResults();
        setOnQueryTextChangeListener();
        currentWeatherViewModel.getCurrentWeather().observe(this, result -> {
            if (result.status == Resource.Status.SUCCESS) {
                navigateToHome(adapter.getItemCount() - 1);
            } else if (result.status == Resource.Status.ERROR) {
                Log.i("error", result.message);
            }
        });
    }

    private void navigateToHome(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(Const.locationIndex, index);
        Navigation.findNavController(this.binding.getRoot()).navigate(R.id.action_searchFragment_to_homeFragment, bundle);
    }

    private void setOnQueryTextChangeListener() {
        binding.searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentWeatherViewModel.searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void observeAllWeather() {
        currentWeatherViewModel.getAllWeather().observe(this, resultt -> {
            if (resultt.status == Resource.Status.LOADING) {

            } else if (resultt.status == Resource.Status.SUCCESS) {
                successView(resultt.data);
            } else if (resultt.status == Resource.Status.ERROR) {

            }
        });
    }

    private void successView(List<CurrentWeatherUI> currentWeatherUIList) {
        adapter = new LocationAdapter();
        adapter.setCurrentWeathers(currentWeatherUIList);
        binding.locations.setAdapter(adapter);
        binding.locations.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
    }
}
