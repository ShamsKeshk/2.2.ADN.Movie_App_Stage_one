package com.example.shams.moviestageone.movie.main;

import android.text.TextUtils;
import android.util.Log;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.network.connection.utils.FetchDataHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shams on 14/03/18.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }


    public static List<Movies> fetchMovieData(String url){

        URL mUrl = FetchDataHttpConnection.createUrl(url);

        String jsonResponse = null;

        try {
            jsonResponse = FetchDataHttpConnection.makeHttpRequest(mUrl);
        }catch (IOException e){
            Log.e(LOG_TAG , "error While Fetching Data");
        }

        return extractJsonData(jsonResponse);
    }


    private static List<Movies> extractJsonData(String jsonResponse){
        List<Movies> moviesList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray resultJsonArray = new JSONArray();
            if (rootJsonObject.has(Constants.JSON_KEY_RESULTS)){
                resultJsonArray = rootJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            }
            int movieId = 0;
            Double voteAverage = 0.0;
            String originalTitle = "";
            String posterPath = "";
            String releaseDate = "";
            String overview = "";

            for (int i=0; i <resultJsonArray.length() ; i++){
                JSONObject currentJsonObject = resultJsonArray.getJSONObject(i);

                if (currentJsonObject.has(Constants.JSON_KEY_MOVIE_ID)){
                    movieId = currentJsonObject.getInt(Constants.JSON_KEY_MOVIE_ID);
                }

                if (currentJsonObject.has(Constants.JSON_KEY_VOTE_AVERAGE)){
                    voteAverage = currentJsonObject.getDouble(Constants.JSON_KEY_VOTE_AVERAGE);
                }
                if (currentJsonObject.has(Constants.JSON_KEY_ORIGINAL_TITLE)){
                    originalTitle = currentJsonObject.getString(Constants.JSON_KEY_ORIGINAL_TITLE);
                }
                if (currentJsonObject.has(Constants.JSON_KEY_POSTER_PATH)){
                    posterPath = currentJsonObject.getString(Constants.JSON_KEY_POSTER_PATH);
                }
                if (currentJsonObject.has(Constants.JSON_KEY_RELEASE_DATE)){
                    releaseDate = currentJsonObject.getString(Constants.JSON_KEY_RELEASE_DATE);
                }
                if (currentJsonObject.has(Constants.JSON_KEY_OVERVIEW)){
                    overview = currentJsonObject.getString(Constants.JSON_KEY_OVERVIEW);
                }

                moviesList.add(new Movies(movieId,voteAverage ,originalTitle,posterPath,releaseDate,overview));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return moviesList;
    }
}
