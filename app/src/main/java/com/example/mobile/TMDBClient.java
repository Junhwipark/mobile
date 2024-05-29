package com.example.mobile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDBClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static TMDBApi api;

    public static TMDBApi getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(TMDBApi.class);
        }
        return api;
    }
}
