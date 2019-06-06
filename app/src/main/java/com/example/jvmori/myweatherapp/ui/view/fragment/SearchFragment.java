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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DaggerFragment {

    private RecyclerView cities, locations;
    private SearchViewModel searchViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(SearchViewModel.class);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchField);
        cities = view.findViewById(R.id.cities);
        locations = view.findViewById(R.id.locations);
        searchView.setOnCloseListener(() -> {
            cities.setVisibility(View.GONE);
            locations.setVisibility(View.VISIBLE);
            return false;
        });
        searchViewModel.search(searchView);
        searchViewModel.cities().observe(this, result -> showSugestions(result));
    }

    private void showSugestions(List<Search> searchList) {
        cities.setVisibility(View.VISIBLE);
        locations.setVisibility(View.GONE);
        createCitiesAdapter(searchList);
    }

    private void createCitiesAdapter(List<Search> searchList) {
        SearchResultsAdapter adapter = new SearchResultsAdapter();
        adapter.setSearchedResult(searchList);
        cities.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        cities.setAdapter(adapter);
    }
}
