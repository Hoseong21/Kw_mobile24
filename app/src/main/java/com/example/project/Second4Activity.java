package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Second4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second4);


        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);
        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);

        LinearLayout btnSteak_1 = findViewById(R.id.btnSteak_1);

        btnSteak_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, third4.class);
                startActivity(intent);
            }
        });

        // 한식 버튼 클릭 이벤트
        btnRice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        btnChina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, Second2Activity.class);
                startActivity(intent);
            }
        });

        // 양식 버튼 클릭 이벤트
        btnSushi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, Second3Activity.class);
                startActivity(intent);
            }
        });

        // 햄버거 버튼 클릭 이벤트
        btnFast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, Second5Activity.class);
                startActivity(intent);
            }
        });

        // 카페 버튼 클릭 이벤트
        btnCafe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second4Activity.this, Second6Activity.class);
                startActivity(intent);
            }
        });
        // 거리순 버튼 클릭 이벤트
        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 1);
                rating_array(db, 1);
                pictures_array(db, 1);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2);
                rating_array(db, 2);
                pictures_array(db, 2);
            }
        });
        name_array(db, 0);
        rating_array(db, 0);
        pictures_array(db, 0);
    }
    private void name_array(SQLiteDatabase db, int num) {
        // TextView 배열 생성
        TextView[] westernFoodNames = new TextView[10];
        for (int i = 0; i < 10; i++) {
            String textViewID = "westernfoodname" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            westernFoodNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor westernfoodnamecursor;
        if (num == 0) {
            westernfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            westernfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            westernfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (westernfoodnamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 10) { // 최대 10개의 값만 처리
                    String nameValue = westernfoodnamecursor.getString(0); // 이름 가져오기
                    westernFoodNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (westernfoodnamecursor.moveToNext());
        }
        westernfoodnamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] westernfoodRatings = {
                findViewById(R.id.westernfoodrating1), findViewById(R.id.westernfoodrating2), findViewById(R.id.westernfoodrating3),
                findViewById(R.id.westernfoodrating4), findViewById(R.id.westernfoodrating5), findViewById(R.id.westernfoodrating6),
                findViewById(R.id.westernfoodrating7), findViewById(R.id.westernfoodrating8), findViewById(R.id.westernfoodrating9),
                findViewById(R.id.westernfoodrating10)
        };

        // "양식" 분류의 별점 값을 10개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10";
        }

        Cursor westernfoodratingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (westernfoodratingcursor.moveToFirst()) {
            do {
                String ratingString = westernfoodratingcursor.getString(0); // 별점 값을 문자열로 가져오기
                float ratingValue = 0;

                // "별점 없음" 체크 후 변환
                if (!"별점 없음".equals(ratingString)) {
                    try {
                        ratingValue = Float.parseFloat(ratingString); // 문자열을 float로 변환
                    } catch (NumberFormatException e) {
                        ratingValue = 0; // 변환 실패 시 기본값 0
                    }
                }

                // RatingBar에 값 설정
                if (index < westernfoodRatings.length) {
                    westernfoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (westernfoodratingcursor.moveToNext());
        }

        westernfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[10];  // 이미지 URL을 저장할 배열
        Cursor westernfoodimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            westernfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            westernfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            westernfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (westernfoodimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = westernfoodimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (westernfoodimageurlcursor.moveToNext());
            westernfoodimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.westernfoodpicture1, R.id.westernfoodpicture2, R.id.westernfoodpicture3, R.id.westernfoodpicture4, R.id.westernfoodpicture5,
                    R.id.westernfoodpicture6, R.id.westernfoodpicture7, R.id.westernfoodpicture8, R.id.westernfoodpicture9, R.id.westernfoodpicture10
            };

            // Glide를 사용하여 ImageView에 이미지 로드
            for (int i = 0; i < imageUrls.length; i++) {
                ImageView imageView = findViewById(imageViewIds[i]);
                Glide.with(this)
                        .load(imageUrls[i])
                        .into(imageView);
            }
        }
    }
}