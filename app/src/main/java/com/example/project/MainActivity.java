package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomRouletteView rouletteView = findViewById(R.id.roulette_view);
        List<String> data = Arrays.asList("한식", "중식", "양식", "일식", "패/분", "카페");
        int[] colors = {
                R.color.red,
                R.color.blue,
                R.color.green,
                R.color.yellow,
                R.color.purple_200,
                R.color.teal_200
        };
        rouletteView.setRouletteData(data, colors);

        // 회전 리스너 설정
        rouletteView.setRotateListener(new CustomRouletteView.RotateListener() {
            @Override
            public void onRotateStart() {
                // 회전 시작 시 동작
                System.out.println("룰렛 회전 시작!");
            }

            @Override
            public void onRotateEnd(int resultIndex) {
                // 회전 종료 후 결과
                System.out.println("룰렛 결과 인덱스: " + resultIndex);
                // 예: 결과에 따라 특정 동작 수행
            }
        });

        // 버튼 클릭으로 랜덤 회전 시작
        findViewById(R.id.spin_button).setOnClickListener(v -> {
            // 랜덤 각도 생성 (360 ~ 3600 사이, 즉 최소 1회전 ~ 최대 10회전)
            Random random = new Random();
            int randomDegrees = 2880 + random.nextInt(36)*10; // 최소 2880도 + 최대 3240도 (9회전)

            // 랜덤 회전 각도로 룰렛 회전
            rouletteView.rotateRoulette(randomDegrees);
        });


        // 김밥천국 버튼 클릭 이벤트
        LinearLayout btnKimbap = findViewById(R.id.btnrice);
        btnKimbap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        LinearLayout btnChina = findViewById(R.id.btnchina);
        btnChina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second2Activity.class);
                startActivity(intent);
            }
        });
        // 양식 버튼 클릭 이벤트
        LinearLayout btnSteak = findViewById(R.id.btnsteak);
        btnSteak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second4Activity.class);
                startActivity(intent);
            }
        });
        // 일식 버튼 클릭 이벤트
        LinearLayout btnsushi = findViewById(R.id.btnsushi);
        btnsushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second3Activity.class);
                startActivity(intent);
            }
        });
        // 햄버거 버튼 클릭 이벤트
        LinearLayout btnfastfood = findViewById(R.id.btnfastfood);
        btnfastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second5Activity.class);
                startActivity(intent);
            }
        });
        // 카페 버튼 클릭 이벤트
        LinearLayout btncoffee = findViewById(R.id.btncoffee);
        btncoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Second6Activity.class);
                startActivity(intent);
            }
        });
    }

}