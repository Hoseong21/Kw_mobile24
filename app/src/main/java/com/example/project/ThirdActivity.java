package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third); // XML 레이아웃을 지정 (예: activity_third.xml)

        // 버튼 찾기
        Button backButton = findViewById(R.id.backButton);

        // 버튼 클릭 리스너 설정
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료하고 이전 화면으로 돌아감
            }
        });
        // 전달받은 데이터 가져오기
        String foodTel = getIntent().getStringExtra("foodTel");
        String foodTime = getIntent().getStringExtra("foodTime");
        String foodAddress = getIntent().getStringExtra("foodAddress");
        String foodMenus = getIntent().getStringExtra("foodMenus");
        String imageUrls = getIntent().getStringExtra("imageUrls");
        float foodRatings = getIntent().getFloatExtra("foodRatings", 0f); // 기본값 0f
        String foodNames = getIntent().getStringExtra("foodNames");

        // UI에 데이터 표시
        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(foodNames);

        TextView menuTextView = findViewById(R.id.menuTextView);
        menuTextView.setText(foodMenus);
        
        TextView telTextView = findViewById(R.id.telTextView);
        telTextView.setText(foodTel);

        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText(foodAddress);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(foodRatings);

        TextView timeTextView = findViewById(R.id.timeTextView);
        timeTextView.setText(foodTime);


        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageUrls)
                .into(imageView);
    }
}

