package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.databinding.HomeFragmentBind;
import com.example.jvmori.myweatherapp.ui.view.adapters.SlidePagerAdapter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private HomeFragmentBind binding;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getActivity() != null) {
            createPageAdapter();
        }
    }

    private void createPageAdapter() {
        SlidePagerAdapter adapter = new SlidePagerAdapter(getChildFragmentManager(), this.getLifecycle(), 2);
        binding.pages.setAdapter(adapter);
        binding.pages.setNestedScrollingEnabled(true);
    }
}
