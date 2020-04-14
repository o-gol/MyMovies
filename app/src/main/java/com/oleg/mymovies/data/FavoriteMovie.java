package com.oleg.mymovies.data;

import androidx.room.Entity;

@Entity(tableName = "favoriteMovies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(id, voteCount, title, originalTitle, overview, posterPath, backdropPath, voteAverage, releaseDate);
    }




}
