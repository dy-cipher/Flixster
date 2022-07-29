package com.example.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.flixster.R;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.network.NetworkVideoClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    RecyclerView rvMovies;
    List<Movie> movies;
    // Store the binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view (replacing `setContentView`)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        rvMovies = binding.rvMovies;
        movies = new ArrayList<>();

        // create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set teh adapter to the recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a layout adapter on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // using network class for cleaner code
        NetworkVideoClient networkVideoClient = new NetworkVideoClient(NOW_PLAYING_URL);
        networkVideoClient.mainActivityClient((ArrayList) movies, movieAdapter, TAG);

    }
}