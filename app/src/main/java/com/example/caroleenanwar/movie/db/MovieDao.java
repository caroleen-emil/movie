package com.example.caroleenanwar.movie.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.caroleenanwar.movie.models.Movie;

import java.util.List;

/**
 * Created by user on 09-May-18.
 */
@Dao
public interface MovieDao {
    @Insert
    void insert(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAll();


  @Query("SELECT * from movie_table WHERE title LIKE :titleInput ORDER BY release_date DESC  limit 10  ")
    LiveData<List<Movie>> getAllFirstMovie(String titleInput);
    @Query("SELECT * from movie_table WHERE title LIKE :titleInput ORDER BY release_date DESC  limit -1 OFFSET 10 ")
    LiveData<List<Movie>> getAllMovie(String titleInput);
    @Query("SELECT COUNT( *) from movie_table WHERE id=:idInput ORDER BY release_date ASC")
    int getAllCount(int idInput);
}
