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

public class Second3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second3);

        LinearLayout btnRice2 = (LinearLayout) findViewById(R.id.btnRice2);
        LinearLayout btnChina2 = (LinearLayout) findViewById(R.id.btnChina2);
        LinearLayout btnSteak2 = (LinearLayout) findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = (LinearLayout) findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = (LinearLayout) findViewById(R.id.btnCafe2);
        Button btnDis = (Button) findViewById(R.id.btnDis);
        Button btnRat = (Button) findViewById(R.id.btnRat);
        Button btnRe = (Button) findViewById(R.id.btnRe);

        RatingBar[] japanFoodRatings = new RatingBar[13];
        TextView[] japanFoodNames = new TextView[13];
        String[] imageUrls = new String[13];
        String[] japanFoodMenus = new String[13];
        String[] japanFoodAddress = new String[13];
        String[] japanFoodTel = new String[13];
        String[] japanFoodTime = new String[13];

        LinearLayout[] btnJapan = new LinearLayout[13];
        for (int i = 1; i <= 13; i++) {
            String buttonID = "btnJapan_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnJapan[i - 1] = findViewById(resID); // 배열에 할당
        }

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

        // 패스트푸드/분식 버튼 클릭 이벤트
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
                name_array(db, 1, japanFoodNames);
                rating_array(db, 1, japanFoodRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, japanFoodMenus, japanFoodAddress, japanFoodTel, japanFoodTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, japanFoodNames);
                rating_array(db, 2, japanFoodRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, japanFoodMenus, japanFoodAddress, japanFoodTel, japanFoodTime);
            }
        });
        // 돌아가기 버튼 클릭 이벤트
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동
                Intent intent = new Intent(Second3Activity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 현재 Activity 종료
            }
        });
        for (int i = 0; i < btnJapan.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnJapan[i].setOnClickListener(v -> {
                Intent intent = new Intent(Second3Activity.this, ThirdActivity.class);
                intent.putExtra("foodTel", japanFoodTel[index]);
                intent.putExtra("foodTime", japanFoodTime[index]);
                intent.putExtra("foodAddress", japanFoodAddress[index]);
                intent.putExtra("foodMenus", japanFoodMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", japanFoodRatings[index].getRating());
                intent.putExtra("foodNames", japanFoodNames[index].getText().toString());

                startActivity(intent);
            });
        }

        name_array(db, 0, japanFoodNames);
        rating_array(db, 0, japanFoodRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, japanFoodMenus, japanFoodAddress, japanFoodTel, japanFoodTime);
    }
    private void name_array(SQLiteDatabase db, int num, TextView[] japanFoodNames) {
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

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] japanFoodRatings) {
        for (int i = 0; i < japanFoodRatings.length; i++) {
            String ratingBarID = "japanfoodrating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            japanFoodRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

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
                if (index < japanFoodRatings.length) {
                    japanFoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (japanfoodratingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < japanFoodRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (japanFoodRatings[i]));
        }
        japanfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
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

    private void etc_array(SQLiteDatabase db, int num, String[] japanFoodMenus,  String[] japanFoodAddress, String[] japanFoodTel, String[] japanFoodTime) {

        // Cursor 쿼리 실행
        Cursor japanFoodMenuCursor;
        if (num == 0) {
            japanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            japanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            japanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (japanFoodMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 13) { // 최대 13개의 값만 처리
                    String menuValue = japanFoodMenuCursor.getString(0); // 대표메뉴 가져오기
                    japanFoodMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (japanFoodMenuCursor.moveToNext());
        }
        japanFoodMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor japanFoodAddressCursor;
        if (num == 0) {
            japanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            japanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            japanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (japanFoodAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 13) { // 최대 13개의 값만 처리
                    String addressValue = japanFoodAddressCursor.getString(0); // 대표메뉴 가져오기
                    japanFoodAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (japanFoodAddressCursor.moveToNext());
        }
        japanFoodAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 13) { // 최대 13개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        japanFoodTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        japanFoodTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor japanFoodTimeCursor;
        if (num == 0) {
            japanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '일식' LIMIT 13", null);
        } else if (num == 1) {
            japanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '일식' ORDER BY 거리 ASC LIMIT 13", null);
        } else {
            japanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '일식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 13", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (japanFoodTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 13) { // 최대 13개의 값만 처리
                    String timeValue = japanFoodTimeCursor.getString(0); // 대표메뉴 가져오기
                    japanFoodTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (japanFoodTimeCursor.moveToNext());
        }
        japanFoodTimeCursor.close();
    }
}
