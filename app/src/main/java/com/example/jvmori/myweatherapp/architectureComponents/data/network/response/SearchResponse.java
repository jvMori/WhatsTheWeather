package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import java.util.List;

public class SearchResponse
{
    private List<Search> results;

    public List<Search> getResults() {
        return results;
    }

    public void setResults(List<Search> results) {
        this.results = results;
    }
}
