package com.example.shams.moviestageone.movie.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.movie.MovieDetailsActivity;
import com.example.shams.moviestageone.movie.main.Movies;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class MovieDetailsFragment extends Fragment {

    @BindView(R.id.iv_movie_poster_details_activity)
    ImageView mMoviePosterImageView;
    @BindView(R.id.tv_movie_original_title)
    TextView mMovieOriginalTitleTextView;
    @BindView(R.id.tv_movie_release_date)
    TextView mMovieReleaseDateTextView;
    @BindView(R.id.tv_movie_vote_average)
    TextView mMovieVoteAverageTextView;
    @BindView(R.id.tv_movie_overview_id)
    TextView mMovieOverviewTextView;

    private Movies currentMovie;
    public static String SAVED_INSTANCE_OF_CURRENT_MOVIE__KEY = "saved_instance_of_current_movie_key";


    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (MovieDetailsActivity.currentFavouriteMovie != null) {
            currentMovie = MovieDetailsActivity.currentFavouriteMovie;
            Log.e(TAG, "onActivityCreated: current Movie Value When current Favourite Movie Not equal Null"
                    + currentMovie );
        } else {

            currentMovie = MovieDetailsActivity.currentMovie;
        }

        if (currentMovie.getPosterPath() != null){
            Uri imageUri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                    .appendPath(Constants.IMAGE_SIZE_W185)
                    .appendEncodedPath(currentMovie.getPosterPath())
                    .build();

            Picasso.get().load(imageUri).into(mMoviePosterImageView);

        } else {
            mMoviePosterImageView.setImageResource(R.drawable.no_image);

        }

        setTextToView(currentMovie.getOriginalTitle(), mMovieOriginalTitleTextView);

        setTextToView(currentMovie.getReleaseDate(), mMovieReleaseDateTextView);

        setTextToView(String.valueOf(currentMovie.getVoteAverage()), mMovieVoteAverageTextView);

        setTextToView(currentMovie.getOverview(), mMovieOverviewTextView);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this ,view);

        return view;
    }

    private void setTextToView(String text, TextView textView) {

        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText(R.string.not_provided);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_OF_CURRENT_MOVIE__KEY,currentMovie);
    }

}
