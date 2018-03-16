package com.example.shams.moviestageone;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

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


    private Movies movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("movie_object")){
            movie =(Movies) getIntent().getSerializableExtra("movie_object");
        }

        if (movie.getPosterPath() != null){
            Uri imageUri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                    .appendPath(Constants.IMAGE_SIZE_W185)
                    .appendEncodedPath(movie.getPosterPath())
                    .build();

            Picasso.get().load(imageUri).into(mMoviePosterImageView);
        }

        if (movie.getOriginalTitle() != null){
            mMovieOriginalTitleTextView.setText(movie.getOriginalTitle());
        }

        if (movie.getReleaseDate() != null){
            mMovieReleaseDateTextView.setText(movie.getReleaseDate());
        }

        if (movie.getVoteAverage() != null){
            mMovieVoteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));
        }

        if (movie.getOverview() != null){
            mMovieOverviewTextView.setText(movie.getOverview());
        }



    }
}
