package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class DeleteLocationItemOnSwipe implements DeleteLocationItem, RecyclerItemTouchHelper.IOnSwipeListener {

    private LocationAdapter locationAdapter;
    private IOnDeletedAction iOnDeletedAction;

    public DeleteLocationItemOnSwipe(LocationAdapter locationAdapter){
        this.locationAdapter = locationAdapter;
    }

    @Override
    public void deleteListener(RecyclerView recyclerView) {
        deleteOnSwipe(recyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LocationAdapter.ViewHolder){
            if (locationAdapter != null && iOnDeletedAction != null){
                iOnDeletedAction.onDeleted(position);
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
        void onDeleted(int position);
    }
}
