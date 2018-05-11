package com.example.caroleenanwar.movie.api;





import com.example.caroleenanwar.movie.models.MoviesResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("search/movie")
    Call<MoviesResult> getMovies(@Query("page") int page,@Query("query") String query,@Query("api_key") String key);

}