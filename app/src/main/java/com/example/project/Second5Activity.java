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

        RatingBar[] fastFoodRatings = new RatingBar[48];
        TextView[] fastFoodNames = new TextView[48];
        String[] imageUrls = new String[48];
        String[] fastFoodMenus = new String[48];
        String[] fastFoodAddress = new String[48];
        String[] fastFoodTel = new String[48];
        String[] fastFoodTime = new String[48];

        LinearLayout[] btnFast = new LinearLayout[48];
        for (int i = 1; i <= 48; i++) {
            String buttonID = "btnFast_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnFast[i - 1] = findViewById(resID); // 배열에 할당
        }

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
                name_array(db, 1, fastFoodNames);
                rating_array(db, 1, fastFoodRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, fastFoodMenus, fastFoodAddress, fastFoodTel, fastFoodTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, fastFoodNames);
                rating_array(db, 2, fastFoodRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, fastFoodMenus, fastFoodAddress, fastFoodTel, fastFoodTime);
            }
        });
        for (int i = 0; i < btnFast.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnFast[i].setOnClickListener(v -> {
                Intent intent = new Intent(Second5Activity.this, ThirdActivity.class);
                intent.putExtra("foodTel", fastFoodTel[index]);
                intent.putExtra("foodTime", fastFoodTime[index]);
                intent.putExtra("foodAddress", fastFoodAddress[index]);
                intent.putExtra("foodMenus", fastFoodMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", fastFoodRatings[index].getRating());
                intent.putExtra("foodNames", fastFoodNames[index].getText().toString());

                startActivity(intent);
            });
        }

        name_array(db, 0, fastFoodNames);
        rating_array(db, 0, fastFoodRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, fastFoodMenus, fastFoodAddress, fastFoodTel, fastFoodTime);
    }

    private void name_array(SQLiteDatabase db, int num, TextView[] fastFoodNames) {
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

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] fastFoodRatings) {
        for (int i = 0; i < fastFoodRatings.length; i++) {
            String ratingBarID = "fastfoodrating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            fastFoodRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

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
                if (index < fastFoodRatings.length) {
                    fastFoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (fastfoodratingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < fastFoodRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (fastFoodRatings[i]));
        }
        fastfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
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

    private void etc_array(SQLiteDatabase db, int num, String[] fastFoodMenus,  String[] fastFoodAddress, String[] fastFoodTel, String[] fastFoodTime) {

        // Cursor 쿼리 실행
        Cursor fastFoodMenuCursor;
        if (num == 0) {
            fastFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            fastFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            fastFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (fastFoodMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 48) { // 최대 48개의 값만 처리
                    String menuValue = fastFoodMenuCursor.getString(0); // 대표메뉴 가져오기
                    fastFoodMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (fastFoodMenuCursor.moveToNext());
        }
        fastFoodMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor fastFoodAddressCursor;
        if (num == 0) {
            fastFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            fastFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            fastFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (fastFoodAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 48) { // 최대 48개의 값만 처리
                    String addressValue = fastFoodAddressCursor.getString(0); // 대표메뉴 가져오기
                    fastFoodAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (fastFoodAddressCursor.moveToNext());
        }
        fastFoodAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 48) { // 최대 48개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        fastFoodTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        fastFoodTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor fastFoodTimeCursor;
        if (num == 0) {
            fastFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' LIMIT 48", null);
        } else if (num == 1) {
            fastFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY 거리 ASC LIMIT 48", null);
        } else {
            fastFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '패스트푸드/분식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 48", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (fastFoodTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 48) { // 최대 48개의 값만 처리
                    String timeValue = fastFoodTimeCursor.getString(0); // 대표메뉴 가져오기
                    fastFoodTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (fastFoodTimeCursor.moveToNext());
        }
        fastFoodTimeCursor.close();
    }
}
