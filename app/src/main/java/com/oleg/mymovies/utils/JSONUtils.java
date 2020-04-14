package com.oleg.mymovies.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.oleg.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    private static final String KEY_RESULTS = "results";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE= "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";



    public static List<Movie> getMoviesFromJSON(JSONObject jsonObject){
        List<Movie> movieArrayList=new ArrayList<>();
        Movie movie;
        if(jsonObject==null){
            return movieArrayList;
        }
        try {
            JSONArray result=jsonObject.getJSONArray(KEY_RESULTS);
            for(int i=0 ;i<result.length(); i++){
                JSONObject jsonObject1=result.getJSONObject(i);
                 movie=new Movie(jsonObject1.getInt(KEY_ID)
                        ,jsonObject1.getInt(KEY_VOTE_COUNT)
                        ,jsonObject1.getString(KEY_TITLE)
                        ,jsonObject1.getString(KEY_ORIGINAL_TITLE)
                        ,jsonObject1.getString(KEY_OVERVIEW)
                        ,jsonObject1.getString(KEY_POSTER_PATH)
                        ,jsonObject1.getString(KEY_BACKDROP_PATH)
                        ,jsonObject1.getDouble(KEY_VOTE_AVERAGE)
                        ,jsonObject1.getString(KEY_RELEASE_DATE));
                movieArrayList.add(movie);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
        return movieArrayList;

    }














}
