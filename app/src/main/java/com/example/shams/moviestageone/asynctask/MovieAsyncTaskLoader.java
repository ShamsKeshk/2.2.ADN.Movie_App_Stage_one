package com.example.shams.moviestageone.asynctask;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.shams.moviestageone.movie.Movies;
import com.example.shams.moviestageone.utils.QueryUtils;

import java.util.List;

/**
 * Created by shams on 14/03/18.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<List<Movies>> {

    private String mUrl;
    private List<Movies> mMoviesList = null;

    public MovieAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMoviesList != null) {
            deliverResult(mMoviesList);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Movies> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchMovieData(mUrl);
    }


    @Override
    public void deliverResult(List<Movies> data) {
        mMoviesList = data;
        super.deliverResult(data);
    }

}
