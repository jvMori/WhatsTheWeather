package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.databinding.SearchFragmentBinding;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItem;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItemOnSwipe;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.example.jvmori.myweatherapp.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DaggerFragment implements
        LocationAdapter.IOnClickListener,
        DeleteLocationItemOnSwipe.IOnDeletedAction {

    private RecyclerView cities, locations;
    private ProgressBar progressBar;
    private SearchView searchView;
    private SearchFragmentBinding binding;
    // private SearchViewModel searchViewModel;
    private ConstraintLayout constraintLayout;
    @Inject
    LocationAdapter locationAdapter;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
//    @Inject
//    DeleteLocationItem deleteLocationItem;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    private void createLocationsAdapter(List<ForecastEntry> forecastEntries) {
        locationAdapter.setCurrentWeathers(forecastEntries);
        locationAdapter.setiOnClickListener(this);
        locations.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        locations.setAdapter(locationAdapter);
        // deleteLocationItem.delete(locations);
        // deleteLocationItem.setiOnDeletedAction(this);
    }

    @Override
    public void onDeleted(int deletedIndex, ForecastEntry deletedItem) {
        showUndoSnackBar(constraintLayout, deletedItem.getLocation().mCityName, deletedIndex, deletedItem);
    }

    private void showUndoSnackBar(ConstraintLayout parentLayout, String name, int deletedIndex, ForecastEntry deletedItem) {
        Snackbar snackbar = Snackbar
                .make(parentLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", view -> locationAdapter.restoreItem(deletedItem, deletedIndex));
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onLocationClicked(ForecastEntry forecastEntry) {
        WeatherParameters weatherParameters = new WeatherParameters(
                forecastEntry.getLocation().mCityName,
                forecastEntry.getLocation().mCityName,
                forecastEntry.isDeviceLocation,
                Const.FORECAST_DAYS
        );
        navigateToWeatherFragment(weatherParameters);
    }

    private void navigateToWeatherFragment(WeatherParameters parameters) {
        //NavDirections directions = SearchFragmentDirections.actionSearchFragmentToWeatherFragment().setWeatherParam(parameters);
        // NavHostFragment.findNavController(this).navigate(directions);
    }

}
