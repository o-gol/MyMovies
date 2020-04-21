package com.oleg.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies ")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM favoriteMovies ")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovies();

    @Query("SELECT * FROM movies WHERE id== :movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM favoriteMovies WHERE id== :movieId")
    FavoriteMovie getFavoriteMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deleteAllMovies();
    @Insert
    void insertMovies(List<Movie> movie);
    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertFavoriteMovies(FavoriteMovie favoriteMovies);
    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovies);


}
