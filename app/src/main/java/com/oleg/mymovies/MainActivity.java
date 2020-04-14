package com.oleg.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.oleg.mymovies.data.FavoriteMovie;
import com.oleg.mymovies.data.MainViewModel;
import com.oleg.mymovies.data.Movie;
import com.oleg.mymovies.utils.JSONUtils;
import com.oleg.mymovies.utils.NetworkUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        //implements CompoundButton.OnCheckedChangeListener
{
    public NetworkUtils networkUtils;
    private RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    private Switch aSwitch;
    //public NetworkUtils.JSONLoadTask jsonLoadTask;
    List<Movie> movies=new ArrayList<>() ;
    TextView textViewPopularuty;
    TextView textViewTopRated;
    int metodOfSort=0;
    MainViewModel mainViewModel;
    LiveData<List<Movie>> listLiveData;

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
        setContentView(R.layout.activity_main);
        textViewPopularuty=findViewById(R.id.textViewPopularuty);
        textViewTopRated=findViewById(R.id.textViewTopRated);
        recyclerView=findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movieAdapter=new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        /*JSONObject jsonObject1Aver=NetworkUtils.getJSONFromNet(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page());
        JSONObject jsonObjectPopul=NetworkUtils.getJSONFromNet(NetworkUtils.POPULARITY,NetworkUtils.getNumber_of_page());
        String urlAver=NetworkUtils.bueldUrl(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page()).toString();
        String urlPopul=NetworkUtils.bueldUrl(NetworkUtils.POPULARITY,NetworkUtils.getNumber_of_page()).toString();
        Log.i("JSON_aver",jsonObject1Aver.toString());
        Log.i("JSON_popul",jsonObjectPopul.toString());
        Log.i("Url_aver",urlAver);
        Log.i("Url_popul",urlPopul);*/
        aSwitch=findViewById(R.id.switchSort);
        //if (aSwitch!=null)
        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chengeSort(isChecked);

            }
        });
        aSwitch.setChecked(false);
        listLiveData=mainViewModel.getMovies();
        listLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovieList(movies );
            }
        });
        movieAdapter.setOnMoviePosterClickListener(new MovieAdapter.OnMoviePosterClickListener() {
            @Override
            public void onShotClick(int pisition) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("MovieId",movieAdapter.getMovieList().get(pisition).getId());

                startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            //int nomberofpage=NetworkUtils.getNumber_of_page();
            @Override
            public void onRechEnd() {
                Toast.makeText(MainActivity.this, "End of page", Toast.LENGTH_SHORT).show();
                /*JSONObject jsonObject=NetworkUtils.getJSONFromNet(metodOfSort,NetworkUtils.getNumber_of_page());
                movies=JSONUtils.getMoviesFromJSON(jsonObject);
                movieAdapter.setMovieList(movies);
                movieAdapter.addMovies();*/
            }
        });
        /*JSONObject jsonObject=NetworkUtils.getJSONFromNet(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page());
        movies=JSONUtils.getMoviesFromJSON(jsonObject);*/
        /*for (Movie movie:movies){
            Log.i("title",movie.getSmallPosterPath().toString());
        }*/
        //Log.i("url",NetworkUtils.bueldUrl(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page()).toString());
        /*String string1;
        if(jsonObject!=null)
            string1="good";
        else string1="bed";
        */
    }

    public void sortPopul(View view) {
        aSwitch.setChecked(true);
    }

    public void sortAver(View view) {
        aSwitch.setChecked(false);
    }

    public void chengeSort(boolean isChenge){

        if (isChenge){
            metodOfSort=NetworkUtils.AVERAGE;
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularuty.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            metodOfSort=NetworkUtils.POPULARITY;
            textViewPopularuty.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        getDataToBD(metodOfSort,1);


    }

    public void getDataToBD(int metodOfSort,int page){
        JSONObject jsonObject=NetworkUtils.getJSONFromNet(metodOfSort,page);
        movies=JSONUtils.getMoviesFromJSON(jsonObject);
        if(movies!=null&&!movies.isEmpty()){
            mainViewModel.deleteAllMovies();
            for (Movie movie:movies
            ) {
                mainViewModel.insertMovies(movie);
            }
        }
    }


  /*  @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            JSONObject jsonObject=NetworkUtils.getJSONFromNet(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page());
            movies=JSONUtils.getMoviesFromJSON(jsonObject);
            movieAdapter.setMovieList(movies);
            recyclerView.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
        }
        else {
            JSONObject jsonObject=NetworkUtils.getJSONFromNet(NetworkUtils.POPULARITY,NetworkUtils.getNumber_of_page());
            movies=JSONUtils.getMoviesFromJSON(jsonObject);
            movieAdapter.setMovieList(movies);
            recyclerView.setAdapter(movieAdapter);
            movieAdapter.notifyDataSetChanged();
        }
    }*/

}
