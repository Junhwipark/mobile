package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends AppCompatActivity {
    ListView listViewFavorites;
    ArrayAdapter<String> adapter;
    List<String> favoriteItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mypage);

        // Set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mypage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ListView and Adapter
        listViewFavorites = findViewById(R.id.listViewFavorites);
        favoriteItems = new ArrayList<>();
        favoriteItems.add("좋아하는 영화: 인셉션");
        favoriteItems.add("좋아하는 감독: 크리스토퍼 놀란");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteItems);
        listViewFavorites.setAdapter(adapter);

        Log.d("MypageActivity", "ListView: " + listViewFavorites);
        Log.d("MypageActivity", "Adapter: " + adapter);
    }
}
