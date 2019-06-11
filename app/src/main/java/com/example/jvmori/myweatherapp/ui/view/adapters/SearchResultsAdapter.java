package com.example.jvmori.myweatherapp.ui.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.network.response.Search;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder> {
    private List<Search> items = new ArrayList<>();
    private IOnItemClicked iOnItemClicked;

    public void setSearchedResult(List<Search> items, IOnItemClicked iOnItemClicked) {
        this.items = items;
        this.iOnItemClicked = iOnItemClicked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searched_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.cityName.setText(items.get(position).getName());
        holder.addClickListener(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
        }

        private void addClickListener(int position) {
            itemView.setOnClickListener(listener -> {
                if (iOnItemClicked != null && position > -1)
                    iOnItemClicked.onClick(items.get(position));
            });
        }
    }

    public interface IOnItemClicked {
        void onClick(Search item);
    }
}
