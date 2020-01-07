package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
    private List<ForecastEntity> currentWeathers;
    private IOnClickListener iOnClickListener;
    private ILongClickListener iLongClickListener;

    public void setiLongClickListener(ILongClickListener iLongClickListener) {
        this.iLongClickListener = iLongClickListener;
    }

    public void setiOnClickListener(IOnClickListener iOnClickListener) {
        this.iOnClickListener = iOnClickListener;
    }

    public void setCurrentWeathers(List<ForecastEntity> currentWeathers) {
        this.currentWeathers = currentWeathers;
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

    }

    @Override
    public int getItemCount() {
        return currentWeathers.size();
    }


    public void removeItem(int position){
        currentWeathers.remove(position);
        notifyItemRemoved(position);
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
