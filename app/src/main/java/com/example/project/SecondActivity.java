package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);

        TextView koreanfoodname1 = findViewById(R.id.koreanfoodname1);
        TextView koreanfoodname2 = findViewById(R.id.koreanfoodname2);
        TextView koreanfoodname3 = findViewById(R.id.koreanfoodname3);
        TextView koreanfoodname4 = findViewById(R.id.koreanfoodname4);
        TextView koreanfoodname5 = findViewById(R.id.koreanfoodname5);
        TextView koreanfoodname6 = findViewById(R.id.koreanfoodname6);

        RatingBar koreanfoodRating1 = findViewById(R.id.koreanfoodrating1);
        RatingBar koreanfoodRating2 = findViewById(R.id.koreanfoodrating2);
        RatingBar koreanfoodRating3 = findViewById(R.id.koreanfoodrating3);
        RatingBar koreanfoodRating4 = findViewById(R.id.koreanfoodrating4);
        RatingBar koreanfoodRating5 = findViewById(R.id.koreanfoodrating5);
        RatingBar koreanfoodRating6 = findViewById(R.id.koreanfoodrating6);

        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);


        LinearLayout btnRice_1 = findViewById(R.id.btnRice_1);


        btnRice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, third.class);
                startActivity(intent);
            }
        });


        // 중식 버튼 클릭 이벤트
        btnChina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Second2Activity.class);
                startActivity(intent);
            }
        });

        // 일식 버튼 클릭 이벤트
        btnSushi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Second3Activity.class);
                startActivity(intent);
            }
        });

        // 양식 버튼 클릭 이벤트
        btnSteak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Second4Activity.class);
                startActivity(intent);
            }
        });

        // 햄버거 버튼 클릭 이벤트
        btnFast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Second5Activity.class);
                startActivity(intent);
            }
        });

        // 카페 버튼 클릭 이벤트
        btnCafe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, Second6Activity.class);
                startActivity(intent);
            }
        });
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // "분류" 컬럼의 값을 가져오기
        String nameValue1 = "";
        String nameValue2 = "";
        String nameValue3 = "";
        String nameValue4 = "";
        String nameValue5 = "";
        String nameValue6 = "";
        Cursor koreanfoodnamecursor = db.rawQuery("SELECT 이름 FROM crawling WHERE 분류 = '한식' LIMIT 6", null);

        // 커서가 첫 번째 항목으로 이동한 후 반복하여 각 별점 값을 가져오기
        int index = 0;  // 인덱스를 통해 각 RatingBar에 값을 설정
        if (koreanfoodnamecursor.moveToFirst()) {
            do {
                // 커서에서 별점 값을 가져와서 각 변수에 할당
                switch (index) {
                    case 0:
                        nameValue1 = koreanfoodnamecursor.getString(0);
                        break;
                    case 1:
                        nameValue2 = koreanfoodnamecursor.getString(0);
                        break;
                    case 2:
                        nameValue3 = koreanfoodnamecursor.getString(0);
                        break;
                    case 3:
                        nameValue4 = koreanfoodnamecursor.getString(0);
                        break;
                    case 4:
                        nameValue5 = koreanfoodnamecursor.getString(0);
                        break;
                    case 5:
                        nameValue6 = koreanfoodnamecursor.getString(0);
                        break;
                }
                index++;
            } while (koreanfoodnamecursor.moveToNext());  // 다음 항목으로 이동
        }
        // 첫 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToFirst()) {
//            Category1 = koreanfoodnamecursor.getString(0);
//        }
//
//        // 두 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToNext()) {
//            Category2 = koreanfoodnamecursor.getString(0);
//        }
//        // 세 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToNext()) {
//            Category3 = koreanfoodnamecursor.getString(0);
//        }
//        // 네 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToNext()) {
//            Category4 = koreanfoodnamecursor.getString(0);
//        }
//        // 다섯 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToNext()) {
//            Category5 = koreanfoodnamecursor.getString(0);
//        }
//        // 여섯 번째 값 가져오기
//        if (koreanfoodnamecursor.moveToNext()) {
//            Category6 = koreanfoodnamecursor.getString(0);
//        }

        koreanfoodnamecursor.close();

        // TextView에 값 설정
        koreanfoodname1.setText(nameValue1);
        koreanfoodname2.setText(nameValue2);
        koreanfoodname3.setText(nameValue3);
        koreanfoodname4.setText(nameValue4);
        koreanfoodname5.setText(nameValue5);
        koreanfoodname6.setText(nameValue6);

        // DB에서 "한식" 분류의 첫 번째 별점 값 가져오기
        float ratingValue1 = 0;  // 기본값 (0)
        float ratingValue2 = 0;
        float ratingValue3 = 0;
        float ratingValue4 = 0;
        float ratingValue5 = 0;
        float ratingValue6 = 0;
        Cursor koreanfoodratingcursor = db.rawQuery("SELECT 별점 FROM crawling WHERE 분류 = '한식' LIMIT 6", null);


        index = 0;
        if (koreanfoodratingcursor.moveToFirst()) {
            do {
                switch (index) {
                    case 0:
                        ratingValue1 = koreanfoodratingcursor.getFloat(0);
                        break;
                    case 1:
                        ratingValue2 = koreanfoodratingcursor.getFloat(0);
                        break;
                    case 2:
                        ratingValue3 = koreanfoodratingcursor.getFloat(0);
                        break;
                    case 3:
                        ratingValue4 = koreanfoodratingcursor.getFloat(0);
                        break;
                    case 4:
                        ratingValue5 = koreanfoodratingcursor.getFloat(0);
                        break;
                    case 5:
                        ratingValue6 = koreanfoodratingcursor.getFloat(0);
                        break;
                }
                index++;
            } while (koreanfoodratingcursor.moveToNext());
        }
        koreanfoodratingcursor.close();

        // RatingBar에 각 별점 값 설정
        koreanfoodRating1.setRating(ratingValue1);
        koreanfoodRating2.setRating(ratingValue2);
        koreanfoodRating3.setRating(ratingValue3);
        koreanfoodRating4.setRating(ratingValue4);
        koreanfoodRating5.setRating(ratingValue5);
        koreanfoodRating6.setRating(ratingValue6);

        // DB에서 "이미지 url" 값을 가져오기
        String imageUrl1 = "";
        String imageUrl2 = "";
        String imageUrl3 = "";
        String imageUrl4 = "";
        String imageUrl5 = "";
        String imageUrl6 = "";
        Cursor koreanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM crawling WHERE 분류 = '한식' LIMIT 6", null);

        index = 0;
        if (koreanfoodimageurlcursor.moveToFirst()) {
            do {
                switch (index) {
                    case 0:
                        imageUrl1 = koreanfoodimageurlcursor.getString(0);
                        break;
                    case 1:
                        imageUrl2 = koreanfoodimageurlcursor.getString(0);
                        break;
                    case 2:
                        imageUrl3 = koreanfoodimageurlcursor.getString(0);
                        break;
                    case 3:
                        imageUrl4 = koreanfoodimageurlcursor.getString(0);
                        break;
                    case 4:
                        imageUrl5 = koreanfoodimageurlcursor.getString(0);
                        break;
                    case 5:
                        imageUrl6 = koreanfoodimageurlcursor.getString(0);
                        break;
                }
                index++;
            } while (koreanfoodimageurlcursor.moveToNext());


            // 첫 번째 레코드가 있으면 이미지 URL 가져오기
//        if (koreanfoodimageurlcursor.moveToFirst()) {
//            imageUrl1 = koreanfoodimageurlcursor.getString(koreanfoodimageurlcursor.getColumnIndex("이미지 url"));
//        }
            koreanfoodimageurlcursor.close();

            // Glide를 사용하여 이미지 URL을 ImageView에 로드
            ImageView koreanfoodImageView1 = findViewById(R.id.koreanfoodpicture1);
            ImageView koreanfoodImageView2 = findViewById(R.id.koreanfoodpicture2);
            ImageView koreanfoodImageView3 = findViewById(R.id.koreanfoodpicture3);
            ImageView koreanfoodImageView4 = findViewById(R.id.koreanfoodpicture4);
            ImageView koreanfoodImageView5 = findViewById(R.id.koreanfoodpicture5);
            ImageView koreanfoodImageView6 = findViewById(R.id.koreanfoodpicture6);
            Glide.with(this)
                    .load(imageUrl1)  // 이미지 URL을 로드
                    .into(koreanfoodImageView1);  // ImageView에 설정
            Glide.with(this)
                    .load(imageUrl2)  // 이미지 URL을 로드
                    .into(koreanfoodImageView2);  // ImageView에 설정
            Glide.with(this)
                    .load(imageUrl3)  // 이미지 URL을 로드
                    .into(koreanfoodImageView3);  // ImageView에 설정
            Glide.with(this)
                    .load(imageUrl4)  // 이미지 URL을 로드
                    .into(koreanfoodImageView4);  // ImageView에 설정
            Glide.with(this)
                    .load(imageUrl5)  // 이미지 URL을 로드
                    .into(koreanfoodImageView5);  // ImageView에 설정
            Glide.with(this)
                    .load(imageUrl6)  // 이미지 URL을 로드
                    .into(koreanfoodImageView6);  // ImageView에 설정
        }
    }
}
