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

public class Second3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second3);

        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);
        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);

        LinearLayout btnjapan_1 = findViewById(R.id.btnSushi_1);

        btnjapan_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second3Activity.this, third3.class);
                startActivity(intent);
            }
        });

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
        TextView[] japanFoodNames = new TextView[13];
        for (int i = 0; i < 13; i++) {
            String textViewID = "japanfoodname" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            japanFoodNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor japanfoodnamecursor;
        if (num == 0) {
            japanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            japanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            japanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (japanfoodnamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 13) { // 최대 13개의 값만 처리
                    String nameValue = japanfoodnamecursor.getString(0); // 이름 가져오기
                    japanFoodNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (japanfoodnamecursor.moveToNext());
        }
        japanfoodnamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] japanfoodRatings = {
                findViewById(R.id.japanfoodrating1), findViewById(R.id.japanfoodrating2), findViewById(R.id.japanfoodrating3),
                findViewById(R.id.japanfoodrating4), findViewById(R.id.japanfoodrating5), findViewById(R.id.japanfoodrating6),
                findViewById(R.id.japanfoodrating7), findViewById(R.id.japanfoodrating8), findViewById(R.id.japanfoodrating9),
                findViewById(R.id.japanfoodrating10), findViewById(R.id.japanfoodrating11), findViewById(R.id.japanfoodrating12),
                findViewById(R.id.japanfoodrating13)
        };

        // "일식" 분류의 별점 값을 13개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13";
        }

        Cursor japanfoodratingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (japanfoodratingcursor.moveToFirst()) {
            do {
                String ratingString = japanfoodratingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < japanfoodRatings.length) {
                    japanfoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (japanfoodratingcursor.moveToNext());
        }

        japanfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[13];  // 이미지 URL을 저장할 배열
        Cursor japanfoodimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            japanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            japanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            japanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (japanfoodimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = japanfoodimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (japanfoodimageurlcursor.moveToNext());
            japanfoodimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.japanfoodpicture1, R.id.japanfoodpicture2, R.id.japanfoodpicture3, R.id.japanfoodpicture4, R.id.japanfoodpicture5,
                    R.id.japanfoodpicture6, R.id.japanfoodpicture7, R.id.japanfoodpicture8, R.id.japanfoodpicture9, R.id.japanfoodpicture10,
                    R.id.japanfoodpicture11, R.id.japanfoodpicture12, R.id.japanfoodpicture13
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
