package com.example.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            layoutInflater.inflate(R.layout.activity_mypage, layouthome, true);
        });
    }
}