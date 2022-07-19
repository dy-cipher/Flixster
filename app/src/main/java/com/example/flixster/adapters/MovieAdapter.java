package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    View movieView;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

    // inflate a layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 2) {
           movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        } else {
            movieView = LayoutInflater.from(context).inflate(R.layout.item1_movie, parent, false);
            return new ViewHolder(movieView);
        }
//        return new ViewHolder(movieView);
    }

    // populate data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the movie at the passed position
        Movie movie = movies.get(position);
        if(holder.getItemViewType() == 1){
            holder.bind1(movie);
        }else {
            holder.bind2(movie);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        Movie m = movies.get(position);
        int types;
        if ((int)m.getVote_average() > 5){
            types = 1;
        }else {
            types = 2;        }
        return types;
    }

    // viewHolder

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverView;
        ImageView ivPoster;
        RelativeLayout container;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverView = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);

        }


        public void bind1(Movie movie){
            // render images
            String imageUrl;
            imageUrl = movie.getBackDropPath();


            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loadind1)
                    .error(R.drawable.error)
                    .into(ivPoster);
        }


        public void bind2(Movie movie){

            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverView());
            // render images
            String imageUrl;
            if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                imageUrl = movie.getBackDropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }


            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loadind1)
                    .error(R.drawable.error)
                    .into(ivPoster);
            // 1. Register click Listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }

    }

}
