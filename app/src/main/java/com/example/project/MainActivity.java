package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 김밥천국 버튼 클릭 이벤트
        LinearLayout btnKimbap = findViewById(R.id.btnrice);
        btnKimbap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KimbapActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        LinearLayout btnChina = findViewById(R.id.btnchina);
        btnChina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChinaActivity.class);
                startActivity(intent);
            }
        });
        // 양식 버튼 클릭 이벤트
        LinearLayout btnSteak = findViewById(R.id.btnsteak);
        btnSteak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SteakActivity.class);
                startActivity(intent);
            }
        });
        // 일식 버튼 클릭 이벤트
        LinearLayout btnsushi = findViewById(R.id.btnsushi);
        btnsushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SushiActivity.class);
                startActivity(intent);
            }
        });
        // 햄버거 버튼 클릭 이벤트
        LinearLayout btnfastfood = findViewById(R.id.btnfastfood);
        btnfastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FastfoodActivity.class);
                startActivity(intent);
            }
        });
        // 카페 버튼 클릭 이벤트
        LinearLayout btncoffee = findViewById(R.id.btncoffee);
        btncoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoffeeActivity.class);
                startActivity(intent);
            }
        });
    }

}

