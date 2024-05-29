package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String date = "", time = "";
    private String x = "60", y = "127";
    private String weather = "";
    private TextView currentWeather, temperature, humidity, windSpeed;
    private ImageView weatherIcon;

    private RecyclerView recyclerView;
    private MoviePosterAdapter adapter;
    private List<String> posterUrls;
    private Set<String> favoriteMoviePosters;

    private static final int ACCOUNT_ID = 123; // 실제 사용자 ID로 변경
    private static final String API_KEY = "d2bfbfd363a792a5e30d3fcc521c68c7"; // TMDB API 키로 변경
    private static final String SESSION_ID = "your_session_id"; // 실제 세션 ID로 변경

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 여기에서 savedState를 savedInstanceState로 수정합니다.
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button main_button = findViewById(R.id.main_button);
        main_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        Button recommend_button = findViewById(R.id.recommend_button);
        recommend_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
            startActivity(intent);
        });

        // 현재 날씨 알림 ---------------------------------------------------------------
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        currentWeather = findViewById(R.id.currentWeather);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        windSpeed = findViewById(R.id.wind_speed);
        weatherIcon = findViewById(R.id.weather_icon);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        // 날짜, 시간의 형식 설정
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");

        // 현재 날짜를 받아오는 형식 설정 ex) 20221121
        String getDate = simpleDateFormat1.format(mDate);
        // 현재 시간를 받아오는 형식 설정, 시간만 가져오고 WeatherData의 timechange()를 사용하기 위해 시간만 가져오고 뒤에 00을 붙임 ex) 02 + "00"
        String getTime = simpleDateFormat2.format(mDate) + "00";
        String CurrentTime = simpleDateFormat2.format(mDate) + ":00";
        Log.d("date", getDate + getTime);
        // 현재 월 가져오기 봄 = 3월 ~ 5월 / 여름 = 6월 ~ 8월 / 가을 = 9월, 10월 / 겨울 = 11월 ~ 2월
        String getSeason = simpleDateFormat.format(mDate);

        WeatherData wd = new WeatherData();
        try {
            date = getDate;
            time = getTime;
            weather = wd.lookUpWeather(date, time, x, y);
        } catch (IOException e) {
            Log.i("THREE_ERROR1", e.getMessage());
        } catch (JSONException e) {
            Log.i("THREE_ERROR2", e.getMessage());
        }
        Log.d("현재날씨", weather);

        // return한 값을 " " 기준으로 자른 후 배열에 추가
        // array[0] = 구름의 양, array[1] = 강수 확률, array[2] = 기온, array[3] = 풍속, array[4] = 적설량, array[5] = 습도
        String[] weatherarray = weather.split(" ");
        for (int i = 0; i < weatherarray.length; i++) {
            Log.d("weather = ", i + " " + weatherarray[i]);
        }

        currentWeather.setText("현재 날씨: " + weatherarray[0]);
        temperature.setText("온도: " + weatherarray[2] + "°C");
        humidity.setText("습도: " + weatherarray[5] + "%");
        windSpeed.setText("풍속: " + weatherarray[3] + "m/s");

        // 날씨에 맞는 아이콘 설정 (Glide를 사용하여 이미지 로드)
        int weatherIconResId = getWeatherIconResource(weatherarray[0]);
        Glide.with(this).load(weatherIconResId).into(weatherIcon);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        posterUrls = new ArrayList<>();
        favoriteMoviePosters = new HashSet<>(); // Set to avoid duplicate posters
        adapter = new MoviePosterAdapter(this, posterUrls, this::onMovieSelected);
        recyclerView.setAdapter(adapter);

        // 좋아하는 영화 포스터 로드
        loadFavoriteMovies();

        Button myPageButton = findViewById(R.id.mypage_button);
        myPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            intent.putStringArrayListExtra("favoriteMoviePosters", new ArrayList<>(favoriteMoviePosters));
            startActivity(intent);
        });
    }

    private int getWeatherIconResource(String weatherCondition) {
        switch (weatherCondition) {
            case "맑음":
                return R.drawable.sunny;
            case "흐림":
                return R.drawable.cloudy;
            case "비":
                return R.drawable.rainy;
            case "눈":
                return R.drawable.snowy;
            default:
                return R.drawable.unknown;
        }
    }

    private void loadFavoriteMovies() {
        TMDBClient.getApi().getFavoriteMovies(ACCOUNT_ID, API_KEY, SESSION_ID).enqueue(new Callback<FavoriteMoviesResponse>() {
            @Override
            public void onResponse(Call<FavoriteMoviesResponse> call, Response<FavoriteMoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (FavoriteMoviesResponse.Movie movie : response.body().getResults()) {
                        posterUrls.add("https://image.tmdb.org/t/p/w500" + movie.getPosterPath());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FavoriteMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load favorite movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onMovieSelected(String posterUrl) {
        if (favoriteMoviePosters.contains(posterUrl)) {
            favoriteMoviePosters.remove(posterUrl);
        } else {
            favoriteMoviePosters.add(posterUrl);
        }
    }
}
