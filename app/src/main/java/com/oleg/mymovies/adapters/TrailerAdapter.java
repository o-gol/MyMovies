package com.oleg.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.oleg.mymovies.R;
import com.oleg.mymovies.data.Trailer;

import java.net.URL;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerVieWHolder> {
    List<Trailer> trailerList;
    public interface ClickOnImageViewVideoListener{
        public void onShotClick(URL url);

    };

    private ClickOnImageViewVideoListener onImageViewVideoClickListener;

    public void setOnImageViewVideoClickListener(ClickOnImageViewVideoListener onImageViewVideoClickListener) {
        this.onImageViewVideoClickListener = onImageViewVideoClickListener;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerVieWHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item,viewGroup,false);
        return new TrailerAdapter.TrailerVieWHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerVieWHolder trailerVieWHolder, int i) {
        trailerVieWHolder.textView.setText(trailerList.get(i).getName());
        //trailerVieWHolder.imageView.setImageURI(trailerList.get(i).getUrl().toURI());


    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailerVieWHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        //private ClickOnImageViewVideoListener clickOnImageViewVideoListener;

        public TrailerVieWHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewPlay);
            textView=itemView.findViewById(R.id.textViewNameVideo);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onImageViewVideoClickListener!=null){
                        onImageViewVideoClickListener.onShotClick(trailerList.get(getAdapterPosition()).getUrl());
                    }
                }
            });

        }


    }
}
