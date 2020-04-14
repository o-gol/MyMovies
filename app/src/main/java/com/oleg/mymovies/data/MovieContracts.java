package com.oleg.mymovies.data;

import android.provider.BaseColumns;

public class MovieContracts {

    public static final class MoviesEntry implements BaseColumns {
        /*private int id;
        private int voteCount;
        private String title;
        private String originalTitle;
        private String overview;
        private String posterPath;
        private String backdropPath;
        private double voteAverage;
        private String releaseDate;*/




        public static final String TABLE_NAME = "movies";
        public static final String _ID = "id";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_BACK_DROP_PATH = "backdropPath";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_RELAESE_DATE = "releaseDate";
        //public static final String _ID="id";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        //public static final String COMMAND_CREATE="CREATE TABLE IF NOT EXIST"+TABLE_NAME+"("+;

        public static final String COMMAND_CREATE = String.format("CREATE TABLE IF NOT EXISTS %s (%s %s PRIMARY KEY , %s %s, %s %s, %s %s, %s %s, %s %s, %s %s, %s %s, %s %s)", TABLE_NAME,
                _ID, TYPE_INTEGER,
                COLUMN_VOTE_COUNT,TYPE_INTEGER,
                COLUMN_TITLE, TYPE_TEXT,
                COLUMN_ORIGINAL_TITLE, TYPE_TEXT,
                COLUMN_OVERVIEW, TYPE_TEXT,
                COLUMN_POSTER_PATH, TYPE_TEXT,
                COLUMN_BACK_DROP_PATH, TYPE_TEXT,
                COLUMN_VOTE_AVERAGE , TYPE_TEXT,
                COLUMN_RELAESE_DATE , TYPE_TEXT
        );
        public static final String COMMAND_DROP = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }
}