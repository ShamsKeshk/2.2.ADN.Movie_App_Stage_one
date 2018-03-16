package com.example.shams.moviestageone;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
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

        if (getIntent().hasExtra(Constants.MOVIE_OBJECT_KEY)) {
            movie = (Movies) getIntent().getSerializableExtra(Constants.MOVIE_OBJECT_KEY);
        }

        if (movie.getPosterPath() != null){
            Uri imageUri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                    .appendPath(Constants.IMAGE_SIZE_W185)
                    .appendEncodedPath(movie.getPosterPath())
                    .build();

            Picasso.get().load(imageUri).into(mMoviePosterImageView);
        } else {
            mMoviePosterImageView.setImageResource(R.drawable.no_image);
        }

        setTextToView(movie.getOriginalTitle(), mMovieOriginalTitleTextView);

        setTextToView(movie.getReleaseDate(), mMovieReleaseDateTextView);

        setTextToView(String.valueOf(movie.getVoteAverage()), mMovieVoteAverageTextView);

        setTextToView(movie.getOverview(), mMovieOverviewTextView);
    }

    private void setTextToView(String text, TextView textView) {

        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText(R.string.not_provided);
        }
    }
}
