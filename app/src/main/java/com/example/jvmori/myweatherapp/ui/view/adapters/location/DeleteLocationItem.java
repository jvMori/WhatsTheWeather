package com.example.jvmori.myweatherapp.ui.view.adapters.location;

import androidx.recyclerview.widget.RecyclerView;

public interface DeleteLocationItem {
    void delete(RecyclerView recyclerView);
    void setiOnDeletedAction(DeleteLocationItemOnSwipe.IOnDeletedAction iOnDeletedAction);
}
