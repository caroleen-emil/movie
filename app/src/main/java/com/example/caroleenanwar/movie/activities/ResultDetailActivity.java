package com.example.caroleenanwar.movie.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.caroleenanwar.movie.R;
import com.example.caroleenanwar.movie.api.APIClient;
import com.example.caroleenanwar.movie.models.Movie;
import com.example.caroleenanwar.movie.models.MovieViewModel;

import es.dmoral.toasty.Toasty;

public class ResultDetailActivity extends AppCompatActivity {
    private ImageView mPosterIv;
    private TextView mVoteTv;
    private TextView mVoteAverageTv;
    private TextView mPopularityTv;
    private TextView mOverviewTv;
    private TextView mDateTv;
    private TextView mAdultTv;
    private TextView mLangTv;
    private Button saveBtn;
    //data
    private Context mContext;
    private MovieViewModel mMovieViewModel;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        bindVariable();
        mContext = ResultDetailActivity.this;
        initialize();
        handleListener();
    }

    private void bindVariable() {
        mPosterIv = findViewById(R.id.posterIV);

        mVoteTv = findViewById(R.id.voteTv);
        mVoteAverageTv= findViewById(R.id.voteAverageTv);
        mPopularityTv = findViewById(R.id.popularityTv);
        mOverviewTv = findViewById(R.id.overviewTv);
        mDateTv = findViewById(R.id.dateTv);
        mAdultTv = findViewById(R.id.adultTv);
        mLangTv = findViewById(R.id.langTv);
        saveBtn=findViewById(R.id.saveBtn);
    }

    private void initialize() {
        //set width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;

        mPosterIv.getLayoutParams().height = (int) (height * 0.3);

        if (getIntent().getExtras() != null) {
            movie = getIntent().getParcelableExtra("movie");
            String imageUrl = APIClient.getImageBase() + movie.getPosterPath();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.movie).diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.error(R.mipmap.ic_launcher);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(imageUrl).transition(GenericTransitionOptions.with(R.anim.zoom_in)).into(mPosterIv);
            //setText
            mVoteTv.setText(movie.getVoteCount() + "");
            mVoteAverageTv.setText(movie.getVoteAverage()+"");
            mPopularityTv.setText(movie.getPopularity() + "");
            mOverviewTv.setText(movie.getOverview());
            mDateTv.setText(movie.getReleaseDate());
            mLangTv.setText(movie.getOriginalLanguage());
            if (!movie.getAdult()) {
                mAdultTv.setVisibility(View.GONE);
            } else
                mAdultTv.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(movie.getName());
        }
        mMovieViewModel = ViewModelProviders.of(ResultDetailActivity.this).get(MovieViewModel.class);
    }
    private void handleListener(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMovieViewModel.insert(movie);
                Toasty.success(mContext, "Sucessfully save", Toast.LENGTH_SHORT, true).show();

            }
        });
    }
}
