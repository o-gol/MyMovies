package com.oleg.mymovies;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import com.oleg.mymovies.data.FavoriteMovie;
import com.oleg.mymovies.data.MainViewModel;
import com.oleg.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewDate;
    private TextView textViewOverviwe;
    private int movieId;
    private MainViewModel mainViewModel;
    Movie movie;
    boolean isFavorite;
    FavoriteMovie favoriteMovie;
    private ImageView imageViewRemoveFromFavorite;
    private ImageView imageViewAddToFavorite;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.itemFavorite:
                Intent intentToFavorite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster=findViewById(R.id.imageViewBigPoster);
        textViewTitle=findViewById(R.id.textViewTitle);
        textViewOriginalTitle=findViewById(R.id.textViewOriginalTitle);
        textViewRating=findViewById(R.id.textViewRating);
        textViewDate=findViewById(R.id.textViewDate);
        textViewOverviwe=findViewById(R.id.textViewOverview);
        imageViewRemoveFromFavorite=findViewById(R.id.imageViewRemoveFromFavorite);
        imageViewAddToFavorite=findViewById(R.id.imageViewAddToFavorite);

        Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra("MovieId")){
            movieId=intent.getIntExtra("MovieId",-1);

        }else
            finish();


        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        if(mainViewModel.getMovieById(movieId)==null){
            movie=mainViewModel.getFavoriteMovieById(movieId);
        }else {
            movie = mainViewModel.getMovieById(movieId);
        }
        //favoriteMovie= movie.toFavoriteMovie();
        favoriteMovie=mainViewModel.getFavoriteMovieById(movieId);
        isFavoriteInBD();
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        textViewDate.setText(movie.getReleaseDate());
        textViewOverviwe.setText(movie.getOverview());
        //favoriteMovie= movie.toFavoriteMovie();
        //Log.i("title",mainViewModel.getFavoriteMovieById(movieId).getTitle());



    }

    private void isFavoriteInBD(){
        if(favoriteMovie!=null) {
            isFavorite = true;
            imageViewAddToFavorite.setVisibility(View.INVISIBLE);
            imageViewRemoveFromFavorite.setVisibility(View.VISIBLE);
        }
        else {
            isFavorite = false;
            imageViewAddToFavorite.setVisibility(View.VISIBLE);
            imageViewRemoveFromFavorite.setVisibility(View.INVISIBLE);
        }
    }



    public void ChangeFavoriteMovie(View view) {
        favoriteMovie=mainViewModel.getFavoriteMovieById(movieId);
        if (isFavorite){
            mainViewModel.deleteFavoriteMovie(favoriteMovie);
            isFavorite = false;
            imageViewAddToFavorite.setVisibility(View.VISIBLE);
            imageViewRemoveFromFavorite.setVisibility(View.INVISIBLE);
            /*Intent intent=new Intent(this,FavouriteActivity.class);
            startActivity(intent);*/
        }else{
            mainViewModel.insertFavoriteMovie(movie.toFavoriteMovie());
            isFavorite = true;
            imageViewAddToFavorite.setVisibility(View.INVISIBLE);
            imageViewRemoveFromFavorite.setVisibility(View.VISIBLE);
            //FavoriteMovie fm=movie.toFavoriteMovie();

        }

        }

}
