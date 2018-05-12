package com.example.caroleenanwar.movie.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.*;
import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.caroleenanwar.movie.R;
import com.example.caroleenanwar.movie.activities.ResultDetailActivity;
import com.example.caroleenanwar.movie.api.APIClient;

import com.example.caroleenanwar.movie.models.Movie;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.CustomViewHolder> {


    private final Context mContext;
    private final ArrayList<Movie> Movies;

    // 1
    public ResultAdapter(Context context, ArrayList<Movie> categories) {
        this.mContext = context;
        this.Movies = categories;

    }

    public void add(List<Movie> categoryList) {
        Movies.addAll(categoryList);
        notifyDataSetChanged();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.item_result, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        final Movie movie = Movies.get(i);

        //set width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        customViewHolder.mTeamImageView.getLayoutParams().width = (int) (width * 0.25);
        customViewHolder.mTeamImageView.getLayoutParams().height = (int) (width * 0.25);
        String imageUrl = APIClient.getImageBase() + movie.getPosterPath();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(width, width).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(imageUrl).into(customViewHolder.mTeamImageView);


        //set Text
        customViewHolder.mTeamTv.setText(movie.getName());
        customViewHolder.mPointTv.setText(movie.getVoteAverage() + "");
        customViewHolder.mPlayedGame.setText(movie.getReleaseDate() + "");
        customViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ResultDetailActivity.class);
                intent.putExtra("movie", Movies.get(i));
                ((Activity) mContext).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return (null != Movies ? Movies.size() : 0);
    }


    // View lookup cache
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView mTeamImageView;
        private TextView mTeamTv;
        private TextView mPlayedGame;
        private TextView mPointTv;
        private CardView mCardView;

        public CustomViewHolder(View view) {
            super(view);
            //bind variable
            mTeamImageView = view.findViewById(R.id.teamIv);
            mTeamTv = view.findViewById(R.id.teamNameTv);

            mPointTv = (TextView) view.findViewById(R.id.pointTv);
            mPlayedGame = view.findViewById(R.id.playedTv);
            mCardView = view.findViewById(R.id.cardview);
        }
    }
}

