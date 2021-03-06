package com.example.shams.moviestageone.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shams.moviestageone.BuildConfig;
import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.adapters.MoviesAdapter;
import com.example.shams.moviestageone.asynctask.MovieAsyncTaskLoader;
import com.example.shams.moviestageone.movie.Movies;
import com.example.shams.moviestageone.network.connection.utils.NetworkStatues;
import com.example.shams.moviestageone.setting.preference.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movies>>,
        MoviesAdapter.MovieListClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final int MOVIE_LOADER_ID = 1;
    @BindView(R.id.rv_movies_id)
    RecyclerView recyclerView;
    @BindView(R.id.tv_connection_error_id)
    TextView mConnectionErrorTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.sr_activity_main_id)
    SwipeRefreshLayout swipeRefreshLayout;
    private Uri moviesUri;
    private MoviesAdapter moviesAdapter;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this , calculateNoOfColumns(this));

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(new ArrayList<Movies>(), this);

        recyclerView.setAdapter(moviesAdapter);


        if (NetworkStatues.isConnected(this)) {
            hideConnectionErrorDisplayData();
            loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);
        } else {
            displayConnectionErrorHideData();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkStatues.isConnected(MainActivity.this)) {
                    hideConnectionErrorDisplayData();
                    getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                } else {
                    displayConnectionErrorHideData();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void hideConnectionErrorDisplayData() {
        mConnectionErrorTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void displayConnectionErrorHideData() {
        mConnectionErrorTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void setUpSharedPreference() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String movieType = sharedPreferences.getString(getString(R.string.movies_type_key), getString(R.string.movies_type_popular_value));
        moviesUri = Uri.parse(Constants.BASE_MOVIE_URL).buildUpon()
                .appendPath(movieType)
                .appendQueryParameter(Constants.API_KEY,BuildConfig.MY_API_KEY)
                .build();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {

        progressBar.setVisibility(View.VISIBLE);
        setUpSharedPreference();

        return new MovieAsyncTaskLoader(this, moviesUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {
        progressBar.setVisibility(View.GONE);

        moviesAdapter.clearAdapter();

        if (data != null) {
            moviesAdapter.setmMoviesList(data);
        } else {
            mConnectionErrorTextView.setText(R.string.no_movies_found);
            displayConnectionErrorHideData();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        moviesAdapter.clearAdapter();
    }

    @Override
    public void onMovieListClickListener(int position) {
        Movies movies = moviesAdapter.getItem(position);
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(Constants.MOVIE_OBJECT_KEY, movies);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.filter_icon:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            case R.id.favourite_icon_id:
                startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.movies_type_key))) {
            loaderManager.restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }


}
