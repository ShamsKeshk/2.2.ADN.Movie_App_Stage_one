package com.example.shams.moviestageone.movie.trailers;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.Movies;
import com.example.shams.moviestageone.R;
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

    public void setmMovieReviewsList(List<MovieTrailer> mMovieReviewsList) {
        this.mMovieTrailersList = mMovieReviewsList;
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

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutResourceOfListItemId = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResourceOfListItemId, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        MovieTrailer currentMovieTrailer = getItem(position);
        Intent intent = ((Activity) context).getIntent();
        Movies movies = intent.getParcelableExtra(Constants.MOVIE_OBJECT_KEY);
        if (!TextUtils.isEmpty(currentMovieTrailer.getTrailerName()) &&
                currentMovieTrailer.getTrailerName() != null) {
            holder.mMovieTrailerNameTextView.setText(currentMovieTrailer.getTrailerName());
        }

        if (movies.getPosterPath() != null){
            Uri uri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                    .appendPath(Constants.IMAGE_SIZE_W185)
                    .appendEncodedPath(movies.getPosterPath())
                    .build();

            Picasso.get().load(uri).into(holder.mMovieTrailerImageView);
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