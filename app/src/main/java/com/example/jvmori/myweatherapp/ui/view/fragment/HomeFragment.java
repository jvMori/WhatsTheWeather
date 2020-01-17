package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.databinding.HomeFragmentBind;
import com.example.jvmori.myweatherapp.databinding.MainActivityBinding;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.view.adapters.SlidePagerAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.jvmori.myweatherapp.util.Const.locationIndex;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends DaggerFragment {

    private HomeFragmentBind binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    private CurrentWeatherViewModel currentWeatherViewModel;

    @Inject
    ViewModelProviderFactory factory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    private void navigateToSearchFragment(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_homeFragment_to_searchFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        currentWeatherViewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel.class);
        currentWeatherViewModel.fetchCities();
        observeCitiesAndDisplayView();
        binding.ivSearch.setOnClickListener(this::navigateToSearchFragment);
        scrollToCurrentCity();
    }

    private void observeCitiesAndDisplayView() {
        if (this.getActivity() != null) {
            currentWeatherViewModel.getCities().observe(this, result -> {
                if (result.status == Resource.Status.SUCCESS) {
                    if (result.data != null){
                        if (result.data.isEmpty())
                            createPageAdapter(new ArrayList<>());
                        else
                            createPageAdapter(result.data);
                    }
                } else if (result.status == Resource.Status.ERROR) {

                }
            });
        }
    }

    private void scrollToCurrentCity() {
        if (getArguments() != null) {
            int index = getArguments().getInt(locationIndex);
            binding.pages.setCurrentItem(index, true);
        }
    }

    private void createPageAdapter(List<String> cities) {
        SlidePagerAdapter adapter = new SlidePagerAdapter(getChildFragmentManager(), this.getLifecycle(), cities);
        binding.pages.setAdapter(adapter);
        binding.pages.setNestedScrollingEnabled(true);
    }
}
