package com.example.jvmori.myweatherapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.model.Forecast;
import com.example.jvmori.myweatherapp.utils.SetImage;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>
{
    private ArrayList<Forecast> forecast;
    private  Context context;
    public ForecastAdapter(ArrayList<Forecast> forecast, Context context) {
        this.forecast = forecast;
        this.context = context;
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
        String code = forecast.get(i).getCode();
        SetImage.setImageView(context, code,  viewHolder.ivIcon);


    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }
}
