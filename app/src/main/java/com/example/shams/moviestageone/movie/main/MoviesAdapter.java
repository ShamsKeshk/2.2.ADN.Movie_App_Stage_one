package com.example.shams.moviestageone.movie.main;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shams.moviestageone.Constants;
import com.example.shams.moviestageone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shams on 15/03/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.AdapterViewHolder> {

    private List<Movies> mMoviesList;
    private MovieListClickListener movieListClickListener;


    public MoviesAdapter(ArrayList<Movies> moviesArrayList, MovieListClickListener movieListClickListener) {
        mMoviesList = moviesArrayList;
        this.movieListClickListener = movieListClickListener;
    }

    public List<Movies> getmMoviesList() {
        return mMoviesList;
    }

    public void setmMoviesList(List<Movies> mMoviesList) {
        this.mMoviesList = mMoviesList;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        int size = mMoviesList.size();
        mMoviesList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public Movies getItem(int position) {
        return mMoviesList.get(position);
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutResourceOfListItemId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResourceOfListItemId, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Movies movie = getItem(position);

        Uri uri = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon()
                .appendPath(Constants.IMAGE_SIZE_W185)
                .appendEncodedPath(movie.getPosterPath())
                .build();

        Picasso.get().load(uri).into(holder.mMoviePosterImageView);

    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public interface MovieListClickListener {
        void onMovieListClickListener(int position);
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.iv_movie_poster)
        ImageView mMoviePosterImageView;

        public AdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieListClickListener.onMovieListClickListener(getAdapterPosition());
        }
    }


}
