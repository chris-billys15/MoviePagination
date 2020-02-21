package com.example.movielistapp.data.server;

import com.example.movielistapp.data.server.model.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/3/movie/top_rated?api_key=3aae48c328a9fab973b63b3a4f788f51")
    Call<Film.ListFilm> getTopRatedMovies(@Query("language") String lang, @Query("page") int page);
}
