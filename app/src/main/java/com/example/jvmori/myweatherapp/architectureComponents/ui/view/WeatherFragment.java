package com.example.jvmori.myweatherapp.architectureComponents.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private View view;
    private TextView mainTemp, minMaxTemp, desc;
    private ImageView ivIcon;
    private RecyclerView recyclerView;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_loc_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainTemp = view.findViewById(R.id.tvMainCurrTemp);
        minMaxTemp = view.findViewById(R.id.tvMinMaxMain);
        ivIcon = view.findViewById(R.id.ivMainIcon);
        desc = view.findViewById(R.id.tvDescriptionMain);
        recyclerView = view.findViewById(R.id.RecyclerViewList);
        getCurrentWeatherView();
    }

    private void getCurrentWeatherView(){
        CurrentWeatherViewModel viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
        viewModel.getCurrentWeather("London", "en").observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                createCurrentWeatherUi(currentWeather);
            }
        });
       
    }

    private void createCurrentWeatherUi(CurrentWeather currentWeather){
        String description = currentWeather.mCondition.getText();
        String minTemp = currentWeather.mFeelslikeC.toString();
        String maxTemp = currentWeather.mHumidity.toString();
        String minMax = String.format("%s° / %s°", maxTemp, minTemp);

        mainTemp.setText(String.format("%s °C", currentWeather.mTempC.toString()));
        desc.setText(description);
        minMaxTemp.setText(minMax);
        String url = "http:" + currentWeather.mCondition.getIcon();
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_11)
                .into(ivIcon);
    }

}
