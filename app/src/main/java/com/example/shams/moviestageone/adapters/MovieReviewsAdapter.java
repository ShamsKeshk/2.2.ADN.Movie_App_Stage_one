package com.example.shams.moviestageone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shams.moviestageone.R;
import com.example.shams.moviestageone.movie.CustomMovieReview;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shams on 05/04/18.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.AdapterViewHolder> {

    private List<CustomMovieReview> mMovieReviewsList;
    private Context context;


    public MovieReviewsAdapter(ArrayList<CustomMovieReview> movieReviewsArrayList) {
        mMovieReviewsList = movieReviewsArrayList;
    }

    public List<CustomMovieReview> getmMovieReviewsList() {
        return mMovieReviewsList;
    }

    public void setmMovieReviewsList(List<CustomMovieReview> mMovieReviewsList) {
        this.mMovieReviewsList = mMovieReviewsList;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        int size = mMovieReviewsList.size();
        mMovieReviewsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    private CustomMovieReview getItem(int position) {
        return mMovieReviewsList.get(position);
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutResourceOfListItemId = R.layout.reviews_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResourceOfListItemId, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        CustomMovieReview currentMovieReview = getItem(position);
        if (!TextUtils.isEmpty(currentMovieReview.getmReviewContent()) &&
                currentMovieReview.getmReviewContent() != null) {
            holder.mMovieReviewExpandTextView.setText(currentMovieReview.getmReviewContent());
        } else {
            holder.mMovieReviewExpandTextView.setText(context.getString(R.string.no_reviews_are_provided));
        }

        if (!TextUtils.isEmpty(currentMovieReview.getmAuthorName()) &&
                currentMovieReview.getmAuthorName() != null) {
            holder.mMovieReviewAuthorTv.setText(currentMovieReview.getmAuthorName());
        } else {
            holder.mMovieReviewExpandTextView.setText(context.getString(R.string.author_name_not_provided));
        }

    }

    @Override
    public int getItemCount() {
        return mMovieReviewsList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.expandable_text)
        TextView mMovieReviewTextView;
        @BindView(R.id.expand_text_view)
        ExpandableTextView mMovieReviewExpandTextView;
        @BindView(R.id.tv_movie_review_author)
        TextView mMovieReviewAuthorTv;

        public AdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
