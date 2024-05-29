package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviePosterAdapter adapter;
    private List<String> posterUrls;

    private static final int REQUEST_CODE_SELECT_MOVIE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        posterUrls = new ArrayList<>();
        adapter = new MoviePosterAdapter(this, posterUrls, posterUrl -> {
            // 마이페이지에서는 선택된 영화를 처리할 필요가 없습니다.
        });
        recyclerView.setAdapter(adapter);

        Button selectMovieButton = findViewById(R.id.select_movie_button);
        selectMovieButton.setOnClickListener(v -> {
            Intent intent = new Intent(MypageActivity.this, MovieSelect.class); // MovieSelect가 영화 선택을 위한 Activity
            startActivityForResult(intent, REQUEST_CODE_SELECT_MOVIE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_MOVIE && resultCode == RESULT_OK) {
            String selectedPosterUrl = data.getStringExtra("selectedPosterUrl");
            if (selectedPosterUrl != null) {
                posterUrls.add(selectedPosterUrl);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
