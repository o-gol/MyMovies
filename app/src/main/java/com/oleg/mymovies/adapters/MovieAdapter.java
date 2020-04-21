package com.oleg.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oleg.mymovies.R;
import com.oleg.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;

    public void clearAdapter(){
        this.movieList.clear();
        notifyDataSetChanged();
    }

    public interface OnMoviePosterClickListener {
        void onShotClick(int pisition);

    }
    public interface OnReachEndListener{
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

        Picasso.get().load(movieList.get(position).getSmallPosterPath()).into(holder.imageViewSmallPoster);
        if(movieList.size()>=20 && position>movieList.size()-5 && onReachEndListener!=null){
            onReachEndListener.onRechEnd();
        }

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
