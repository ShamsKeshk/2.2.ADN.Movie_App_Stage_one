package com.example.shams.moviestageone.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FMContract {

    public static final String CONTENT_AUTHORITY = "com.example.shams.moviestageone";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String MOVIE_PATH = "moviestageone";

    private FMContract() {

    }

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, MOVIE_PATH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        public static final String TABLE_NAME = "movie_table";

        // Table Columns
        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_NAME = "movie_name";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";

        public static final String COLUMN_IS_FAVOURITE = "is_favourite";

        public static final String COLUMN_MOVIE_POSTER = "movie_poster";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";


        //Constants Value Of is movie favourite
        public static final int IS_FAVOURITE_FALSE = 0;

        public static final int IS_FAVOURITE_TRUE = 1;


    }


}
