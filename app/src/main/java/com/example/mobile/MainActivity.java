package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import network.RetrofitClientInstance;
import network.WeatherService;
import model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView weatherTextView;

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

        weatherTextView = (TextView) findViewById(R.id.textView); // 날씨 정보를 표시할 TextView
        fetchWeatherData("Seoul"); // 서울의 날씨 정보를 가져옵니다.

        Button main_button = findViewById(R.id.main_button);

        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        Button mypage_button = findViewById(R.id.mypage_button);
        mypage_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            startActivity(intent);
        });

        Button recommend_button = findViewById(R.id.recommend_button);
        recommend_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
            startActivity(intent);
        });
    }

    private void fetchWeatherData(String city) {
        WeatherService service = RetrofitClientInstance.getRetrofitInstance().create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeather(city, "be30d0417182e4188259fd1a1bf395fe"); // 실제 API 키로 교체 필요

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    double tempInCelsius = weatherResponse.getMain().getTemp() - 273.15; // Kelvin to Celsius
                    weatherTextView.setText("현재 온도: " + String.format("%.1f°C", tempInCelsius));
                } else {
                    weatherTextView.setText("날씨 정보를 가져올 수 없습니다.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherTextView.setText("네트워크 오류가 발생했습니다.");
            }
        });
    }
}
