package com.example.shams.moviestageone;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<List<Movies>>{

    private final String baseUrl = "https://api.themoviedb.org/3/movie/popular?api_key=c50f5aa4e7c95a2a553d29b81aad6dd0";

    private final int MOVIE_LOADER_ID = 1;

    @BindView(R.id.rv_movies_id)
    RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this , 2));
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this , new ArrayList<Movies>());

        recyclerView.setAdapter(moviesAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADER_ID ,null , MainActivity.this);

    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {
        return new MovieAsyncTaskLoader(this , baseUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {
        moviesAdapter.clearAdapter();

        if (data != null){
            moviesAdapter.setmMoviesList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        moviesAdapter.clearAdapter();
    }
}
