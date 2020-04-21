package com.oleg.mymovies;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.oleg.mymovies.adapters.ReviewAdapter;
import com.oleg.mymovies.adapters.TrailerAdapter;
import com.oleg.mymovies.data.*;
import com.oleg.mymovies.utils.JSONUtils;
import com.oleg.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewDate;
    private TextView textViewOverviwe;
    private RecyclerView recyclerViewTrailer;
    private RecyclerView recyclerViewReviwe;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private int movieId;
    private List<Trailer> trailerList;
    private List<Review> reviewList;
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
        recyclerViewReviwe=findViewById(R.id.recyclerViewReviwe);
        recyclerViewTrailer=findViewById(R.id.recyclerViewTralers);
        recyclerViewReviwe.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter=new TrailerAdapter();
        reviewAdapter=new ReviewAdapter();
        recyclerViewTrailer.setAdapter(trailerAdapter);
        recyclerViewReviwe.setAdapter(reviewAdapter);




        final Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra("MovieId")){
            movieId=intent.getIntExtra("MovieId",-1);

        }else
            finish();


        //--------------------------------------------------------Загрузка RecyclerView для Trailers, Reviews
        reviewList=getReviews();
        trailerList=getTrailers();
       /* for (Review review:
             reviewList) {
            Log.i("revie",review.getAuthor());
            Log.i("revie",review.getContent());
        }
        for (Trailer trailer:
             trailerList) {
            Log.i("revie",trailer.getUrl().toString());
            Log.i("revie",trailer.getName());
        }*/
        trailerAdapter.setTrailerList(trailerList);
        trailerAdapter.setOnImageViewVideoClickListener(new TrailerAdapter.ClickOnImageViewVideoListener() {
            @Override
            public void onShotClick(URL url) {

                Intent intent1=new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
                startActivity(intent1);


            }
        });
        reviewAdapter.setReviewList(reviewList);
        //--------------------------------------------------------Конец


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

    private List<Trailer> getTrailers(){

        return JSONUtils.getTreilersOfMovie(NetworkUtils.getJSONVideosMoviesFromNet(movieId));
    }

    private List<Review> getReviews(){
        return JSONUtils.getReviwOfMovie(NetworkUtils.getJSONReviewsFromNet(movieId,1));
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
