package com.example.jvmori.myweatherapp.architectureComponents.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse
{
    @SerializedName("")
    private List<Search> results;

    public List<Search> getResults() {
        return results;
    }

    public void setResults(List<Search> results) {
        this.results = results;
    }
}
