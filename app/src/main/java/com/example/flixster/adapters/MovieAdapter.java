package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.activities.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.Item1MovieBinding;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static Context context;
    List<Movie> movies;
    ItemMovieBinding itemMovieBinding;
    Item1MovieBinding item1MovieBinding;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

    // inflate a layout from XML and return the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == 2) {

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                itemMovieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_movie, parent, false);
                return new ViewHolder(itemMovieBinding);

        }   else {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                item1MovieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item1_movie, parent, false);
                return new ViewHolder1(item1MovieBinding);
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
// First class viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        // this will be used by onBindViewHolder()
        private final ItemMovieBinding binding;



        public ViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            // render images
            binding.setMovie(movie);
            binding.executePendingBindings();

            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)  context, binding.ivPoster, String.valueOf(R.string.poster));
                    context.startActivity(i, options.toBundle());

                }
            });
        }

    }

// second class viewHolder
    public class ViewHolder1 extends RecyclerView.ViewHolder {
        private final Item1MovieBinding binding;

        public ViewHolder1(Item1MovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind1(Movie movie) {

            // render images
//            String imageUrl = movie.getBackDropPath();
            binding.setMovie(movie);
            binding.executePendingBindings();


            binding.ivIcon.setImageResource(R.drawable.plays);
            // 1. Register click Listener on the whole row

            binding.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
//                    Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)  context, binding.ivPoster1, String.valueOf(R.string.poster));
                    context.startActivity(i, options.toBundle());
                }
            });
        }


    }


    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {
            int radius = 30;
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.load)
                    .centerCrop()
                    .transform(new RoundedCorners(radius))
                    .error(R.drawable.error)
                    .into(view);
        }



        @BindingAdapter({"imageUrl1"})
        public static void loadImage1(ImageView view, String url) {
            int radius = 30;
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.load)
                    .centerCrop()
                    .transform(new RoundedCorners(radius))
                    .error(R.drawable.error)
                    .into(view);
        }
    }

}


