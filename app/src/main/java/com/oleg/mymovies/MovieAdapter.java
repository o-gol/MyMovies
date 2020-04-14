package com.oleg.mymovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oleg.mymovies.data.Movie;
import com.oleg.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;
    interface OnMoviePosterClickListener {
        void onShotClick(int pisition);

    }
    interface OnReachEndListener{
        void onRechEnd();
    }

    private OnReachEndListener onReachEndListener;
    private OnMoviePosterClickListener onMoviePosterClickListener;


    public void setOnMoviePosterClickListener(OnMoviePosterClickListener onMoviePosterClickListener) {
        this.onMoviePosterClickListener = onMoviePosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public MovieAdapter() {
        movieList = new ArrayList<Movie>();
    }

    public void addMovies (List<Movie> movies){
        movieList.addAll(movies);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if(position>movieList.size()-5 && onReachEndListener!=null){
            onReachEndListener.onRechEnd();
        }
        Picasso.get().load(movieList.get(position).getSmallPosterPath()).into(holder.imageViewSmallPoster);

       /*NetworkUtils.DowenloadSmallPosterURLTask dowenloadSmallPosterTask;

        try {
            dowenloadSmallPosterTask=new NetworkUtils.DowenloadSmallPosterURLTask();
            holder.imageViewSmallPoster.setImageBitmap(dowenloadSmallPosterTask.execute(movieList.get(position).getSmallPosterUrl()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
     /*   NetworkUtils.DowenloadSmallPosterTask dowenloadSmallPosterTask1;

        try {
            dowenloadSmallPosterTask1=new NetworkUtils.DowenloadSmallPosterTask();
            holder.imageViewSmallPoster.setImageBitmap(dowenloadSmallPosterTask1.execute(movieList.get(position).getSmallPosterPath()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageViewSmallPoster=itemView.findViewById(R.id.imageSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onMoviePosterClickListener!=null){
                        onMoviePosterClickListener.onShotClick(getAdapterPosition());
                    }

                }
            });
        }
    }
}
