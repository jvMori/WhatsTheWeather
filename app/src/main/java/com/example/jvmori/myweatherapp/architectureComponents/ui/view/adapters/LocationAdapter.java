package com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
    private List<CurrentWeatherResponse> currentWeathers;
    private ItemClicked itemClicked;

    public LocationAdapter(List<CurrentWeatherResponse> locations, Context ctx) {
        this.currentWeathers = locations;
        itemClicked = (ItemClicked) ctx;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Locations tag = (Locations) view.getTag();
                    itemClicked.onItemClicked(currentWeathers.indexOf(tag));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Locations tag = (Locations) view.getTag();
                    itemClicked.onLongPress(currentWeathers.indexOf(tag));
                    return true;
                }
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
        String currTemp = currentWeathers.get(i).getCurrent().mTempC.toString();
        if(i == 0){
            viewHolder.ivMarker.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivMarker.setVisibility(View.INVISIBLE);
        }
        String url = "http:" + currentWeathers.get(i).getCurrent().mCondition.getIcon();
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
}
