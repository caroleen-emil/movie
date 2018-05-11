package com.example.caroleenanwar.movie.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 09-May-18.
 */

public class MoviesResult {
    @SerializedName("page")
   private int mPage;
    @SerializedName("total_pages")
    private int mTotalPage;
    @SerializedName("results")
    private ArrayList<Movie> mMovies;

    public int getmPage() {
        return mPage;
    }

    public int getmTotalPage() {
        return mTotalPage;
    }

    public ArrayList<Movie> getmMovies() {
        return mMovies;
    }
}
