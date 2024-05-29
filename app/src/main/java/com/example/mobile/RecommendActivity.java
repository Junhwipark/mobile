package com.example.mobile;
// RecommendActivity.java
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class RecommendActivity extends AppCompatActivity {
    private LinearLayout recommendLayout;
    private static final String API_KEY = "d2bfbfd363a792a5e30d3fcc521c68c7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        recommendLayout = findViewById(R.id.recommend);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayMovies(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // 실패 처리
            }
        });
    }

    private void displayMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            ImageView imageView = new ImageView(this);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(imageView);
            recommendLayout.addView(imageView);
        }
    }
}
