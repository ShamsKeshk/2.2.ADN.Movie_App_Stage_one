package com.example.shams.moviestageone.movie.trailers;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by shams on 05/04/18.
 */

public class MovieTrailerAsyncTask extends AsyncTaskLoader<List<MovieTrailer>> {

    private String mUrl;
    private List<MovieTrailer> mMovieTrailersList = null;

    public MovieTrailerAsyncTask(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieTrailersList != null) {
            deliverResult(mMovieTrailersList);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<MovieTrailer> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return MovieTrailerQueryUtils.fetchMovieData(mUrl);
    }


    @Override
    public void deliverResult(List<MovieTrailer> data) {
        mMovieTrailersList = data;
        super.deliverResult(data);
    }

}
