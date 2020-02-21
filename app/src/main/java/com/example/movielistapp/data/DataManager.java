package com.example.movielistapp.data;

import com.example.movielistapp.data.server.ApiInterface;
import com.example.movielistapp.data.server.model.Film;

import retrofit2.Call;

public class DataManager{
    public ApiInterface apiInterface;
    public DataManager(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public Call<Film.ListFilm> getTopRatedMovies(String lang, int page) {
        return apiInterface.getTopRatedMovies(lang, page);
    }
}
