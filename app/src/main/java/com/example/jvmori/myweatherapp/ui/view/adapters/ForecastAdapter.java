package com.example.jvmori.myweatherapp.ui.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.databinding.ForecastItemVerticalBinding;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>
{
    private List<ForecastEntity> forecast;

    public ForecastAdapter(List<ForecastEntity> forecast) {
        this.forecast = forecast;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private  ForecastItemVerticalBinding binding;

        ViewHolder(@NonNull ForecastItemVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ForecastEntity forecastEntity){
            binding.setForecast(forecastEntity);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ForecastItemVerticalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.forecast_item_vertical, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(forecast.get(i));
    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }
}
