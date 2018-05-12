package com.example.caroleenanwar.movie.activities;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caroleenanwar.movie.R;
import com.example.caroleenanwar.movie.models.Movie;
import com.example.caroleenanwar.movie.models.MovieViewModel;
import com.example.caroleenanwar.movie.utils.PrefUtils;
import com.example.caroleenanwar.movie.utils.WebserviceUtil;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressBaseDialog;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {
    private EditText searchET;
    private ACProgressBaseDialog progressDialog;
    //data
    private Context mContext;
    private Boolean mOnline;
    private MovieViewModel mMovieViewModel;
    private MediatorLiveData<List<Movie>> mMovies;
    private Observer<List<Movie>> mObservable;
    private Boolean observe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = SearchActivity.this;
        bindView();
        if (mMovieViewModel == null) {
            mMovieViewModel = ViewModelProviders.of(SearchActivity.this).get(MovieViewModel.class);
        }
        handlistener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = SearchActivity.this;
    }

    private void bindView() {
        searchET = findViewById(R.id.searchET);
    }

    private void handlistener() {
        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    hideKeyBoard(SearchActivity.this);
                    final String search = searchET.getText().toString().trim();
                    if (search.length() > 0) {

                        mMovieViewModel.setmCurrentPage(1);
                        mOnline = false;
                        if (WebserviceUtil.isNetworkOnline(mContext)) {
                            mOnline = true;
                            showDialog();
                        }
                        //check observe already register
                        if (mMovies != null && mObservable != null)
                            observe = true;
                        mMovies = mMovieViewModel.getAllMovie(search, mOnline, true);

                        if (mObservable == null) {
                            mObservable = new Observer<List<Movie>>() {
                                @Override
                                public void onChanged(@Nullable final List<Movie> words) {
                                    // Update the cached copy of the words in the adapter.
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                    if (mContext != null) {
                                        if (words.size() > 0) {
                                            Boolean online = false;
                                            if (WebserviceUtil.isNetworkOnline(mContext)) {
                                                online = true;
                                            }
                                            Intent intent = new Intent(mContext, SearchResutActivity.class);
                                            intent.putExtra("search", search);
                                            intent.putExtra("online", online);
                                            try {
                                                intent.putParcelableArrayListExtra("result", (ArrayList) words);
                                                startActivity(intent);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }


                                        } else
                                            Toasty.info(mContext, "No Result found or check network", Toast.LENGTH_SHORT, true).show();
                                    }

                                }
                            };
                        }

                        //  if (observe == false||mOnline)
                        mMovies.observe(SearchActivity.this, mObservable);

                    }
                }
                return false;
            }
        });
    }

    public void hideKeyBoard(AppCompatActivity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDialog() {
        progressDialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mContext = null;
//        if(mMovies!=null &&mObservable!=null )
//        mMovies.removeObserver(mObservable);
    }
}
