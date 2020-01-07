package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.databinding.SearchFragmentBinding;
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

}
