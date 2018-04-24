package com.example.shams.moviestageone.movie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.database.FMContract.MoviesEntry;
import com.example.shams.moviestageone.database.FMDbHelper;
import com.example.shams.moviestageone.movie.details.MovieDetailsFragmentAdapter;
import com.example.shams.moviestageone.movie.main.Movies;

public class MovieDetailsActivity extends AppCompatActivity {

    private boolean isMovieFavourite ;

    private FMDbHelper fmDbHelper;

    public static Movies currentFavouriteMovie;
    private Toast toast;
    public static Movies currentMovie;
    private final String SAVED_INSTANCE_KEY = "current_movie_saved_instance" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        fmDbHelper = new FMDbHelper(this);

        TabLayout tabLayout = findViewById(R.id.tl_movie_details_tab_id);

        ViewPager viewPager = findViewById(R.id.vp_movie_details_tab_id);

        MovieDetailsFragmentAdapter movieDetailsFragmentAdapter
                = new MovieDetailsFragmentAdapter(getSupportFragmentManager(), this);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(movieDetailsFragmentAdapter);

        if (currentFavouriteMovie != null) {
            currentMovie = currentFavouriteMovie;
        } else {
            currentMovie = getIntent().getParcelableExtra(Constants.MOVIE_OBJECT_KEY);
        }

        isMovieFavourite = hasObject(String.valueOf(currentMovie.getMovieId()));

    }

    private ContentValues getContentValuesColumns() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesEntry.COLUMN_MOVIE_ID,currentMovie.getMovieId());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_NAME,currentMovie.getOriginalTitle());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, currentMovie.getReleaseDate());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE, String.valueOf(currentMovie.getVoteAverage()));
        contentValues.put(MoviesEntry.COLUMN_MOVIE_OVERVIEW, currentMovie.getOverview());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_POSTER,currentMovie.getPosterPath());

        if (isMovieFavourite) {
            contentValues.put(MoviesEntry.COLUMN_IS_FAVOURITE, MoviesEntry.IS_FAVOURITE_TRUE);
        } else {
            contentValues.put(MoviesEntry.COLUMN_IS_FAVOURITE, MoviesEntry.IS_FAVOURITE_FALSE);
        }

        return contentValues;
    }

        private void makeToast(String message){

            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);

            toast.show();
        }

    public boolean hasObject(String id) {
        SQLiteDatabase db = fmDbHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + MoviesEntry.TABLE_NAME +
                " WHERE " + MoviesEntry.COLUMN_MOVIE_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectString,null);

        if(cursor.getCount() <= 0){
           cursor.close();
           db.close();

            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_detail_activity,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isMovieFavourite){
            menu.getItem(0).setIcon(R.drawable.ic_favorite_red_36px);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_white_36px);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.favourite_icon_id_detail_activity:
                if (isMovieFavourite) {
                    item.setIcon(R.drawable.ic_favorite_border_white_36px);
                    isMovieFavourite = false;
                    makeToast(getString(R.string.movie_deleted_from_favourite));
                    deleteProducts();
                }else {
                    item.setIcon(R.drawable.ic_favorite_red_36px);
                    isMovieFavourite = true;
                    makeToast(getString(R.string.movie_added_to_favourite));
                    insertInventoryProduct();
                }
                return true;
            case android.R.id.home:
                currentFavouriteMovie = null;
                NavUtils.navigateUpFromSameTask(this);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFavouriteMovie = null;
    }

    private void insertInventoryProduct() {

            ContentValues contentValues = getContentValuesColumns();

            makeToast(currentMovie.getOriginalTitle());

            Uri newUri = getContentResolver().insert(MoviesEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                makeToast(getString(R.string.failed_to_insert_movie));
            } else {
                makeToast(getString(R.string.movie_saved_correctly));
            }

    }

    private void deleteProducts() {
        int rowsDeleted = 0;
        if (MoviesEntry.CONTENT_URI != null) {
            rowsDeleted = getContentResolver()
                    .delete(MoviesEntry.CONTENT_URI
                            ,MoviesEntry.COLUMN_MOVIE_ID + " = " + currentMovie.getMovieId()
                            , null);
        }

        if (rowsDeleted == 0) {
            makeToast(getString(R.string.failed_to_delete_movie) + MoviesEntry.CONTENT_URI);
        } else {
            makeToast(getString(R.string.movie_deleted_done));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_KEY,currentMovie);

    }
}
