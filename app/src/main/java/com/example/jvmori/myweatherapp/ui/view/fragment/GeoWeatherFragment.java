package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.ui.LifecycleBoundLocationManager;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import java.util.Objects;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeoWeatherFragment extends BaseWeatherFragment implements LocationServiceDialog.ILocationServiceDialog {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    ILoadImage iLoadImage;

    private LifecycleBoundLocationManager lifecycleBoundLocationManager;

    private String defaultCity = "Gdynia";

    public GeoWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            lifecycleBoundLocationManager = ((MainActivity) getActivity()).lifecycleBoundLocationManager;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (lifecycleBoundLocationManager != null) {
            observeLocationChanges();
            observeLocationProviderStatus(lifecycleBoundLocationManager);
        }
    }

    private void observeLocationChanges() {
        lifecycleBoundLocationManager.deviceLocation().observe(this, location -> {
            if (location != null && location.status == Resource.Status.SUCCESS && location.data != null) {
                currentWeatherViewModel.fetchWeatherByGeographic(location.data);
            }
        });
    }

    private void observeLocationProviderStatus(LifecycleBoundLocationManager lifecycleBoundLocationManager) {
        lifecycleBoundLocationManager.deviceLocation().observe(this, locationResource -> {
            if (locationResource.status == Resource.Status.ERROR) {
                LocationServiceDialog dialog = new LocationServiceDialog();
                dialog.setILocationServiceDialog(this);
                assert this.getFragmentManager() != null;
                dialog.show(this.getFragmentManager(), null);
            }
        });
    }

    @Override
    public void onPositiveBtn() {
        if (getActivity() != null)
            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    @Override
    public void onCancel() {
        currentWeatherViewModel.fetchCurrentWeather(defaultCity);
    }
}
