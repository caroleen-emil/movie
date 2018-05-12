package com.example.caroleenanwar.movie.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.os.AsyncTask;

import com.example.caroleenanwar.movie.models.Movie;
import com.example.caroleenanwar.movie.db.MovieDao;
import com.example.caroleenanwar.movie.db.MovieRoomDatabase;

import java.util.List;

/**
 * Created by user on 09-May-18.
 */

public class MovieRepository {
    private MovieDao mWordDao;
    private LiveData<List<Movie>> mAllMovie;

    public MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();

    }

    public LiveData<List<Movie>> getAllMovie(String movie,Boolean firstRows) {
        if(firstRows){
            mAllMovie=  mWordDao.getAllFirstMovie('%' + movie + '%');
        }else
        mAllMovie = mWordDao.getAllMovie('%' + movie + '%');
//int size=mWordDao.getAllMovie().getValue().size();
        return mAllMovie;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Movie word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            int count = mAsyncTaskDao.getAllCount(params[0].getId());
            if (count == 0)
                mAsyncTaskDao.insert(params[0]);

            return null;
        }
    }
}
