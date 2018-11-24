package com.example.jvmori.myweatherapp.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.model.Forecast;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>
{
    private ArrayList<Forecast> forecast;

    public ForecastAdapter(ArrayList<Forecast> forecast, Context context) {
        this.forecast = forecast;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView day, minMaxTemp;

        public ViewHolder(@NonNull View itemView) {
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
        String[] date = forecast.get(i).getDate().split(" ");
        String dayData = forecast.get(i).getDay();
        String dayDate = String.format("%s %s, %s", date[0], date[1], dayData);
        String minTemp = forecast.get(i).getTempLow();
        String highTemp = forecast.get(i).getTempHigh();
        String minMax = String.format("%s° / %s°", highTemp, minTemp);

        viewHolder.day.setText(dayDate);
        viewHolder.minMaxTemp.setText(minMax);
    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }
}
