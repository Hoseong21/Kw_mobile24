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

public class Second2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second2);

        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);
        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);

        LinearLayout btnChina_1 = findViewById(R.id.btnChina_1);

        btnChina_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, third2.class);
                startActivity(intent);
            }
        });



        // 중식 버튼 클릭 이벤트
        btnRice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 일식 버튼 클릭 이벤트
        btnSushi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, Second3Activity.class);
                startActivity(intent);
            }
        });

        // 양식 버튼 클릭 이벤트
        btnSteak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, Second4Activity.class);
                startActivity(intent);
            }
        });

        // 햄버거 버튼 클릭 이벤트
        btnFast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, Second5Activity.class);
                startActivity(intent);
            }
        });

        // 카페 버튼 클릭 이벤트
        btnCafe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second2Activity.this, Second6Activity.class);
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
        TextView[] chinaFoodNames = new TextView[14];
        for (int i = 0; i < 14; i++) {
            String textViewID = "chinafoodname" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            chinaFoodNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor chinafoodnamecursor;
        if (num == 0) {
            chinafoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            chinafoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            chinafoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (chinafoodnamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 14) { // 최대 14개의 값만 처리
                    String nameValue = chinafoodnamecursor.getString(0); // 이름 가져오기
                    chinaFoodNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (chinafoodnamecursor.moveToNext());
        }
        chinafoodnamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] chinafoodRatings = {
                findViewById(R.id.chinafoodrating1), findViewById(R.id.chinafoodrating2), findViewById(R.id.chinafoodrating3),
                findViewById(R.id.chinafoodrating4), findViewById(R.id.chinafoodrating5), findViewById(R.id.chinafoodrating6),
                findViewById(R.id.chinafoodrating7), findViewById(R.id.chinafoodrating8), findViewById(R.id.chinafoodrating9),
                findViewById(R.id.chinafoodrating10), findViewById(R.id.chinafoodrating11), findViewById(R.id.chinafoodrating12),
                findViewById(R.id.chinafoodrating13), findViewById(R.id.chinafoodrating14)
        };

        // "중식" 분류의 별점 값을 14개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14";
        }

        Cursor chinafoodratingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (chinafoodratingcursor.moveToFirst()) {
            do {
                String ratingString = chinafoodratingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < chinafoodRatings.length) {
                    chinafoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (chinafoodratingcursor.moveToNext());
        }

        chinafoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[14];  // 이미지 URL을 저장할 배열
        Cursor chinafoodimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            chinafoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            chinafoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            chinafoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (chinafoodimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = chinafoodimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (chinafoodimageurlcursor.moveToNext());
            chinafoodimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.chinafoodpicture1, R.id.chinafoodpicture2, R.id.chinafoodpicture3, R.id.chinafoodpicture4, R.id.chinafoodpicture5,
                    R.id.chinafoodpicture6, R.id.chinafoodpicture7, R.id.chinafoodpicture8, R.id.chinafoodpicture9, R.id.chinafoodpicture10,
                    R.id.chinafoodpicture11, R.id.chinafoodpicture12, R.id.chinafoodpicture13, R.id.chinafoodpicture14
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
