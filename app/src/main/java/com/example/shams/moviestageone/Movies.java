package com.example.shams.moviestageone;

import java.io.Serializable;

/**
 * Created by shams on 14/03/18.
 */

public class Movies implements Serializable {

    private Double voteAverage ;
    private String originalTitle ;
    private String posterPath ;
    private String releaseDate ;
    private String overview  ;

    public Movies(Double voteAverage, String originalTitle, String posterPath, String releaseDate, String overview) {
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
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
}
