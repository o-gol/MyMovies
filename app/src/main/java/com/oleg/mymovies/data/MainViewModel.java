package com.oleg.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static MoviesDataBase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database= MoviesDataBase.getInstance(getApplication());
        movies=database.movieDao().getAllMovies();
        favoriteMovies=database.movieDao().getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }
    /*public MoviesDataBase getDatabase() {
        return database;
    }*/

    public Movie getMovieById(int numberId) {
        try {
            return new GetMovieTask().execute(numberId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavoriteMovie getFavoriteMovieById(int numberId) {
        try {
            return new GetFavoriteMovieTask().execute(numberId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllMovies() {

            new DeleteAllTask().execute();
    }

   public void deleteFavoriteMovie(FavoriteMovie movie) {

            new DeleteFavoriteMovieTask().execute(movie);

    }

   public void deleteMovie(Movie movie) {

            new DeleteMovieTask().execute(movie);

    }

    public void insertMovies(List<Movie> movie) {

            new InsertTask().execute(movie);
    }

    public void insertFavoriteMovie(FavoriteMovie movie) {

            new InsertFavoriteMovieTask().execute(movie);
    }




    private class InsertTask extends AsyncTask<List<Movie>,Void,Void> {
        @Override
        protected Void doInBackground(List<Movie>... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDao().insertMovies(movies[0]);
            }
            return null;


        }
    }

    private class InsertFavoriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDao().insertFavoriteMovies(movies[0]);
            }
            return null;


        }
    }

    private class DeleteAllTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private class DeleteMovieTask extends AsyncTask<Movie,Void,Void> {
        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;

        }
    }

      private class DeleteFavoriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteFavoriteMovie(movies[0]);
            }
            return null;

        }
    }

        private class GetMovieTask extends AsyncTask<Integer, Void, Movie> {
            @Override
            protected Movie doInBackground(Integer... integers) {
                if (integers != null && integers.length > 0) {
                    return database.movieDao().getMovieById(integers[0]);
                } else {
                    return null;

                }
            }
        }

        private class GetFavoriteMovieTask extends AsyncTask<Integer, Void, FavoriteMovie> {
            @Override
            protected FavoriteMovie doInBackground(Integer... integers) {
                if (integers != null && integers.length > 0) {
                    return database.movieDao().getFavoriteMovieById(integers[0]);
                } else {
                    return null;

                }
            }
        }
    }
