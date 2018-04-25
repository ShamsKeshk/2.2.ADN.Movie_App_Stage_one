package com.example.shams.moviestageone.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.movie.MovieTrailer;
import com.example.shams.moviestageone.network.connection.utils.FetchDataHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shams on 05/04/18.
 */

public final class MovieTrailerQueryUtils  {

    public static final String LOG_TAG = MovieTrailerQueryUtils.class.getSimpleName();

    private MovieTrailerQueryUtils() {

    }

    public static List<MovieTrailer> fetchMovieData(String url){

        URL mUrl = FetchDataHttpConnection.createUrl(url);

        String jsonResponse = null;

        try {
            jsonResponse = FetchDataHttpConnection.makeHttpRequest(mUrl);
        }catch (IOException e){
            Log.e(LOG_TAG , "error While Fetching Data");
        }

        return extractJsonData(jsonResponse);
    }

    private static List<MovieTrailer> extractJsonData(String jsonResponse) {

        List<MovieTrailer> movieTrailersList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray resultJsonArray = new JSONArray();
            if (rootJsonObject.has(Constants.JSON_KEY_RESULTS)) {
                resultJsonArray = rootJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            }

            String movieTrailerKey = "" ;
            String movieTrailerName = "";

            for (int i = 0; i < resultJsonArray.length(); i++){
                JSONObject currentJsonObject = resultJsonArray.getJSONObject(i);

                if (currentJsonObject.has(Constants.JSON_KEY_TRAILER_KEY)) {
                    movieTrailerKey = currentJsonObject.getString(Constants.JSON_KEY_TRAILER_KEY);
                }

                if (currentJsonObject.has(Constants.JSON_KEY_TRAILER_NAME)) {
                    movieTrailerName = currentJsonObject.getString(Constants.JSON_KEY_TRAILER_NAME);
                }

                movieTrailersList.add(new MovieTrailer(movieTrailerKey, movieTrailerName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieTrailersList;
    }
}
