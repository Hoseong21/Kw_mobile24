package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Second3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second3);

        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);

        // 한식 버튼 클릭 이벤트
        btnRice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        btnChina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, Second2Activity.class);
                startActivity(intent);
            }
        });

        // 양식 버튼 클릭 이벤트
        btnSteak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, Second4Activity.class);
                startActivity(intent);
            }
        });

        // 햄버거 버튼 클릭 이벤트
        btnFast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, Second5Activity.class);
                startActivity(intent);
            }
        });

        // 카페 버튼 클릭 이벤트
        btnCafe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, Second6Activity.class);
                startActivity(intent);
            }
        });
    }
}