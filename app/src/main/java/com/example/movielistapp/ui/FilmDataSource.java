package com.example.movielistapp.ui;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.movielistapp.data.DataManager;
import com.example.movielistapp.data.server.model.Film;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmDataSource extends PageKeyedDataSource<Integer, Film> {
    private static final int FIRST_PAGE = 1;
    private DataManager mDataManager;

    public FilmDataSource(){
        super();
        mDataManager = new DataManager();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Film> callback) {
        mDataManager.getTopRatedMovies("en-US",FIRST_PAGE)
                .enqueue(new Callback<Film.ListFilm>() {
                    @Override
                    public void onResponse(Call<Film.ListFilm> call, Response<Film.ListFilm> response) {
                        if (response.body() != null){
                            callback.onResult(response.body().movieList, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Film.ListFilm> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Film> callback) {
        mDataManager.getTopRatedMovies("en-US", params.key)
                .enqueue(new Callback<Film.ListFilm>() {
                    @Override
                    public void onResponse(Call<Film.ListFilm> call, Response<Film.ListFilm> response) {
                        if(response.body() != null){
                            Integer key = (params.key > 1)? params.key - 1: null;
                            callback.onResult(response.body().movieList, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Film.ListFilm> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Film> callback) {
        mDataManager.getTopRatedMovies("en-US", params.key)
                .enqueue(new Callback<Film.ListFilm>() {
                    @Override
                    public void onResponse(Call<Film.ListFilm> call, Response<Film.ListFilm> response) {
                        if(response.body() != null){
                            Integer key = response.body().page < response.body().totalPage ? params.key + 1 :null;
                            callback.onResult(response.body().movieList, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Film.ListFilm> call, Throwable t) {

                    }
                });
    }
}
