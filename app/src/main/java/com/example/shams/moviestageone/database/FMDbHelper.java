package com.example.shams.moviestageone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shams.moviestageone.database.FMContract.MoviesEntry;

public class FMDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MoviesEntry.TABLE_NAME + "(" +
            MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL ," +
            MoviesEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL ," +
            MoviesEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL ," +
            MoviesEntry.COLUMN_IS_FAVOURITE + " INTEGER NOT NULL DEFAULT 0 ) ;";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME;


    public FMDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
