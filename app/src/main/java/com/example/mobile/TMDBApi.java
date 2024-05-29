package com.example.mobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApi {
    @GET("account/{account_id}/favorite/movies")
    Call<FavoriteMoviesResponse> getFavoriteMovies(
            @Path("account_id") int accountId,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId
    );

    @GET("movie/{movie_id}/images")
    Call<MovieImagesResponse> getMovieImages(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );
}
