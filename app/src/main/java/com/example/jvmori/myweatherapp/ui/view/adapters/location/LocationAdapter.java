package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.databinding.LocationItemBinding;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
    private List<CurrentWeatherUI> currentWeathers;
    private IOnClickListener iOnClickListener;
    private ILongClickListener iLongClickListener;

    public void setiLongClickListener(ILongClickListener iLongClickListener) {
        this.iLongClickListener = iLongClickListener;
    }

    public void setiOnClickListener(IOnClickListener iOnClickListener) {
        this.iOnClickListener = iOnClickListener;
    }

    public void setCurrentWeathers(List<CurrentWeatherUI> currentWeathers) {
        this.currentWeathers = currentWeathers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LocationItemBinding binding;

        public ViewHolder(@NonNull LocationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CurrentWeatherUI currentWeatherUI){
            binding.setCurrentWeather(currentWeatherUI);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       LocationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.location_item, viewGroup, false);
       return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(currentWeathers.get(i));
    }

    @Override
    public int getItemCount() {
        return currentWeathers.size();
    }


    public void removeItem(int position){
        currentWeathers.remove(position);
        notifyItemRemoved(position);
    }

    public String getCityName(int position){
        return currentWeathers.get(position).getCity();
    }

    public void restoreItem() {

    }

    public interface IOnClickListener {
        void onLocationClicked(ForecastEntity forecastEntry);
    }

    public interface ILongClickListener{
        void onLongClick(String location);
    }
}
