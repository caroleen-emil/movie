package com.example.caroleenanwar.movie.activities;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.caroleenanwar.movie.R;
import com.example.caroleenanwar.movie.adapters.ResultAdapter;
import com.example.caroleenanwar.movie.api.APIClient;
import com.example.caroleenanwar.movie.api.APIInterface;
import com.example.caroleenanwar.movie.helper.PaginationScrollListener;
import com.example.caroleenanwar.movie.models.Movie;
import com.example.caroleenanwar.movie.models.MovieViewModel;
import com.example.caroleenanwar.movie.models.MoviesResult;
import com.example.caroleenanwar.movie.utils.PrefUtils;
import com.example.caroleenanwar.movie.utils.WebserviceUtil;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResutActivity extends AppCompatActivity {
    private MovieViewModel mMovieViewModel;
    private RecyclerView mMovieRv;
    private ResultAdapter mResultAdapter;
    private Context mContext;
    private LinearLayoutManager mLinearLayout;
    //data
    private String mSearch;
    private Boolean mIsLastPage = false;
    private Boolean mOnline;
    private MediatorLiveData<List<Movie>> mMovies;
    private Observer<List<Movie>> mObservable;
    private Boolean mPreviusOnline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resut);
        initView();
        mContext = SearchResutActivity.this;
        initize();
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        if (getIntent().getExtras() != null) {
            ArrayList<Movie> movies = getIntent().getParcelableArrayListExtra("result");
            mResultAdapter.add(movies);
        }

        if (!WebserviceUtil.isNetworkOnline(mContext)) {
            mOnline = false;
            mMovies = mMovieViewModel.getAllMovie(mSearch, mOnline, false);
            mObservable = new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable final List<Movie> words) {
                    // Update the cached copy of the words in the adapter.

                    if (words.size() > 0) {
                        mResultAdapter.add(words);
                    } else
                        mIsLastPage = true;

                }
            };
            if (mMovies != null && mObservable != null)
                mMovies.removeObserver(mObservable);
            mMovies.observe(SearchResutActivity.this, mObservable);
        }


        handleListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = SearchResutActivity.this;
    }

    private void initView() {
        mMovieRv = findViewById(R.id.resultRv);
    }

    private void initize() {
        mLinearLayout = new LinearLayoutManager(mContext);
        mMovieRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        mResultAdapter = new ResultAdapter(mContext, new ArrayList<Movie>());
        mMovieRv.setAdapter(mResultAdapter);
        // getMovies("the");
        if (getIntent().getExtras() != null) {
            mSearch = getIntent().getStringExtra("search");
            mPreviusOnline = getIntent().getBooleanExtra("online", false);
            ;
        }
    }

    private void handleListener() {
        mMovieRv.addOnScrollListener(new PaginationScrollListener(mLinearLayout) {
            @Override
            protected void loadMoreItems() {
                if (mPreviusOnline)
                    if (mContext != null) {

                        if (WebserviceUtil.isNetworkOnline(mContext)) {
                            mMovieViewModel.setmIsLoading(true);
                            mOnline = true;
                            if (mMovieViewModel.getmCurrentPage() < mMovieViewModel.getmTotalPage()) {
                                mMovieViewModel.setmCurrentPage(mMovieViewModel.getmCurrentPage() + 1);

                                mMovies = mMovieViewModel.getAllMovie(mSearch, mOnline, false);
                                mObservable = new Observer<List<Movie>>() {
                                    @Override
                                    public void onChanged(@Nullable final List<Movie> words) {
                                        // Update the cached copy of the words in the adapter.

                                        if (words.size() > 0) {
                                            mResultAdapter.add(words);
                                        } else
                                            mIsLastPage = true;
                                    }

                                };
                                if (mMovies != null && mObservable != null)
                                    mMovies.removeObserver(mObservable);
                                mMovies.observe(SearchResutActivity.this, mObservable);
                            }
                        }
                    }
            }

            @Override
            public int getTotalPageCount() {

                return mMovieViewModel.getmTotalPage();
            }

            @Override
            public boolean isLastPage() {

                return mIsLastPage;
            }

            @Override
            public boolean isLoading() {

                return mMovieViewModel.getmIsLoading();
            }
        });
    }

    APIInterface mApiInterface;

    private void getMovies(String movies) {
        //if (WebserviceUtil.isNetworkOnline(mContext)) {
        /**
         GET List of competition
         **/
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        Call<MoviesResult> call = mApiInterface.getMovies(1, movies, APIClient.getToken());
        call.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                if (response.isSuccessful()) {
                    MoviesResult competitionses = response.body();
                    if (competitionses != null) {
//                        mAllWords.setValue(competitionses.getmMovies());
//                        mCurrentPage=competitionses.getmPage();
//                        mTotalPage=competitionses.getmTotalPage();
                        mResultAdapter.add(competitionses.getmMovies());
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {

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

    }

    @Override
    protected void onPause() {
        super.onPause();
        mContext = null;
        if (mMovies != null && mObservable != null)
            mMovies.removeObserver(mObservable);
    }
}
