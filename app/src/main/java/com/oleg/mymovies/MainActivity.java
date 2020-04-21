package com.oleg.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.oleg.mymovies.adapters.MovieAdapter;
import com.oleg.mymovies.data.MainViewModel;
import com.oleg.mymovies.data.Movie;
import com.oleg.mymovies.utils.JSONUtils;
import com.oleg.mymovies.utils.NetworkUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject>
        //implements CompoundButton.OnCheckedChangeListener
{
    ProgressBar progressBarLoading;
    public NetworkUtils networkUtils;
    private RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    private Switch aSwitch;
    //public NetworkUtils.JSONLoadTask jsonLoadTask;
    List<Movie> movies;
    TextView textViewPopularuty;
    TextView textViewTopRated;
    int metodOfSort;
    MainViewModel mainViewModel;
    LiveData<List<Movie>> listLiveData;
    private static final int LOADER_ID=1;
    private LoaderManager loaderManager;
    private static int page;
    private static boolean isLoading=false;

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

    private int getColumnCount(){
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width =(int)(displayMetrics.widthPixels / displayMetrics.density);
        return width/185>2?width/185:2;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager=LoaderManager.getInstance(this);
        progressBarLoading=findViewById(R.id.progressbarLoading);
        textViewPopularuty=findViewById(R.id.textViewPopularuty);
        textViewTopRated=findViewById(R.id.textViewTopRated);
        movies=new ArrayList<>() ;
        recyclerView=findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movieAdapter=new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        page=1;
        /*JSONObject jsonObject1Aver=NetworkUtils.getJSONFromNet(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page());
        JSONObject jsonObjectPopul=NetworkUtils.getJSONFromNet(NetworkUtils.POPULARITY,NetworkUtils.getNumber_of_page());
        String urlAver=NetworkUtils.bueldUrl(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page()).toString();
        String urlPopul=NetworkUtils.bueldUrl(NetworkUtils.POPULARITY,NetworkUtils.getNumber_of_page()).toString();
        Log.i("JSON_aver",jsonObject1Aver.toString());
        Log.i("JSON_popul",jsonObjectPopul.toString());
        Log.i("Url_aver",urlAver);
        Log.i("Url_popul",urlPopul);*/
        aSwitch=findViewById(R.id.switchSort);
        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page=1;
                chengeSort(isChecked);

            }
        });
        aSwitch.setChecked(false);
        listLiveData=mainViewModel.getMovies();
        listLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if(page==1)
                movieAdapter.setMovieList(movies );
            }
        });
        movieAdapter.setOnMoviePosterClickListener(new MovieAdapter.OnMoviePosterClickListener() {
            @Override
            public void onShotClick(int pisition) {
                /*Log.i("reviews",NetworkUtils.buildUrlReviews(movieAdapter.getMovieList().get(pisition).getId(),1).toString());
                Log.i("reviews",NetworkUtils.getJSONReviewsFromNet(movieAdapter.getMovieList().get(pisition).getId(),1).toString());
                Log.i("reviews",NetworkUtils.buildUrlVideosMovies(movieAdapter.getMovieList().get(pisition).getId()).toString());
                Log.i("reviews",NetworkUtils.getJSONVideosMoviesFromNet(movieAdapter.getMovieList().get(pisition).getId()).toString());*/

                /*List<URL> urlTreilerOfMovie=JSONUtils.getURLTreilerOfMovie(NetworkUtils.getJSONVideosMoviesFromNet(movieAdapter.getMovieList().get(pisition).getId()));
                for (URL url:urlTreilerOfMovie) {


                    Log.i("reviews", url.toString());
                }*/
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                //intent.putExtra("MovieId",movieAdapter.getMovieList().get(pisition).getId());
                intent.putExtra("MovieId",movieAdapter.getMovieList().get(pisition).getId());

                startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            //int nomberofpage=NetworkUtils.getNumber_of_page();
            @Override
            public void onRechEnd() {
                Log.i("revi","onrechend");
                if(!isLoading) {
                    //Toast.makeText(MainActivity.this, "End of page", Toast.LENGTH_SHORT).show();
                    getDataToBD(metodOfSort,page);
                }

                /*JSONObject jsonObject=NetworkUtils.getJSONFromNet(metodOfSort,NetworkUtils.getNumber_of_page());
                movies=JSONUtils.getMoviesFromJSON(jsonObject);
                movieAdapter.setMovieList(movies);
                movieAdapter.addMovies();*/
            }
        });
        /*JSONObject jsonObject=NetworkUtils.getJSONFromNet(NetworkUtils.AVERAGE,NetworkUtils.getNumber_of_page());
        movies=JSONUtils.getMoviesFromJSON(jsonObject);
        for (Movie movie:movies){
            Log.i("title",movie.getSmallPosterPath().toString());
        }
        String string1;
        if(jsonObject!=null)
            string1="good";
        else string1="bed";
*/    }

    public void sortPopul(View view) {
        aSwitch.setChecked(true);
    }

    public void sortAver(View view) {
        aSwitch.setChecked(false);
    }

    public void chengeSort(boolean isChenge){

        if (isChenge){
            metodOfSort=NetworkUtils.AVERAGE;
            //mainViewModel.deleteAllMovies();
            //movieAdapter.clearAdapter();
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopularuty.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            metodOfSort=NetworkUtils.POPULARITY;
            //mainViewModel.deleteAllMovies();
            //movieAdapter.clearAdapter();
            textViewPopularuty.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        getDataToBD(metodOfSort,page);


    }
    public void getDataToBD(int metodOfSort,int page){
        URL url=NetworkUtils.bueldUrl(metodOfSort,page);
        Bundle bundle=new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID,bundle,this);


    }


    /*   public void getDataToBD(int metodOfSort,int page){
        JSONObject jsonObject=NetworkUtils.getJSONFromNet(metodOfSort,page);
        movies=JSONUtils.getMoviesFromJSON(jsonObject);
        if(movies!=null&&!movies.isEmpty()){
            mainViewModel.deleteAllMovies();
            for (Movie movie:movies
            ) {
                mainViewModel.insertMovies(movie);
            }
        }
    }*/

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this,args);
        jsonLoader.setIsLoadListener(new NetworkUtils.JSONLoader.IsLoadListener() {
            @Override
            public void isLoad() {
                isLoading=true;
                progressBarLoading.setVisibility(View.VISIBLE);
            }
        });
        return jsonLoader;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        JSONObject jsonObject=NetworkUtils.getJSONFromNet(metodOfSort,page);
        List<Movie> movies=JSONUtils.getMoviesFromJSON(jsonObject);
        //Log.i("reviw",movies.get(0).getTitle());
        if(movies!=null&&!movies.isEmpty()){
            if(page==1) {
                mainViewModel.deleteAllMovies();
                movieAdapter.clearAdapter();
            }
                mainViewModel.insertMovies(movies);
                Log.i("reviw", movies.get(0).getTitle());
                //movies=mainViewModel.getMovies().getValue();
                movieAdapter.addMovies(movies);
                //movieAdapter.setMovieList(movies);
                page++;
            }



        loaderManager.destroyLoader(LOADER_ID);
        progressBarLoading.setVisibility(View.INVISIBLE);
        isLoading=false;

    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

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
