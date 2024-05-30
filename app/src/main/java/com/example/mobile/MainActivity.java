package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String date = "", time = "";
    private String x = "76", y = "121";
    private String weather = "";
    private TextView currentWeather, temperature, humidity, windSpeed;
    private ImageView weatherIcon;
    private LinearLayout movieContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");

        String getDate = simpleDateFormat1.format(mDate);
        String getTime = simpleDateFormat2.format(mDate) + "00";
        String CurrentTime = simpleDateFormat2.format(mDate) + ":00";
        Log.d("date", getDate + getTime);
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

        String[] weatherarray = weather.split(" ");
        for (int i = 0; i < weatherarray.length; i++) {
            Log.d("weather = ", i + " " + weatherarray[i]);
        }

        currentWeather.setText("현재 날씨: " + weatherarray[0]);
        temperature.setText("온도: " + weatherarray[2]);
        humidity.setText("습도: " + weatherarray[5]);
        windSpeed.setText("풍속: " + weatherarray[3]);

        int weatherIconResId = getWeatherIconResource(weatherarray[0]);
        Glide.with(this).load(weatherIconResId).into(weatherIcon);

        // 영화 포스터를 표시하는 LinearLayout 초기화
        movieContainer = findViewById(R.id.recommend_layout);

        // 영화 데이터를 가져와서 LinearLayout에 설정
        fetchMovies();
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

    private void fetchMovies() {
        String apiKey = "d2bfbfd363a792a5e30d3fcc521c68c7";  // TMDB API 키 사용
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(apiKey);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    displayMovies(movies);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_movie, movieContainer, false);
            String posterUrl = "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath();
            Glide.with(this).load(posterUrl).into(imageView);
            movieContainer.addView(imageView);
        }
    }
}
