package com.example.shams.moviestageone.movie.reviews;

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
 * Created by shams on 05/04/18.
 */

public final class ReviewQueryUtils {

    private static final String LOG_TAG = ReviewQueryUtils.class.getSimpleName();

    private ReviewQueryUtils() {

    }

    public static List<CustomMovieReview> fetchMovieData(String url){

        URL mUrl = FetchDataHttpConnection.createUrl(url);

        String jsonResponse = null;

        try {
            jsonResponse = FetchDataHttpConnection.makeHttpRequest(mUrl);
        }catch (IOException e){
            Log.e(LOG_TAG , "error While Fetching Data");
        }

        return extractJsonData(jsonResponse);
    }

    private static List<CustomMovieReview> extractJsonData(String jsonResponse) {

        List<CustomMovieReview> movieReviewsList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray resultJsonArray = new JSONArray();
            if (rootJsonObject.has(Constants.JSON_KEY_RESULTS)){
                resultJsonArray = rootJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            }

            String reviewAuthor = "" ;
            String reviewContent = "";

            for (int i = 0; i < resultJsonArray.length(); i++){
                JSONObject currentJsonObject = resultJsonArray.getJSONObject(i);

                if (currentJsonObject.has(Constants.JSON_KEY_REVIEW_AUTHOR)){
                    reviewAuthor = currentJsonObject.getString(Constants.JSON_KEY_REVIEW_AUTHOR);
                }

                if (currentJsonObject.has(Constants.JSON_KEY_REVIEW_CONTENT)){
                    reviewContent = currentJsonObject.getString(Constants.JSON_KEY_REVIEW_CONTENT);
                }

                movieReviewsList.add(new CustomMovieReview(reviewAuthor, reviewContent));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieReviewsList;
    }
    }

