package com.example.mobile;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FavoriteMoviesResponse {

    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public static class Movie {
        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("poster_path")
        private String posterPath;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getPosterPath() {
            return posterPath;
        }
    }
}
