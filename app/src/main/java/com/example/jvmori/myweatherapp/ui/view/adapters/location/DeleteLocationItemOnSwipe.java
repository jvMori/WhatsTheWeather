package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;


public class DeleteLocationItemOnSwipe implements DeleteLocationItem, RecyclerItemTouchHelper.IOnSwipeListener {

    //private WeatherViewModel weatherViewModel;
    private LocationAdapter locationAdapter;
    private IOnDeletedAction iOnDeletedAction;

    public DeleteLocationItemOnSwipe(LocationAdapter locationAdapter){
        this.locationAdapter = locationAdapter;
    }

    @Override
    public void delete(RecyclerView recyclerView) {
        deleteOnSwipe(recyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LocationAdapter.ViewHolder){
            if (locationAdapter != null){
                String loc  = locationAdapter.getItemAtPosition(position).mCityName;
                final ForecastEntry deletedItem = locationAdapter.getWeatherAtPosition(position);
                locationAdapter.removeItem(position);
//                if (weatherViewModel != null)
//                    weatherViewModel.deleteWeather(loc);
                if(iOnDeletedAction != null) iOnDeletedAction.onDeleted(position, deletedItem);
            }
        }
    }

    private void deleteOnSwipe(RecyclerView locations) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(locations);
    }

    @Override
    public void setiOnDeletedAction(IOnDeletedAction iOnDeletedAction) {
        this.iOnDeletedAction = iOnDeletedAction;
    }

    public interface IOnDeletedAction{
        void onDeleted(int deletedIndex, ForecastEntry deletedItem);
    }
}
