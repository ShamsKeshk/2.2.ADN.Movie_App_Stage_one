package com.example.shams.moviestageone.movie.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.movie.reviews.MovieReviewsFragment;
import com.example.shams.moviestageone.movie.trailers.MovieTrailerFragment;

/**
 * Created by shams on 04/04/18.
 */

public class MovieDetailsFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public MovieDetailsFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieDetailsFragment();
            case 1:
                return new MovieReviewsFragment();
            case 2:
                return new MovieTrailerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.details_title);
            case 1:
                return context.getString(R.string.reviews_title);
            case 2:
                return context.getString(R.string.trailers_title);
            default:
                return super.getPageTitle(position);
        }
    }
}
