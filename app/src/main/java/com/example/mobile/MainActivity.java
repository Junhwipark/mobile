package com.example.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout layouthome = (LinearLayout) findViewById(R.id.display);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.activity_home, layouthome, true);

        Button main_button = (Button) findViewById(R.id.main_button);
        main_button.setOnClickListener(v -> {
            layouthome.removeAllViews();
            layoutInflater.inflate(R.layout.activity_home, layouthome, true);
        });

        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//            startActivity(intent);
            layouthome.removeAllViews();
            layoutInflater.inflate(R.layout.activity_search, layouthome, true);
        });

        Button mypage_button = (Button) findViewById(R.id.mypage_button);
        mypage_button.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
//            startActivity(intent);
            layouthome.removeAllViews();
            View mypageView = layoutInflater.inflate(R.layout.activity_mypage, layouthome, true);
            initializeMypage(mypageView);
        });
    }

    private void initializeMypage(View mypageView) {
        ListView listViewFavorites = mypageView.findViewById(R.id.listViewFavorites);
        List<String> favoriteItems = new ArrayList<>();
        favoriteItems.add("좋아하는 영화: 인셉션");
        favoriteItems.add("좋아하는 감독: 크리스토퍼 놀란");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");
        favoriteItems.add("좋아하는 배우: 레오나르도 디카프리오");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteItems);
        listViewFavorites.setAdapter(adapter);
    }
}