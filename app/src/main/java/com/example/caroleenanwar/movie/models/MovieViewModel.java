package com.example.caroleenanwar.movie.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.caroleenanwar.movie.api.APIClient;
import com.example.caroleenanwar.movie.api.APIInterface;
import com.example.caroleenanwar.movie.repo.MovieRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 09-May-18.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private MediatorLiveData<List<Movie>> mAllWords;
    APIInterface mApiInterface;
    private int mCurrentPage = 1;
    private int mTotalPage = 2;
    private Boolean mIsLoading = false;

    public MovieViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        // mAllWords = mRepository.getAllMovie();
    }

    public MediatorLiveData<List<Movie>> getAllMovie(String movie, Boolean online,Boolean firstRows) {
        if (online) {
            mAllWords = getMovies(movie);

        } else {
            mAllWords = new MediatorLiveData<List<Movie>>();
            mAllWords = getAllMovies(movie,firstRows);

        }
        return mAllWords;
    }

    private MediatorLiveData<List<Movie>> mSectionLive = new MediatorLiveData<>();

    public MediatorLiveData<List<Movie>> getAllMovies(String movie,Boolean firstRows) {

        final LiveData<List<Movie>> sections = mRepository.getAllMovie(movie,firstRows);

        mSectionLive.addSource(sections, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> sectionList) {

                mSectionLive.removeSource(sections);
                mSectionLive.setValue(sectionList);

            }
        });
        return mSectionLive;
    }

    private MediatorLiveData<List<Movie>> getMovies(String movies) {
        //if (WebserviceUtil.isNetworkOnline(mContext)) {
        /**
         GET List of competition
         **/
        mAllWords = new MediatorLiveData<List<Movie>>();
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MoviesResult> call = mApiInterface.getMovies(mCurrentPage, movies, APIClient.getToken());
        call.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                if (response.isSuccessful()) {
                    MoviesResult competitionses = response.body();
                    if (competitionses != null) {
                        mAllWords.setValue(competitionses.getmMovies());
                        mIsLoading = false;
                        mCurrentPage = competitionses.getmPage();
                        mTotalPage = competitionses.getmTotalPage();


                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                mIsLoading = false;
                call.cancel();
            }
        });
//        } else {
//            final Dialog dialog = WebserviceUtil.showNewDialog(mContext);
//            Button yes = ( Button)dialog.findViewById(R.id.yes);
//
//            yes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                    getAllMovie( movie);
//                }
//            });
//        }
        return mAllWords;
    }

    public void insert(Movie word) {
        mRepository.insert(word);
    }

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    public int getmTotalPage() {
        return mTotalPage;
    }

    public Boolean getmIsLoading() {
        return mIsLoading;
    }

    public void setmCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    public void setmIsLoading(Boolean mIsLoading) {
        this.mIsLoading = mIsLoading;
    }

}