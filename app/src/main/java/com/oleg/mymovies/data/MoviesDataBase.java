package com.oleg.mymovies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class, FavoriteMovie.class},version = 2,exportSchema = false)
public abstract class MoviesDataBase extends RoomDatabase {

    private static MoviesDataBase dataBase;
    private static final String DB_NAME="movies.db";
    private static final Object LOCK=new Object();

    public static MoviesDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, MoviesDataBase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return dataBase;

    }

    public abstract MovieDao movieDao();



}
