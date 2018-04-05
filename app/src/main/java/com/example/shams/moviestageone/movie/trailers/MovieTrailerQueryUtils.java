package com.example.shams.moviestageone.movie.trailers;

import android.text.TextUtils;
import android.util.Log;

import com.example.shams.moviestageone.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by shams on 05/04/18.
 */

public final class MovieTrailerQueryUtils  {

    public static final String LOG_TAG = MovieTrailerQueryUtils.class.getSimpleName();

    private MovieTrailerQueryUtils() {

    }

    public static List<MovieTrailer> fetchMovieData(String url){
        //Done

        URL mUrl = createUrl(url);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(mUrl);
        }catch (IOException e){
            Log.e(LOG_TAG , "error While Fetching Data");
        }
        List<MovieTrailer> movieTrailersList = extractJsonData(jsonResponse);
        return movieTrailersList;
    }

    private static URL createUrl(String url){
        //Done
        URL mUrl = null;
        try {
            mUrl = new URL(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG , "Invalid URL" );
        }
        return mUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException{

        //Done

        if (url == null){
            return null;
        }

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();
            if (hasNext){
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }

    private static List<MovieTrailer> extractJsonData(String jsonResponse) {

        List<MovieTrailer> movieTrailersList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray resultJsonArray = new JSONArray();
            if (rootJsonObject.has(Constants.RESULTS_JSON_KEY)){
                resultJsonArray = rootJsonObject.getJSONArray(Constants.RESULTS_JSON_KEY);
            }

            String movieTrailerKey = "" ;
            String movieTrailerName = "";

            for (int i = 0; i < resultJsonArray.length(); i++){
                JSONObject currentJsonObject = resultJsonArray.getJSONObject(i);

                if (currentJsonObject.has("key")){
                    movieTrailerKey = currentJsonObject.getString("key");
                }

                if (currentJsonObject.has("name")){
                    movieTrailerName = currentJsonObject.getString("name");
                }

                movieTrailersList.add(new MovieTrailer(movieTrailerKey, movieTrailerName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieTrailersList;
    }
}