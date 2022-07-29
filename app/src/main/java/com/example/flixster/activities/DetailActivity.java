package com.example.flixster.activities;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.flixster.R;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.example.flixster.network.NetworkVideoClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import org.parceler.Parcels;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyCcEuedd4qSn9u53hDZucg3T99OE6Mq404";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    TextView tvTitle1;
    TextView tvOverview1;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    ActivityDetailBinding detailBinding;
    String TAG = "DetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

//        tvTitle1 = detailBinding.tvTitle1;
//        tvOverview1 = detailBinding.tvOverview1;
//        ratingBar = detailBinding.ratingBar;
        youTubePlayerView = detailBinding.player;

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(getString(R.string.movie)));
//        tvTitle1.setText(movie.getTitle());
//        tvOverview1.setText(movie.getOverView());
//        ratingBar.setRating((float) movie.getVote_average());
        detailBinding.setMovie(movie);
        detailBinding.executePendingBindings();

        // using network class
        NetworkVideoClient networkVideoClient = new NetworkVideoClient(VIDEOS_URL);
        networkVideoClient.detailActivityClient(movie, TAG, youTubePlayerView, YOUTUBE_API_KEY);

    }

}