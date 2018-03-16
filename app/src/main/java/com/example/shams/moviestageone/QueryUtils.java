package com.example.shams.moviestageone;

import android.text.TextUtils;
import android.util.Log;

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
 * Created by shams on 14/03/18.
 */

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(){
    }



    public static List<Movies> fetchMovieData(String url){
        //Done

        URL mUrl = createUrl(url);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(mUrl);
        }catch (IOException e){
            Log.e(LOG_TAG , "error While Fetching Data");
        }
        List<Movies> moviesList = extractJsonData(jsonResponse);
        return moviesList;
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

    private static List<Movies> extractJsonData(String jsonResponse){
        List<Movies> moviesList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray resultJsonArray = new JSONArray();
            if (rootJsonObject.has(Constants.RESULTS_JSON_KEY)){
                resultJsonArray = rootJsonObject.getJSONArray(Constants.RESULTS_JSON_KEY);
            }
            Double voteAverage = 0.0;
            String originalTitle = "";
            String posterPath = "";
            String releaseDate = "";
            String overview = "";

            for (int i=0; i <resultJsonArray.length() ; i++){
                JSONObject currentJsonObject = resultJsonArray.getJSONObject(i);
                if (currentJsonObject.has(Constants.VOTE_AVERAGE_JSON_KEY)){
                    voteAverage = currentJsonObject.getDouble(Constants.VOTE_AVERAGE_JSON_KEY);
                }
                if (currentJsonObject.has(Constants.ORIGINAL_TITLE_JSON_KEY)){
                    originalTitle = currentJsonObject.getString(Constants.ORIGINAL_TITLE_JSON_KEY);
                }
                if (currentJsonObject.has(Constants.POSTER_PATH_JSON_KEY)){
                    posterPath = currentJsonObject.getString(Constants.POSTER_PATH_JSON_KEY);
                }
                if (currentJsonObject.has(Constants.RELEASE_DATE_JSON_KEY)){
                    releaseDate = currentJsonObject.getString(Constants.RELEASE_DATE_JSON_KEY);
                }
                if (currentJsonObject.has(Constants.OVERVIEW_JSON_KEY)){
                    overview = currentJsonObject.getString(Constants.OVERVIEW_JSON_KEY);
                }

                moviesList.add(new Movies(voteAverage ,originalTitle,posterPath,releaseDate,overview));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return moviesList;
    }
}
