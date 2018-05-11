package com.example.caroleenanwar.movie.models;

/**
 * Created by user on 09-May-18.
 */

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao wordDao();

    private static MovieRoomDatabase INSTANCE;


    static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }




}