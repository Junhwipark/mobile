package com.example.mobile;
// Movie.java
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    // Getters
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }
}


