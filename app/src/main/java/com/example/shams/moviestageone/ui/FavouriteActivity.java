package com.example.shams.moviestageone.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.adapters.FavouriteMovieCursorAdapter;
import com.example.shams.moviestageone.database.FMContract.MoviesEntry;
import com.example.shams.moviestageone.movie.Movies;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int FAVOURITE_MOVIE_LOADER_ID = 50;

    @BindView(R.id.lv_favourite_movie_id)
    ListView listView;
    @BindView(R.id.tv_empty_view_favourite_activity_id)
    TextView emptyTextView;

    private FavouriteMovieCursorAdapter favouriteMovieCursorAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);

        favouriteMovieCursorAdapter = new
                FavouriteMovieCursorAdapter(this, null);
        listView.setAdapter(favouriteMovieCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavouriteActivity.this, MovieDetailsActivity.class);
                MovieDetailsActivity.currentFavouriteMovie = getMovie(cursor);
                startActivity(intent);

            }
        });
        listView.setEmptyView(emptyTextView);

        getSupportLoaderManager().initLoader(FAVOURITE_MOVIE_LOADER_ID, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {MoviesEntry._ID
                , MoviesEntry.COLUMN_MOVIE_ID
                , MoviesEntry.COLUMN_MOVIE_NAME
                , MoviesEntry.COLUMN_MOVIE_RELEASE_DATE
                , MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE
                , MoviesEntry.COLUMN_MOVIE_OVERVIEW
                , MoviesEntry.COLUMN_MOVIE_POSTER};
        return new CursorLoader(this, MoviesEntry.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            favouriteMovieCursorAdapter.swapCursor(data);
        }
        this.cursor = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favouriteMovieCursorAdapter.swapCursor(null);
    }

    private Movies getMovie(Cursor cursor) {
        int movieId = getIntFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_ID);
        String movieName = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_NAME);
        String movieReleaseDate = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_RELEASE_DATE);
        double movieVoteAverage =
                Double.valueOf(getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE));
        String movieOverView = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_OVERVIEW);
        String imageUri = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_POSTER);

        return new Movies(movieId, movieVoteAverage, movieName, imageUri, movieReleaseDate, movieOverView);
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    private int getIntFromCursor(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
