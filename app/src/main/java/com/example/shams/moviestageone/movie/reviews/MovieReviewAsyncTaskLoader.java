package com.example.shams.moviestageone.movie.reviews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by shams on 05/04/18.
 */

public class MovieReviewAsyncTaskLoader extends AsyncTaskLoader<List<CustomMovieReview>> {

    private String mUrl ;
    private List<CustomMovieReview> mMovieReviewList = null;

    public MovieReviewAsyncTaskLoader(Context context , String url ){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieReviewList != null){
            deliverResult(mMovieReviewList);
        }else {
            forceLoad();
        }
    }

    @Override
    public List<CustomMovieReview> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        return ReviewQueryUtils.fetchMovieData(mUrl);
    }


    @Override
    public void deliverResult(List<CustomMovieReview> data) {
        mMovieReviewList = data;
        super.deliverResult(data);
    }

}
