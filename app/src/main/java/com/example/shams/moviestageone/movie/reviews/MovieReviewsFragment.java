package com.example.shams.moviestageone.movie.reviews;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.movie.MovieDetailsActivity;
import com.example.shams.moviestageone.movie.main.Movies;
import com.example.shams.moviestageone.network.connection.utils.NetworkStatues;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieReviewsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<CustomMovieReview>>{

    private final int MOVIE_LOADER_ID = 2;
    private Uri movieReviewsUri;

    @BindView(R.id.rv_movie_reviews_id)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar_review_activity)
    ProgressBar progressBar;
    @BindView(R.id.sr_movie_reviews_fragment_id)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_empty_text_view_reviews_fragment_id)
    TextView emptyTextView;

    MovieReviewsAdapter movieReviewsAdapter;

    LoaderManager loaderManager;


    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieReviewsAdapter = new MovieReviewsAdapter(new ArrayList<CustomMovieReview>());

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL ,false);


        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(movieReviewsAdapter);
        final LoaderManager.LoaderCallbacks loaderCallbacks = this;


        if (NetworkStatues.isConnected(getActivity())) {
            hideEmptyText();
            loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, loaderCallbacks);
        } else {
            emptyTextView.setText(getString(R.string.no_internet_connection_connect_and_try_again));
            displayEmptyText();
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkStatues.isConnected(getActivity())) {
                    hideEmptyText();
                    loaderManager.restartLoader(MOVIE_LOADER_ID, null, loaderCallbacks);
                } else {
                    emptyTextView.setText(getString(R.string.no_internet_connection_connect_and_try_again));
                    displayEmptyText();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        ButterKnife.bind(this , view);

        return view;
    }


    @NonNull
    @Override
    public Loader<List<CustomMovieReview>> onCreateLoader(int id, @Nullable Bundle args) {
        Movies currentMovie;
        if (MovieDetailsActivity.currentFavouriteMovie != null) {
            currentMovie = MovieDetailsActivity.currentFavouriteMovie;
        } else {
            currentMovie = getActivity().
                    getIntent().
                    getParcelableExtra(Constants.MOVIE_OBJECT_KEY);
        }

        int movieId = currentMovie.getMovieId() ;

        movieReviewsUri = Uri.parse(Constants.BASE_MOVIE_URL).buildUpon()
                .appendEncodedPath(String.valueOf(movieId))
                .appendEncodedPath("reviews")
                .appendQueryParameter(Constants.API_KEY,Constants.API_KEY_VALUE)
                .build();

        progressBar.setVisibility(View.VISIBLE);

        return new MovieReviewAsyncTaskLoader(getActivity(),movieReviewsUri.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CustomMovieReview>> loader, List<CustomMovieReview> data) {
        progressBar.setVisibility(View.GONE);

        movieReviewsAdapter.clearAdapter();

        if (data != null) {
            hideEmptyText();
            movieReviewsAdapter.setmMovieReviewsList(data);
        } else {
            emptyTextView.setText(getString(R.string.there_are_no_reviews_for_this_movie));
            displayEmptyText();
        }
    }

    private void displayEmptyText() {
        emptyTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyText() {
        emptyTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CustomMovieReview>> loader) {
        movieReviewsAdapter.clearAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        loaderManager.restartLoader(MOVIE_LOADER_ID,null,this);
    }


}
