package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView cities, locations;
    private SearchViewModel searchViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchField);
        cities = view.findViewById(R.id.cities);
        locations = view.findViewById(R.id.locations);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cities.setVisibility(View.GONE);
                locations.setVisibility(View.VISIBLE);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                cities.setVisibility(View.VISIBLE);
                locations.setVisibility(View.GONE);
                return true;
            }
        });

        searchViewModel.search(searchView);
    }
}
