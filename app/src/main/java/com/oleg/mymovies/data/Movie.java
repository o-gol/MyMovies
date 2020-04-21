package com.oleg.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.oleg.mymovies.utils.JSONUtils;

import java.net.MalformedURLException;
import java.net.URL;

@Entity(tableName = "movies")
public class  Movie {
    @PrimaryKey(autoGenerate = true)
    private int myDbId;
    private int id;
    private int voteCount;
    private String title;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private double voteAverage;
    private String releaseDate;


    public Movie(int myDbId, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        this.myDbId = myDbId;
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String backdropPath, double voteAverage, String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public void setMyDbId(int myDbId) {
        this.myDbId = myDbId;
    }

    public int getMyDbId() {
        return myDbId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String string= JSONUtils.BASE_POSTER_URL+JSONUtils.SMALL_POSTER_SIZE+posterPath;

    public String getSmallPosterPath(){
        return JSONUtils.BASE_POSTER_URL+JSONUtils.SMALL_POSTER_SIZE+this.posterPath;

    }

    public String getBigPosterPath(){
        return JSONUtils.BASE_POSTER_URL+JSONUtils.BIG_POSTER_SIZE+this.posterPath;

    }

    public URL getSmallPosterUrl(){
        URL url=null;
        try {
            url=new URL(JSONUtils.BASE_POSTER_URL+JSONUtils.SMALL_POSTER_SIZE+this.posterPath);
            return  url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public URL getBigPosterUrl(){
        URL url=null;
        try {
            url=new URL(JSONUtils.BASE_POSTER_URL+JSONUtils.BIG_POSTER_SIZE+this.posterPath);
            return  url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }



    public FavoriteMovie toFavoriteMovie(){

         FavoriteMovie favoriteMovie=new FavoriteMovie(id, voteCount,title,originalTitle,overview,posterPath
                ,backdropPath,voteAverage,releaseDate);
         return favoriteMovie;

    }





}



