package com.oleg.mymovies;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.oleg.mymovies.data.FavoriteMovie;
import com.oleg.mymovies.data.MainViewModel;
import com.oleg.mymovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    MainViewModel mainViewModel;

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
        setContentView(R.layout.activity_favourite);
        recyclerView=findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movieAdapter=new MovieAdapter();
        //movieAdapter.setMovieList( mainViewModel.getFavoriteMovies());
        recyclerView.setAdapter(movieAdapter);
        LiveData<List<FavoriteMovie>> favorListLiveData;
        favorListLiveData=mainViewModel.getFavoriteMovies();
        favorListLiveData.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                List<Movie> movies=new ArrayList<>();
                if(favoriteMovies!=null) {
                    movies.addAll(favoriteMovies);
                    movieAdapter.setMovieList(movies);
                }
            }
        });


        movieAdapter.setOnMoviePosterClickListener(new MovieAdapter.OnMoviePosterClickListener() {
            @Override
            public void onShotClick(int pisition) {
                Intent intent=new Intent(FavouriteActivity.this,DetailActivity.class);
                intent.putExtra("MovieId",movieAdapter.getMovieList().get(pisition).getId());

                startActivity(intent);
            }
        });

    }
}
