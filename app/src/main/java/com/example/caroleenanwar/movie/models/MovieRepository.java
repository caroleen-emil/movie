package com.example.caroleenanwar.movie.models;

import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.os.AsyncTask;
import android.widget.Button;

import com.example.caroleenanwar.movie.api.APIClient;
import com.example.caroleenanwar.movie.api.APIInterface;
import com.example.caroleenanwar.movie.utils.WebserviceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 09-May-18.
 */

public class MovieRepository {
    private MovieDao mWordDao;
    private LiveData<List<Movie>> mAllMovie;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();

    }

    LiveData<List<Movie>> getAllMovie(String movie) {
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
