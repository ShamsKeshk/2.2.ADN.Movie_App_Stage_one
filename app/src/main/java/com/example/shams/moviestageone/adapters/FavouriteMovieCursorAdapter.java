package com.example.shams.moviestageone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.database.FMContract.MoviesEntry;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteMovieCursorAdapter extends CursorAdapter {

    Context context;

    public FavouriteMovieCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.favourite_list_item, viewGroup, false);
        FavouriteViewHolder viewHolder = new FavouriteViewHolder(v);
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final FavouriteViewHolder viewHolder = (FavouriteViewHolder) view.getTag();

        String movieName = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_NAME);
        String imageUri = getStringFromCursor(cursor, MoviesEntry.COLUMN_MOVIE_POSTER);

        if (TextUtils.isEmpty(movieName) && movieName == null) {
            viewHolder.favouriteMovieTextView.setText(context.getString(R.string.movie_name_not_provided));
        } else {
            viewHolder.favouriteMovieTextView.setText(movieName);
        }

        Uri uri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                .appendPath(Constants.IMAGE_SIZE_W185)
                .appendEncodedPath(imageUri)
                .build();

        if (uri == null) {
            Picasso.get().load(R.drawable.no_image).into(viewHolder.favouriteMovieImageView);
        } else {
            Picasso.get().load(uri).into(viewHolder.favouriteMovieImageView);
        }
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public class FavouriteViewHolder {

        @BindView(R.id.iv_favourite_movie_image_id)
        ImageView favouriteMovieImageView;
        @BindView(R.id.tv_favourite_movie_title_id)
        TextView favouriteMovieTextView;

        public FavouriteViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
