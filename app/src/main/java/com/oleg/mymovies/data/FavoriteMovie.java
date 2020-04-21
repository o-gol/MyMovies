package com.oleg.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favoriteMovies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int myDbId, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(myDbId, id, voteCount, title, originalTitle, overview, posterPath, backdropPath, voteAverage, releaseDate);
    }

    @Ignore
    public FavoriteMovie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(id, voteCount, title, originalTitle, overview, posterPath, backdropPath, voteAverage, releaseDate);

    }




}
