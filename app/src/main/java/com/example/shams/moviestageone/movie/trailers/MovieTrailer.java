package com.example.shams.moviestageone.movie.trailers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shams on 05/04/18.
 */

public class MovieTrailer implements Parcelable {

    String trailerKey ;
    String trailerName;

    public MovieTrailer(String trailerKey, String trailerName) {
        this.trailerKey = trailerKey;
        this.trailerName = trailerName;
    }

    public MovieTrailer(Parcel parcel){
        trailerKey = parcel.readString();
        trailerName = parcel.readString();
    }

    public String getTrailerKey() {
        return trailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trailerKey);
        dest.writeString(trailerName);
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR
            = new Parcelable.Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}
