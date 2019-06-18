package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;

public class DeleteLocationItemOnSwipe implements DeleteLocationItem, RecyclerItemTouchHelper.IOnSwipeListener {

    private WeatherViewModel weatherViewModel;
    private LocationAdapter locationAdapter;

    public DeleteLocationItemOnSwipe(WeatherViewModel weatherViewModel, LocationAdapter locationAdapter){
        this.weatherViewModel = weatherViewModel;
        this.locationAdapter = locationAdapter;
    }

    @Override
    public void delete(RecyclerView recyclerView) {
        deleteOnSwipe(recyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LocationAdapter.ViewHolder){
            String loc = locationAdapter.getItemAtPosition(position).mCityName;
            weatherViewModel.deleteWeather(loc);
            locationAdapter.removeItem(position);
        }
    }

    private void deleteOnSwipe(RecyclerView locations) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(locations);
    }
}
