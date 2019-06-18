package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.Location;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
    private List<ForecastEntry> currentWeathers;
    private IOnClickListener iOnClickListener;
    private ILongClickListener iLongClickListener;

    public LocationAdapter(List<ForecastEntry> locations,
                           IOnClickListener iOnClickListener,
                           ILongClickListener iLongClickListener
                           ) {
        this.currentWeathers = locations;
        this.iOnClickListener = iOnClickListener;
        this.iLongClickListener = iLongClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName, tvCurrentTemp;
        ImageView ivMarker, ivIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCurrLocItem);
            tvCurrentTemp = itemView.findViewById(R.id.tvCurrTempLocItem);
            ivMarker = itemView.findViewById(R.id.ivLocationMarker);
            ivIcon = itemView.findViewById(R.id.ivIconLocItem);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != -1 && iOnClickListener != null)
                    iOnClickListener.onLocationClicked(currentWeathers.get(position));
            });

            itemView.setOnLongClickListener(view -> {
                int position = getAdapterPosition();
                if (position != -1 && iLongClickListener != null)
                    iLongClickListener.onLongClick(currentWeathers.get(position).getLocation().mCityName);
                return true;
            });
        }
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_item, viewGroup, false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(currentWeathers.get(i));
        String cityName = currentWeathers.get(i).getLocation().getName();
        String currTemp = currentWeathers.get(i).getCurrentWeather().mTempC.toString();
        Boolean isDeviceLoc = currentWeathers.get(i).isDeviceLocation;
        if(isDeviceLoc){
            viewHolder.ivMarker.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivMarker.setVisibility(View.INVISIBLE);
        }
        String url = "http:" + currentWeathers.get(i).getCurrentWeather().mCondition.getIcon();
        Picasso.get()
                .load(url)
                .into(viewHolder.ivIcon);
        viewHolder.tvCityName.setText(cityName);
        viewHolder.tvCurrentTemp.setText(String.format("%s°C", currTemp));
    }

    @Override
    public int getItemCount() {
        return currentWeathers.size();
    }

    public Location getItemAtPosition(int position){
        return currentWeathers.get(position).getLocation();
    }
    public void removeItem(int position){
        currentWeathers.remove(position);
        notifyItemRemoved(position);
    }
    public void addForecastAndNotifyAdapter(ForecastEntry forecastEntry){
        for (ForecastEntry weather: currentWeathers) {
            if (weather.getLocation().mCityName.equals(forecastEntry.getLocation().mCityName))
                return;
        }
        currentWeathers.add(forecastEntry);
        notifyDataSetChanged();
    }

    public interface IOnClickListener {
        void onLocationClicked(ForecastEntry forecastEntry);
    }

    public interface ILongClickListener{
        void onLongClick(String location);
    }
}
