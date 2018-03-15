package com.example.shams.moviestageone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shams on 15/03/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<Movies> mMoviesList;



    public MoviesAdapter(Context context, ArrayList<Movies> moviesArrayList){
        mContext = context;
        mMoviesList = moviesArrayList;
    }

    public List<Movies> getmMoviesList() {
        return mMoviesList;
    }

    public void setmMoviesList(List<Movies> mMoviesList) {
        this.mMoviesList = mMoviesList;
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        int size = mMoviesList.size();
        mMoviesList.clear();
        notifyItemRangeRemoved(0 , size);
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutResourceOfListItemId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResourceOfListItemId , parent , false);
        AdapterViewHolder adapterViewHolder = new AdapterViewHolder(view);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Movies movie = mMoviesList.get(position);
        if (movie.getOriginalTitle() != null){
            holder.mMovieTitleTextView.setText(movie.getOriginalTitle());
        }else {
            holder.mMovieTitleTextView.setText("Not Provided");
        }

    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_title)
        TextView mMovieTitleTextView;

        public AdapterViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }


}
