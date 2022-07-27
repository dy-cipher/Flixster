package com.example.flixster.adapters;

import android.app.Activity;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (viewType == 2) {
            movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        } else {
            movieView = LayoutInflater.from(context).inflate(R.layout.item1_movie, parent, false);
            return new ViewHolder1(movieView);
        }
//        return new ViewHolder(movieView);
    }

    // populate data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,  int position) {

        // Get the movie at the passed position
        Movie movie = movies.get(position);
        if (holder.getItemViewType() == 1) {
            ViewHolder1 vh = (ViewHolder1) holder;
            vh.bind1(movie);
        } else {
            ViewHolder vh = (ViewHolder) holder;
            vh.bind(movie);
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
        if ((float) m.getVote_average() > 7) {
            types = 1;
        } else {
            types = 2;
        }
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

        public void bind(Movie movie) {
            // render images
            String imageUrl;
            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverView());


            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackDropPath();
            }
            else {
                imageUrl = movie.getPosterPath();

            }
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loadind1)
                    .error(R.drawable.error)
                    .into(ivPoster);

//            ivPoster.setImageResource(R.drawable.play);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)  context, ivPoster, "poster");
                    context.startActivity(i, options.toBundle());

                }
            });
        }

    }


    public class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView ivPoster1;
        CardView container;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            ivPoster1 = itemView.findViewById(R.id.ivPoster1);
            container = itemView.findViewById(R.id.cardview);
        }




        public void bind1(Movie movie) {

            // render images
            String imageUrl = movie.getBackDropPath();

//            ivPoster.setImageResource(R.drawable.play);

            double radius = 30;
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.loadind1)
                    .error(R.drawable.error)
                    .into(ivPoster1);
            // 1. Register click Listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)  context, ivPoster1, "poster");
                    context.startActivity(i, options.toBundle());
                }
            });
        }


    }

}


