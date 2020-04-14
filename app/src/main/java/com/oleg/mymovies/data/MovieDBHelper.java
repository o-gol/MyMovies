package com.oleg.mymovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieDBHelper extends SQLiteOpenHelper {
    public String commandCreat=MovieContracts.MoviesEntry.COMMAND_CREATE;
    private static final String DB_NAME="movies.db";
    private static final int DB_VERSION=0;

    public MovieDBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieContracts.MoviesEntry.COMMAND_CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieContracts.MoviesEntry.COMMAND_DROP);

    }
}
