package com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.FutureWeather;
import com.example.jvmori.myweatherapp.model.Forecast;
import com.example.jvmori.myweatherapp.utils.SetImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>
{
    private List<FutureWeather> forecast;
    private  Context context;

    public ForecastAdapter(List<FutureWeather> forecast, Context context) {
        this.forecast = forecast;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView day, minMaxTemp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIconForecast);
            day = itemView.findViewById(R.id.tvDayForecast);
            minMaxTemp = itemView.findViewById(R.id.tvTempMaxMin);
        }
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder viewHolder, int i) {
        String dayDate = forecast.get(i).getDate();
        String minTemp = forecast.get(i).getDay().getMintempC().toString();
        String highTemp = forecast.get(i).getDay().getMaxtempC().toString();
        String minMax = String.format("%s° / %s°", highTemp, minTemp);

        viewHolder.day.setText(dayDate);
        viewHolder.minMaxTemp.setText(minMax);
        String url = "http:" + forecast.get(i).getDay().getCondition().getIcon();
        Picasso.get().load(url).into(viewHolder.ivIcon);

    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }
}
