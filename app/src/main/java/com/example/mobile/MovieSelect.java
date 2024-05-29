package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MovieSelect extends AppCompatActivity implements MoviePosterAdapter.OnMovieSelectedListener {

    private RecyclerView recyclerView;
    private MoviePosterAdapter adapter;
    private List<String> posterUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieselect);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        posterUrls = new ArrayList<>();
        // 여기에서 posterUrls를 TMDB API를 사용하여 로드합니다.
        // 예를 들어, 인기 영화 포스터를 로드합니다.

        adapter = new MoviePosterAdapter(this, posterUrls, this);
        recyclerView.setAdapter(adapter);

        // 영화 포스터를 로드하는 로직을 추가합니다.
        loadPopularMovies();
    }

    private void loadPopularMovies() {
        // TMDB API를 사용하여 인기 영화를 로드하고 posterUrls에 추가합니다.
        // 예시:
        // posterUrls.add("https://image.tmdb.org/t/p/w500" + movie.getPosterPath());
        // adapter.notifyDataSetChanged();
    }

    @Override
    public void onMovieSelected(String posterUrl) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedPosterUrl", posterUrl);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
