package com.example.shams.moviestageone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shams on 14/03/18.
 */

public class Movies implements Parcelable {

    private Double voteAverage ;
    private String originalTitle ;
    private String posterPath ;
    private String releaseDate ;
    private String overview  ;
    private int movieId;


    public Movies(int movieId ,Double voteAverage, String originalTitle, String posterPath, String releaseDate, String overview) {
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.movieId = movieId;
    }

    private Movies(Parcel parcel){
        voteAverage = parcel.readDouble();
        originalTitle = parcel.readString();
        posterPath = parcel.readString();
        releaseDate = parcel.readString();
        overview = parcel.readString();
        movieId = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(voteAverage);
        dest.writeString(originalTitle);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeInt(movieId);
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
