package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle1;
    TextView tvOverview1;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle1 = findViewById(R.id.tvTitle1);
        tvOverview1 = findViewById(R.id.tvOverview1);
        ratingBar = findViewById(R.id.ratingBar);


        String title = getIntent().getStringExtra("title");
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle1.setText(movie.getTitle());
        tvOverview1.setText(movie.getOverView());
        ratingBar.setRating((float) movie.getVote_average());
    }
}