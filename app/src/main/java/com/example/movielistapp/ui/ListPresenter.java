package com.example.movielistapp.ui;

import com.example.movielistapp.data.DataManager;
import com.example.movielistapp.data.server.model.Film;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter {

    ListMVPView mMvpView; //connect MVPView dengan Presenter
    DataManager mDataManager;
    int mCurrentPage;

    public ListPresenter(ListMVPView listMVPView) {
        this.mMvpView= listMVPView;
        mDataManager = new DataManager();
        mCurrentPage = 0;
    }

    public void getMovies(String lang){
        mCurrentPage++;
        mDataManager.getTopRatedMovies(lang, mCurrentPage).enqueue(new Callback<Film.ListFilm>() {
            @Override
            public void onResponse(Call<Film.ListFilm> call, Response<Film.ListFilm> response) {
                if(response.code()==200 && response.body()!= null){
                    mCurrentPage++;
                    ArrayList<Film> movies = response.body().movieList;
                    mMvpView.onSucceedGetResult(movies,mCurrentPage-1); //setup RecyclerView
                }
                else{
                    mCurrentPage--;
                    mMvpView.viewToast("response null");
                }
            }

            @Override
            public void onFailure(Call<Film.ListFilm> call, Throwable t) {
                mMvpView.viewToast("error"); //show Toast
                t.printStackTrace();
            }
        });
    }
}
