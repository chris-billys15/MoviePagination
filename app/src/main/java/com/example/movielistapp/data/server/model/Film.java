package com.example.movielistapp.data.server.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Film {

    @SerializedName("id")
    public String filmID;
    @SerializedName("title")
    public String title;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("vote_average")
    public String voteAverage;
    @SerializedName("release_date")
    public String releaseDate;
    public class ListFilm{ //arrayList of Film
        @SerializedName("page")
        public int page;
        @SerializedName("total_results")
        public int totalResults;
        @SerializedName("total_pages")
        public int totalPage;
        @SerializedName("status_message")
        public String statusMessage;
        @SerializedName("success")
        public boolean success;
        @SerializedName("results")
        public ArrayList<Film> movieList = new ArrayList<>();
    }
}