package com.example.shams.moviestageone.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shams on 05/04/18.
 */

public class CustomMovieReview implements Parcelable {

    private String mAuthorName;
    private String mReviewContent;

    public CustomMovieReview(String mAuthorName, String mReviewContent) {
        this.mAuthorName = mAuthorName;
        this.mReviewContent = mReviewContent;
    }

    private CustomMovieReview(Parcel parcel){
        mAuthorName = parcel.readString();
        mReviewContent = parcel.readString();
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getmReviewContent() {
        return mReviewContent;
    }

    public void setmReviewContent(String mReviewContent) {
        this.mReviewContent = mReviewContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthorName);
        dest.writeString(mReviewContent);
    }

    public static final Parcelable.Creator<CustomMovieReview> CREATOR
            = new Parcelable.Creator<CustomMovieReview>() {
        @Override
        public CustomMovieReview createFromParcel(Parcel source) {
            return new CustomMovieReview(source);
        }

        @Override
        public CustomMovieReview[] newArray(int size) {
            return new CustomMovieReview[size];
        }
    };
}
