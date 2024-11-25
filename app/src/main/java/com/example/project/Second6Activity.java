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

public class Second6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second6);

        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);;
        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);

        LinearLayout btnCafe_1 = findViewById(R.id.btnCafe_1);

        btnCafe_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, third6.class);
                startActivity(intent);
            }
        });

        // 한식 버튼 클릭 이벤트
        btnRice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        btnChina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, Second2Activity.class);
                startActivity(intent);
            }
        });

        // 일식 버튼 클릭 이벤트
        btnSushi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, Second3Activity.class);
                startActivity(intent);
            }
        });

        // 양식 버튼 클릭 이벤트
        btnSteak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, Second4Activity.class);
                startActivity(intent);
            }
        });

        // 햄버거 버튼 클릭 이벤트
        btnFast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second6Activity.this, Second5Activity.class);
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
        TextView[] cofeNames = new TextView[30];
        for (int i = 0; i < 30; i++) {
            String textViewID = "cofename" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            cofeNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor cofenamecursor;
        if (num == 0) {
            cofenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cofenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cofenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (cofenamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String nameValue = cofenamecursor.getString(0); // 이름 가져오기
                    cofeNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (cofenamecursor.moveToNext());
        }
        cofenamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] cofeRatings = {
                findViewById(R.id.coferating1), findViewById(R.id.coferating2), findViewById(R.id.coferating3),
                findViewById(R.id.coferating4), findViewById(R.id.coferating5), findViewById(R.id.coferating6),
                findViewById(R.id.coferating7), findViewById(R.id.coferating8), findViewById(R.id.coferating9),
                findViewById(R.id.coferating10), findViewById(R.id.coferating11), findViewById(R.id.coferating12),
                findViewById(R.id.coferating13), findViewById(R.id.coferating14), findViewById(R.id.coferating15),
                findViewById(R.id.coferating16), findViewById(R.id.coferating17), findViewById(R.id.coferating18),
                findViewById(R.id.coferating19), findViewById(R.id.coferating20), findViewById(R.id.coferating21),
                findViewById(R.id.coferating22), findViewById(R.id.coferating23), findViewById(R.id.coferating24),
                findViewById(R.id.coferating25), findViewById(R.id.coferating26), findViewById(R.id.coferating27),
                findViewById(R.id.coferating28), findViewById(R.id.coferating29), findViewById(R.id.coferating30)
        };

        // "카페/디저트" 분류의 별점 값을 30개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30";
        }

        Cursor coferatingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (coferatingcursor.moveToFirst()) {
            do {
                String ratingString = coferatingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < cofeRatings.length) {
                    cofeRatings[index].setRating(ratingValue);
                }
                index++;
            } while (coferatingcursor.moveToNext());
        }

        coferatingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[30];  // 이미지 URL을 저장할 배열
        Cursor cofeimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            cofeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cofeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cofeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (cofeimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = cofeimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (cofeimageurlcursor.moveToNext());
            cofeimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.cofepicture1, R.id.cofepicture2, R.id.cofepicture3, R.id.cofepicture4, R.id.cofepicture5,
                    R.id.cofepicture6, R.id.cofepicture7, R.id.cofepicture8, R.id.cofepicture9, R.id.cofepicture10,
                    R.id.cofepicture11, R.id.cofepicture12, R.id.cofepicture13, R.id.cofepicture14, R.id.cofepicture15,
                    R.id.cofepicture16, R.id.cofepicture17, R.id.cofepicture18, R.id.cofepicture19, R.id.cofepicture20,
                    R.id.cofepicture21, R.id.cofepicture22, R.id.cofepicture23, R.id.cofepicture24, R.id.cofepicture25,
                    R.id.cofepicture26, R.id.cofepicture27, R.id.cofepicture28, R.id.cofepicture29, R.id.cofepicture30
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