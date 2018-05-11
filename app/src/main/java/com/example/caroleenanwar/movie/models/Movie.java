package com.example.caroleenanwar.movie.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 09-May-18.
 */
@Entity(tableName = "movie_table")
public class Movie implements Parcelable {
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String name;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;
    @PrimaryKey(autoGenerate = true)


    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String  originalLanguage;
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private Boolean adult;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String  overview;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String  backDropPath;
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;
    //gettter
    public String getPosterPath() {
        return posterPath;
    }

    public String getName() {
        return name;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }
    public Movie(double popularity) {
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        posterPath = in.readString();
        name = in.readString();
        voteAverage = in.readDouble();
        releaseDate= in.readString();
        id=in.readInt();
        voteCount=in.readInt();
        originalLanguage= in.readString();
        adult = in.readByte() != 0;
        overview= in.readString();
        backDropPath= in.readString();
        popularity= in.readDouble();
    }




    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(posterPath);
        dest.writeString(name);
        dest.writeDouble(voteAverage);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeInt(voteCount);
        dest.writeString(originalLanguage);
        dest.writeByte((byte) ( adult? 1 : 0));
        dest.writeString(overview);
        dest.writeString(backDropPath);
        dest.writeDouble(popularity);
    }
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
