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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);
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
        TextView[] koreanFoodNames = new TextView[51];
        for (int i = 0; i < 51; i++) {
            String textViewID = "koreanfoodname" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            koreanFoodNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor koreanfoodnamecursor;
        if (num == 0) {
            koreanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            koreanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            koreanfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (koreanfoodnamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 51) { // 최대 51개의 값만 처리
                    String nameValue = koreanfoodnamecursor.getString(0); // 이름 가져오기
                    koreanFoodNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (koreanfoodnamecursor.moveToNext());
        }
        koreanfoodnamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] koreanfoodRatings = {
                findViewById(R.id.koreanfoodrating1), findViewById(R.id.koreanfoodrating2), findViewById(R.id.koreanfoodrating3),
                findViewById(R.id.koreanfoodrating4), findViewById(R.id.koreanfoodrating5), findViewById(R.id.koreanfoodrating6),
                findViewById(R.id.koreanfoodrating7), findViewById(R.id.koreanfoodrating8), findViewById(R.id.koreanfoodrating9),
                findViewById(R.id.koreanfoodrating10), findViewById(R.id.koreanfoodrating11), findViewById(R.id.koreanfoodrating12),
                findViewById(R.id.koreanfoodrating13), findViewById(R.id.koreanfoodrating14), findViewById(R.id.koreanfoodrating15),
                findViewById(R.id.koreanfoodrating16), findViewById(R.id.koreanfoodrating17), findViewById(R.id.koreanfoodrating18),
                findViewById(R.id.koreanfoodrating19), findViewById(R.id.koreanfoodrating20), findViewById(R.id.koreanfoodrating21),
                findViewById(R.id.koreanfoodrating22), findViewById(R.id.koreanfoodrating23), findViewById(R.id.koreanfoodrating24),
                findViewById(R.id.koreanfoodrating25), findViewById(R.id.koreanfoodrating26), findViewById(R.id.koreanfoodrating27),
                findViewById(R.id.koreanfoodrating28), findViewById(R.id.koreanfoodrating29), findViewById(R.id.koreanfoodrating30),
                findViewById(R.id.koreanfoodrating31), findViewById(R.id.koreanfoodrating32), findViewById(R.id.koreanfoodrating33),
                findViewById(R.id.koreanfoodrating34), findViewById(R.id.koreanfoodrating35), findViewById(R.id.koreanfoodrating36),
                findViewById(R.id.koreanfoodrating37), findViewById(R.id.koreanfoodrating38), findViewById(R.id.koreanfoodrating39),
                findViewById(R.id.koreanfoodrating40), findViewById(R.id.koreanfoodrating41), findViewById(R.id.koreanfoodrating42),
                findViewById(R.id.koreanfoodrating43), findViewById(R.id.koreanfoodrating44), findViewById(R.id.koreanfoodrating45),
                findViewById(R.id.koreanfoodrating46), findViewById(R.id.koreanfoodrating47), findViewById(R.id.koreanfoodrating48),
                findViewById(R.id.koreanfoodrating49), findViewById(R.id.koreanfoodrating50), findViewById(R.id.koreanfoodrating51)
        };

        // "한식" 분류의 별점 값을 51개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51";
        }

        Cursor koreanfoodratingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (koreanfoodratingcursor.moveToFirst()) {
            do {
                String ratingString = koreanfoodratingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < koreanfoodRatings.length) {
                    koreanfoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (koreanfoodratingcursor.moveToNext());
        }

        koreanfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[51];  // 이미지 URL을 저장할 배열
        Cursor koreanfoodimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            koreanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            koreanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            koreanfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (koreanfoodimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = koreanfoodimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (koreanfoodimageurlcursor.moveToNext());
            koreanfoodimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.koreanfoodpicture1, R.id.koreanfoodpicture2, R.id.koreanfoodpicture3, R.id.koreanfoodpicture4, R.id.koreanfoodpicture5,
                    R.id.koreanfoodpicture6, R.id.koreanfoodpicture7, R.id.koreanfoodpicture8, R.id.koreanfoodpicture9, R.id.koreanfoodpicture10,
                    R.id.koreanfoodpicture11, R.id.koreanfoodpicture12, R.id.koreanfoodpicture13, R.id.koreanfoodpicture14, R.id.koreanfoodpicture15,
                    R.id.koreanfoodpicture16, R.id.koreanfoodpicture17, R.id.koreanfoodpicture18, R.id.koreanfoodpicture19, R.id.koreanfoodpicture20,
                    R.id.koreanfoodpicture21, R.id.koreanfoodpicture22, R.id.koreanfoodpicture23, R.id.koreanfoodpicture24, R.id.koreanfoodpicture25,
                    R.id.koreanfoodpicture26, R.id.koreanfoodpicture27, R.id.koreanfoodpicture28, R.id.koreanfoodpicture29, R.id.koreanfoodpicture30,
                    R.id.koreanfoodpicture31, R.id.koreanfoodpicture32, R.id.koreanfoodpicture33, R.id.koreanfoodpicture34, R.id.koreanfoodpicture35,
                    R.id.koreanfoodpicture36, R.id.koreanfoodpicture37, R.id.koreanfoodpicture38, R.id.koreanfoodpicture39, R.id.koreanfoodpicture40,
                    R.id.koreanfoodpicture41, R.id.koreanfoodpicture42, R.id.koreanfoodpicture43, R.id.koreanfoodpicture44, R.id.koreanfoodpicture45,
                    R.id.koreanfoodpicture46, R.id.koreanfoodpicture47, R.id.koreanfoodpicture48, R.id.koreanfoodpicture49, R.id.koreanfoodpicture50,  R.id.koreanfoodpicture51
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
