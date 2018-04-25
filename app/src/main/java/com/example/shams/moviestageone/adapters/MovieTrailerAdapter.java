package com.example.shams.moviestageone.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.movie.MovieTrailer;
import com.example.shams.moviestageone.movie.Movies;
import com.example.shams.moviestageone.ui.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shams on 05/04/18.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.AdapterViewHolder> {

    private List<MovieTrailer> mMovieTrailersList;
    private Context context;

    private TrailerListClickListener trailerListClickListener;


    public interface TrailerListClickListener{
        void onTrailerListClickListener(int position);
    }


    public MovieTrailerAdapter(ArrayList<MovieTrailer> movieTrailersArrayList,TrailerListClickListener trailerListClickListener) {
        mMovieTrailersList = movieTrailersArrayList;
        this.trailerListClickListener = trailerListClickListener;
    }

    public List<MovieTrailer> getmMovieReviewsList() {
        return mMovieTrailersList;
    }

    public void setmMovieTrailersList(List<MovieTrailer> mMovieTrailersList) {
        this.mMovieTrailersList = mMovieTrailersList;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        int size = mMovieTrailersList.size();
        mMovieTrailersList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public MovieTrailer getItem(int position) {
        return mMovieTrailersList.get(position);
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutResourceOfListItemId = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResourceOfListItemId, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        MovieTrailer currentMovieTrailer = getItem(position);
        Intent intent = ((Activity) context).getIntent();
        Movies currentMovie;
        if (MovieDetailsActivity.currentFavouriteMovie != null) {
            currentMovie = MovieDetailsActivity.currentFavouriteMovie;
        } else {
            currentMovie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_KEY);
        }

        if (!TextUtils.isEmpty(currentMovieTrailer.getTrailerName()) &&
                currentMovieTrailer.getTrailerName() != null) {
            holder.mMovieTrailerNameTextView.setText(currentMovieTrailer.getTrailerName());
        } else {
            holder.mMovieTrailerNameTextView.setText(context.getString(R.string.movie_name_not_provided));
        }

        if (currentMovie.getPosterPath() != null) {
            Uri uri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                    .appendPath(Constants.IMAGE_SIZE_W185)
                    .appendEncodedPath(currentMovie.getPosterPath())
                    .build();

            Picasso.get().load(uri).into(holder.mMovieTrailerImageView);
        } else {
            Picasso.get().load(R.drawable.no_image).into(holder.mMovieTrailerImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mMovieTrailersList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.tv_trailer_title_text_view_id)
        TextView mMovieTrailerNameTextView;

        @BindView(R.id.iv_trailer_image_view_id)
        ImageView mMovieTrailerImageView;

        public AdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            trailerListClickListener.onTrailerListClickListener(getAdapterPosition());
        }
    }
}