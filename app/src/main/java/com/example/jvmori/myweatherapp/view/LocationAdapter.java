package com.example.jvmori.myweatherapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.example.jvmori.myweatherapp.utils.SetImage;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
    private ArrayList<Locations> locations;
    private Context ctx;
    private ItemClicked itemClicked;

    public LocationAdapter(ArrayList<Locations> locations, Context ctx) {
        this.locations = locations;
        this.ctx = ctx;
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
                    itemClicked.onItemClicked(locations.indexOf(tag));
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
        viewHolder.itemView.setTag(locations.get(i));
        String cityName = locations.get(i).getCurrentWeather().getCity();
        String currTemp = locations.get(i).getCurrentWeather().getCurrentTemp();
        if(i == 0){
            viewHolder.ivMarker.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivMarker.setVisibility(View.INVISIBLE);
        }
        String code = locations.get(i).getCurrentWeather().getCode();

        SetImage.setImageView(ctx, code, viewHolder.ivIcon);
        viewHolder.tvCityName.setText(cityName);
        viewHolder.tvCurrentTemp.setText(String.format("%s°", currTemp));

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
