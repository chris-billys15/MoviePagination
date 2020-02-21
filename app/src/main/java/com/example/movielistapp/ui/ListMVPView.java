package com.example.movielistapp.ui;

import com.example.movielistapp.data.server.model.Film;

import java.util.ArrayList;

public interface ListMVPView {

    void onSucceedGetResult(ArrayList<Film> movies);

    void viewToast(String message);
}
