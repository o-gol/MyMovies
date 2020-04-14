package com.oleg.mymovies.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL="https://api.themoviedb.org/3/discover/movie";

    public static final int POPULARITY=0;
    public static final int AVERAGE=1;
    private static final String SORT_BY_POPULARITY="popularity.desc";
    private static final String SORT_BY_VOTE_AVERAGE="vote_average.desc";
    private static final String LANGUAGE ="ru-RU" ;
    //private  static  int number_of_page=1;
    private static final String API_KAY= "76c76aea109dee6336fefcae8e50fc2c";

    private static final String  PARAMS_API_KEY="api_key";
    private static final String  PARAMS_LANGUAGE="language";
    private static final String  PARAMS_SORT_BY="sort_by";
    private static final String  PARAMS_PAGE="page";

    /*public static int getNumber_of_page() {
        return number_of_page;
    }*/

    public static String sortBy(int sorting){
        switch (sorting) {
            case 0:
                return "popularity.desc";
            case 1:
                return "vote_count.asc";
            default:
                return "popularity.desc";

        }
    }

    public static URL bueldUrl(int sorting, int number_of_page){
        URL url=null;
        Uri uri=Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAMS_API_KEY,API_KAY)
                .appendQueryParameter(PARAMS_LANGUAGE,LANGUAGE)
                .appendQueryParameter(PARAMS_SORT_BY,sortBy(sorting))
                .appendQueryParameter(PARAMS_PAGE,Integer.toString(number_of_page))
                .build();
        try {
            return url= new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static JSONObject getJSONFromNet(int sorting, int number_of_page){
        URL url=bueldUrl(sorting,number_of_page);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
        return jsonObject;

    }

    private static class JSONLoadTask extends AsyncTask<URL,Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(URL... urls) {
            String line=null;
            StringBuilder stringBuilder=null;
            JSONObject jsonObject=null;
            if(urls==null ||urls.length==0){
                return null;
            }

            try {
                stringBuilder=new StringBuilder();
                URLConnection urlConnection=(HttpURLConnection)urls[0].openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(reader);
                line=bufferedReader.readLine();
                while(line  != null) {
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();

                }
                try {
                    jsonObject= new JSONObject(stringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }


    }




    public static class DowenloadSmallPosterTask extends AsyncTask<String,Void, Bitmap> {
        URLConnection urlConnection;
        URL url;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                Bitmap bitmap= BitmapFactory.decodeStream(in);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
            return null;



        }
    } public static class DowenloadSmallPosterURLTask extends AsyncTask<URL,Void, Bitmap> {
        URLConnection urlConnection=null;
        Bitmap bitmap=null;

        @Override
        protected Bitmap doInBackground(URL... urls) {
            try {

                urlConnection=(HttpURLConnection)urls[0].openConnection();
                InputStream in=urlConnection.getInputStream();
                bitmap= BitmapFactory.decodeStream(in);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
            return bitmap;



        }
    }






}
