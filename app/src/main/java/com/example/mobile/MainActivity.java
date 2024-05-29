package com.example.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String date = "", time = "";
    private String x = "76", y = "121";
    private String weather = "";
    private TextView currentWeather, temperature, humidity, windSpeed;
    private ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button main_button = (Button) findViewById(R.id.main_button);

        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        Button mypage_button = (Button) findViewById(R.id.mypage_button);
        mypage_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            startActivity(intent);
        });
        Button recommend_button = (Button) findViewById(R.id.recommend_button);
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
        temperature.setText("온도: " + weatherarray[2]);
        humidity.setText("습도: " + weatherarray[5]);
        windSpeed.setText("풍속: " + weatherarray[3]);

        // 날씨에 맞는 아이콘 설정 (Glide를 사용하여 이미지 로드)
        int weatherIconResId = getWeatherIconResource(weatherarray[0]);
        Glide.with(this).load(weatherIconResId).into(weatherIcon);
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
}
