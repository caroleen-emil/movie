package com.example.caroleenanwar.movie.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.caroleenanwar.movie.R;
import com.example.caroleenanwar.movie.api.APIClient;
import com.example.caroleenanwar.movie.models.Movie;

public class ResultDetailActivity extends AppCompatActivity {
     private ImageView mPosterIv;
     private TextView mVoteTv;
     private TextView mPopularityTv;
     private TextView mOverviewTv;
     private TextView mDateTv;
     private TextView mAdultTv;
     private TextView mLangTv;
     //data
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        bindVariable();
        mContext=ResultDetailActivity.this;
        initialize();
    }
    private void bindVariable(){
        mPosterIv=findViewById(R.id.posterIV);

        mVoteTv=findViewById(R.id.voteTv);
        mPopularityTv=findViewById(R.id.popularityTv);
        mOverviewTv=findViewById(R.id.overviewTv);
        mDateTv=findViewById(R.id.dateTv);
        mAdultTv=findViewById(R.id.adultTv);
        mLangTv=findViewById(R.id.langTv);
    }
    private void initialize(){
        //set width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;

        mPosterIv.getLayoutParams().height = (int) (height * 0.3);
        Movie movie;
        if(getIntent().getExtras()!=null){
            movie=getIntent().getParcelableExtra("movie");
            String imageUrl= APIClient.getImageBase()+movie.getPosterPath();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher) .diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.error(R.mipmap.ic_launcher);
            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(imageUrl).transition(GenericTransitionOptions.with(R.anim.zoom_in)).into(mPosterIv);
            //setText
            mVoteTv.setText(movie.getVoteCount()+"");
            mPopularityTv.setText(movie.getPopularity()+"");
            mOverviewTv.setText(movie.getOverview());
            mDateTv.setText(movie.getReleaseDate());
            mLangTv.setText(movie.getOriginalLanguage());
            if(!movie.getAdult()){
                mAdultTv.setVisibility(View.GONE);
            }else
                mAdultTv.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(movie.getName());
        }
    }
}
