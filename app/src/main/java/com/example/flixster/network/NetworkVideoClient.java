package com.example.flixster.network;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.activities.DetailActivity;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class NetworkVideoClient {
    private final String url;

    public NetworkVideoClient(String url) {
        this.url = url;
    }

    public void mainActivityClient(ArrayList movies, MovieAdapter movieAdapter, String TAG) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.d(TAG, "Succeed");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "hit  json exception");
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Failed");
            }
        });

    }


    public void detailActivityClient(Movie movie, String TAG, YouTubePlayerView youTubePlayerView, String YOUTUBE_API_KEY) {
        DetailActivity detailActivity = new DetailActivity();


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(url, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray(("results"));
                    if (results.length() == 0){
                        return; // todo placeholder image
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d(TAG, youtubeKey);
                    initializeYoutube(youtubeKey,movie,youTubePlayerView, YOUTUBE_API_KEY, TAG);
//                    initializeYoutube(youtubeKey, movie);
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse json ", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    public void initializeYoutube(final String youtubeKey, Movie movie, YouTubePlayerView youTubePlayerView, String YOUTUBE_API_KEY, String TAG) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializationSuccess");
                if (movie.getVote_average() < 7) {
                    youTubePlayer.cueVideo(youtubeKey);
                }
                else {
                    youTubePlayer.loadVideo(youtubeKey);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure");
            }
        });
    }



}
