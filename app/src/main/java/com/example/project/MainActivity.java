package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // main.xml 레이아웃 연결

        // 다음 화면으로 이동하는 버튼
        Button btnNext = findViewById(R.id.btnNext);

        // 버튼 클릭 이벤트
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SecondActivity로 이동
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // "분류" 컬럼의 첫 번째 값을 가져오기
        String firstCategory = "";
        Cursor cursor = db.rawQuery("SELECT 분류 FROM crawling LIMIT 1", null);
        if (cursor.moveToFirst()) {
            firstCategory = cursor.getString(0); // 첫 번째 값
        }
        cursor.close();

        // TextView에 값 설정
        TextView textView = findViewById(R.id.asdf);
        textView.setText(firstCategory);
    }
}