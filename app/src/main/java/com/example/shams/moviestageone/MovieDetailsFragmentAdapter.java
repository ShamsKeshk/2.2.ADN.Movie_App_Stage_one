package com.example.shams.moviestageone;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.shams.moviestageone.movie.reviews.MovieReviewsFragment;
import com.example.shams.moviestageone.movie.trailers.MovieTrailerFragment;

/**
 * Created by shams on 04/04/18.
 */

public class MovieDetailsFragmentAdapter extends FragmentPagerAdapter {

    public MovieDetailsFragmentAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
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
                return "Details";
            case 1:
                return "Reviews";
            case 2:
                return "Trailers";
            default:
                return super.getPageTitle(position);
        }
    }
}
