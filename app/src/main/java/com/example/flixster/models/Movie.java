package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    double vote_average;
    String backDropPath;
    String posterPath;
    String title;
    String overView;

    public Movie(JSONObject jsonObject) throws JSONException {
        backDropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overView = jsonObject.getString("overview");
        vote_average = jsonObject.getDouble("vote_average");
    }

    public  static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        int len_movie = movieJsonArray.length();
        for (int i = 0; i < len_movie; i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }


    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverView() {
        return overView;
    }
}
