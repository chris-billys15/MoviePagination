package com.example.movielistapp.ui;


import android.util.Log;

import com.example.movielistapp.data.DataManager;
import com.example.movielistapp.data.server.model.Film;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPresenter {

    ListMVPView mMvpView; //connect MVPView dengan Presenter
    DataManager mDataManager;

    public ListPresenter(ListMVPView listMVPView) {
        this.mMvpView= listMVPView;
        mDataManager = new DataManager();
    }

    public void getMovies(String lang, int page){
        mDataManager.getTopRatedMovies(lang,page).enqueue(new Callback<Film.ListFilm>() {
            @Override
            public void onResponse(Call<Film.ListFilm> call, Response<Film.ListFilm> response) {
                Log.d("ResponseBilly == ", response.toString());
                if(response.code()==200 && response.body()!= null){
                    ArrayList<Film> movies = response.body().movieList;
                    Log.d("ResponseBilly == ", "Masuk1");
                    mMvpView.onSucceedGetResult(movies); //setup RecyclerView

//                    if(response.body().success){ //not success
//                        mMvpView.viewToast(response.body().statusMessage); //show Toast
//                        Log.d("ResponseBilly == ", "Masuk2");
//                    }
//                    else{ //success
//                        Log.d("ResponseBilly == ", "Masuk3");
//                    }
                }
                else{
                    mMvpView.viewToast("response null");
                }
            }

            @Override
            public void onFailure(Call<Film.ListFilm> call, Throwable t) {
                Log.e("onFailureBil", call.toString());
                mMvpView.viewToast("error"); //show Toast
                t.printStackTrace();
            }
        });
    }
}
