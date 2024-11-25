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

public class Second5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second5);

        LinearLayout btnRice2 = findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = findViewById(R.id.btnChina2);
        LinearLayout btnSushi2 = findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = findViewById(R.id.btnSteak2);
        LinearLayout btnCafe2 = findViewById(R.id.btnCafe2);
        Button btnDis = findViewById(R.id.btnDis);
        Button btnRat = findViewById(R.id.btnRat);

        LinearLayout btnFast_1 = findViewById(R.id.btnFast_1);

        btnFast_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, third5.class);
                startActivity(intent);
            }
        });

        // 한식 버튼 클릭 이벤트
        btnRice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 중식 버튼 클릭 이벤트
        btnChina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, Second2Activity.class);
                startActivity(intent);
            }
        });

        // 일식 버튼 클릭 이벤트
        btnSushi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, Second3Activity.class);
                startActivity(intent);
            }
        });

        // 패스트푸드/분식 버튼 클릭 이벤트
        btnSteak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, Second4Activity.class);
                startActivity(intent);
            }
        });

        // 카페 버튼 클릭 이벤트
        btnCafe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second5Activity.this, Second6Activity.class);
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
        TextView[] fastFoodNames = new TextView[48];
        for (int i = 0; i < 48; i++) {
            String textViewID = "fastfoodname" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            fastFoodNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor fastfoodnamecursor;
        if (num == 0) {
            fastfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            fastfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            fastfoodnamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (fastfoodnamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 48) { // 최대 48개의 값만 처리
                    String nameValue = fastfoodnamecursor.getString(0); // 이름 가져오기
                    fastFoodNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (fastfoodnamecursor.moveToNext());
        }
        fastfoodnamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num) {
        // RatingBar 배열 선언
        RatingBar[] fastfoodRatings = {
                findViewById(R.id.fastfoodrating1), findViewById(R.id.fastfoodrating2), findViewById(R.id.fastfoodrating3),
                findViewById(R.id.fastfoodrating4), findViewById(R.id.fastfoodrating5), findViewById(R.id.fastfoodrating6),
                findViewById(R.id.fastfoodrating7), findViewById(R.id.fastfoodrating8), findViewById(R.id.fastfoodrating9),
                findViewById(R.id.fastfoodrating10), findViewById(R.id.fastfoodrating11), findViewById(R.id.fastfoodrating12),
                findViewById(R.id.fastfoodrating13), findViewById(R.id.fastfoodrating14), findViewById(R.id.fastfoodrating15),
                findViewById(R.id.fastfoodrating16), findViewById(R.id.fastfoodrating17), findViewById(R.id.fastfoodrating18),
                findViewById(R.id.fastfoodrating19), findViewById(R.id.fastfoodrating20), findViewById(R.id.fastfoodrating21),
                findViewById(R.id.fastfoodrating22), findViewById(R.id.fastfoodrating23), findViewById(R.id.fastfoodrating24),
                findViewById(R.id.fastfoodrating25), findViewById(R.id.fastfoodrating26), findViewById(R.id.fastfoodrating27),
                findViewById(R.id.fastfoodrating28), findViewById(R.id.fastfoodrating29), findViewById(R.id.fastfoodrating30),
                findViewById(R.id.fastfoodrating31), findViewById(R.id.fastfoodrating32), findViewById(R.id.fastfoodrating33),
                findViewById(R.id.fastfoodrating34), findViewById(R.id.fastfoodrating35), findViewById(R.id.fastfoodrating36),
                findViewById(R.id.fastfoodrating37), findViewById(R.id.fastfoodrating38), findViewById(R.id.fastfoodrating39),
                findViewById(R.id.fastfoodrating40), findViewById(R.id.fastfoodrating41), findViewById(R.id.fastfoodrating42),
                findViewById(R.id.fastfoodrating43), findViewById(R.id.fastfoodrating44), findViewById(R.id.fastfoodrating45),
                findViewById(R.id.fastfoodrating46), findViewById(R.id.fastfoodrating47), findViewById(R.id.fastfoodrating48)
        };

        // "패스트푸드/분식" 분류의 별점 값을 48개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48";
        }

        Cursor fastfoodratingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (fastfoodratingcursor.moveToFirst()) {
            do {
                String ratingString = fastfoodratingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < fastfoodRatings.length) {
                    fastfoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (fastfoodratingcursor.moveToNext());
        }

        fastfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num) {
        String[] imageUrls = new String[48];  // 이미지 URL을 저장할 배열
        Cursor fastfoodimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            fastfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            fastfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            fastfoodimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (fastfoodimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = fastfoodimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (fastfoodimageurlcursor.moveToNext());
            fastfoodimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.fastfoodpicture1, R.id.fastfoodpicture2, R.id.fastfoodpicture3, R.id.fastfoodpicture4, R.id.fastfoodpicture5,
                    R.id.fastfoodpicture6, R.id.fastfoodpicture7, R.id.fastfoodpicture8, R.id.fastfoodpicture9, R.id.fastfoodpicture10,
                    R.id.fastfoodpicture11, R.id.fastfoodpicture12, R.id.fastfoodpicture13, R.id.fastfoodpicture14, R.id.fastfoodpicture15,
                    R.id.fastfoodpicture16, R.id.fastfoodpicture17, R.id.fastfoodpicture18, R.id.fastfoodpicture19, R.id.fastfoodpicture20,
                    R.id.fastfoodpicture21, R.id.fastfoodpicture22, R.id.fastfoodpicture23, R.id.fastfoodpicture24, R.id.fastfoodpicture25,
                    R.id.fastfoodpicture26, R.id.fastfoodpicture27, R.id.fastfoodpicture28, R.id.fastfoodpicture29, R.id.fastfoodpicture30,
                    R.id.fastfoodpicture31, R.id.fastfoodpicture32, R.id.fastfoodpicture33, R.id.fastfoodpicture34, R.id.fastfoodpicture35,
                    R.id.fastfoodpicture36, R.id.fastfoodpicture37, R.id.fastfoodpicture38, R.id.fastfoodpicture39, R.id.fastfoodpicture40,
                    R.id.fastfoodpicture41, R.id.fastfoodpicture42, R.id.fastfoodpicture43, R.id.fastfoodpicture44, R.id.fastfoodpicture45,
                    R.id.fastfoodpicture46, R.id.fastfoodpicture47, R.id.fastfoodpicture48
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

