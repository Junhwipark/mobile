package com.example.mobile;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieImagesResponse {

    @SerializedName("posters")
    private List<Image> posters;

    public List<Image> getPosters() {
        return posters;
    }

    public static class Image {
        @SerializedName("file_path")
        private String filePath;

        public String getFilePath() {
            return filePath;
        }
    }
}
