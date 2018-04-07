package com.example.shams.moviestageone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shams.moviestageone.database.FMContract;
import com.example.shams.moviestageone.database.FMContract.MoviesEntry;

import com.example.shams.moviestageone.database.FMDbHelper;
import com.example.shams.moviestageone.movie.details.MovieDetailsFragmentAdapter;

public class MovieDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {


    private final String TAG = MainActivity.class.getSimpleName();

    private Movies currentMovie;
    private int isFavourite ;

    private boolean isMovieFavourite ;

    private FMDbHelper fmDbHelper;

    Toast t;

    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        fmDbHelper = new FMDbHelper(this);

        //

        Log.d(TAG, "onCreate: ");

        TabLayout tabLayout = findViewById(R.id.tl_movie_details_tab_id);

        ViewPager viewPager = findViewById(R.id.vp_movie_details_tab_id);

        MovieDetailsFragmentAdapter movieDetailsFragmentAdapter
                = new MovieDetailsFragmentAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.setAdapter(movieDetailsFragmentAdapter);

        currentMovie = getIntent().getParcelableExtra(Constants.MOVIE_OBJECT_KEY);


        isMovieFavourite = hasObject(String.valueOf(currentMovie.getMovieId()));


        if (isMovieFavourite){
            isFavourite = 1;
        }else {
            isFavourite = 0;
        }

        makeToast(String.valueOf(isMovieFavourite));

    }

    private ContentValues getContentValuesColumns() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesEntry.COLUMN_MOVIE_ID,currentMovie.getMovieId());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_NAME,currentMovie.getOriginalTitle());
        contentValues.put(MoviesEntry.COLUMN_MOVIE_POSTER,currentMovie.getPosterPath());
        contentValues.put(MoviesEntry.COLUMN_IS_FAVOURITE,isFavourite);


        return contentValues;
    }

        private void makeToast(String message){

            if (t != null){
                t.cancel();
            }
            t =    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);

            t.show();
        }

    public boolean hasObject(String id) {
        SQLiteDatabase db = fmDbHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + MoviesEntry.TABLE_NAME +
                " WHERE " + MoviesEntry.COLUMN_MOVIE_ID + " = " + currentMovie.getMovieId();

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString,null);

        if(cursor.getCount() <= 0){
           cursor.close();
           db.close();
            Log.d(TAG, String.format("%d records found", cursor.getCount()));

            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_detail_activity,menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isMovieFavourite){
            menu.getItem(0).setIcon(R.drawable.ic_star_yello_36px);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_filter_list_white_36px);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.favourite_icon_id_detail_activity:
                if (isMovieFavourite) {
                    item.setIcon(R.drawable.ic_filter_list_white_36px);
                    isMovieFavourite = false;
                    makeToast(String.valueOf(isMovieFavourite));
                    deleteProducts();
                }else {
                    item.setIcon(R.drawable.ic_star_yello_36px);
                    isMovieFavourite = true;
                    makeToast(String.valueOf(isMovieFavourite));
                    insertInventoryProduct();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertInventoryProduct() {
        //get Values From Views To Start Insert Them Into The Database

            ContentValues contentValues = getContentValuesColumns();

            makeToast(currentMovie.getOriginalTitle());

            Uri newUri = getContentResolver().insert(MoviesEntry.CONTENT_URI, contentValues);

            if (newUri == null) {
                makeToast("failed_to_insert_product");
            } else {
                makeToast("product_saved_correctly");
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
            makeToast("failed_to_delete_products_with_this_uri" + MoviesEntry.CONTENT_URI );
        } else {
            makeToast("delete_product_done " + MoviesEntry.CONTENT_URI  + rowsDeleted);
        }
    }


}
