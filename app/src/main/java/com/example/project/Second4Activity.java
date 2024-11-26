package com.example.project;

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

        RatingBar[] westernFoodRatings = new RatingBar[10];
        TextView[] westernFoodNames = new TextView[10];
        String[] imageUrls = new String[10];
        String[] westernFoodMenus = new String[10];
        String[] westernFoodAddress = new String[10];
        String[] westernFoodTel = new String[10];
        String[] westernFoodTime = new String[10];

        LinearLayout[] btnWestern = new LinearLayout[10];
        for (int i = 1; i <= 10; i++) {
            String buttonID = "btnWestern_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnWestern[i - 1] = findViewById(resID); // 배열에 할당
        }

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
                name_array(db, 1, westernFoodNames);
                rating_array(db, 1, westernFoodRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, westernFoodMenus, westernFoodAddress, westernFoodTel, westernFoodTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, westernFoodNames);
                rating_array(db, 2, westernFoodRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, westernFoodMenus, westernFoodAddress, westernFoodTel, westernFoodTime);
            }
        });
        for (int i = 0; i < btnWestern.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnWestern[i].setOnClickListener(v -> {
                Intent intent = new Intent(Second4Activity.this, ThirdActivity.class);
                intent.putExtra("foodTel", westernFoodTel[index]);
                intent.putExtra("foodTime", westernFoodTime[index]);
                intent.putExtra("foodAddress", westernFoodAddress[index]);
                intent.putExtra("foodMenus", westernFoodMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", westernFoodRatings[index].getRating());
                intent.putExtra("foodNames", westernFoodNames[index].getText().toString());

                startActivity(intent);
            });
        }

        name_array(db, 0, westernFoodNames);
        rating_array(db, 0, westernFoodRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, westernFoodMenus, westernFoodAddress, westernFoodTel, westernFoodTime);
    }
    private void name_array(SQLiteDatabase db, int num, TextView[] westernFoodNames) {
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

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] westernFoodRatings) {
        for (int i = 0; i < westernFoodRatings.length; i++) {
            String ratingBarID = "westernfoodrating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            westernFoodRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

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
                if (index < westernFoodRatings.length) {
                    westernFoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (westernfoodratingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < westernFoodRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (westernFoodRatings[i]));
        }
        westernfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
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

    private void etc_array(SQLiteDatabase db, int num, String[] westernFoodMenus,  String[] westernFoodAddress, String[] westernFoodTel, String[] westernFoodTime) {

        // Cursor 쿼리 실행
        Cursor westernFoodMenuCursor;
        if (num == 0) {
            westernFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            westernFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            westernFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (westernFoodMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 10) { // 최대 10개의 값만 처리
                    String menuValue = westernFoodMenuCursor.getString(0); // 대표메뉴 가져오기
                    westernFoodMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (westernFoodMenuCursor.moveToNext());
        }
        westernFoodMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor westernFoodAddressCursor;
        if (num == 0) {
            westernFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            westernFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            westernFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (westernFoodAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 10) { // 최대 10개의 값만 처리
                    String addressValue = westernFoodAddressCursor.getString(0); // 대표메뉴 가져오기
                    westernFoodAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (westernFoodAddressCursor.moveToNext());
        }
        westernFoodAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 10) { // 최대 10개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        westernFoodTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        westernFoodTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor westernFoodTimeCursor;
        if (num == 0) {
            westernFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '양식' LIMIT 10", null);
        } else if (num == 1) {
            westernFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '양식' ORDER BY 거리 ASC LIMIT 10", null);
        } else {
            westernFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '양식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 10", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (westernFoodTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 10) { // 최대 10개의 값만 처리
                    String timeValue = westernFoodTimeCursor.getString(0); // 대표메뉴 가져오기
                    westernFoodTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (westernFoodTimeCursor.moveToNext());
        }
        westernFoodTimeCursor.close();
    }
}