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

        RatingBar[] cafeRatings = new RatingBar[30];
        TextView[] cafeNames = new TextView[30];
        String[] imageUrls = new String[30];
        String[] cafeMenus = new String[30];
        String[] cafeAddress = new String[30];
        String[] cafeTel = new String[30];
        String[] cafeTime = new String[30];

        LinearLayout[] btnCafe = new LinearLayout[30];
        for (int i = 1; i <= 30; i++) {
            String buttonID = "btnCafe_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnCafe[i - 1] = findViewById(resID); // 배열에 할당
        }

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
                name_array(db, 1, cafeNames);
                rating_array(db, 1, cafeRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, cafeMenus, cafeAddress, cafeTel, cafeTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, cafeNames);
                rating_array(db, 2, cafeRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, cafeMenus, cafeAddress, cafeTel, cafeTime);
            }
        });
        for (int i = 0; i < btnCafe.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnCafe[i].setOnClickListener(v -> {
                Intent intent = new Intent(Second6Activity.this, ThirdActivity.class);
                intent.putExtra("foodTel", cafeTel[index]);
                intent.putExtra("foodTime", cafeTime[index]);
                intent.putExtra("foodAddress", cafeAddress[index]);
                intent.putExtra("foodMenus", cafeMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", cafeRatings[index].getRating());
                intent.putExtra("foodNames", cafeNames[index].getText().toString());

                startActivity(intent);
            });
        }

        name_array(db, 0, cafeNames);
        rating_array(db, 0, cafeRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, cafeMenus, cafeAddress, cafeTel, cafeTime);
    }
    private void name_array(SQLiteDatabase db, int num, TextView[] cafeNames) {
        for (int i = 0; i < 30; i++) {
            String textViewID = "cafename" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            cafeNames[i] = findViewById(resID); // TextView 배열에 할당
        }

        // Cursor 쿼리 실행
        Cursor cafenamecursor;
        if (num == 0) {
            cafenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cafenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cafenamecursor = db.rawQuery("SELECT 이름 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }

        // Cursor로 데이터를 가져와 TextView에 설정
        if (cafenamecursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String nameValue = cafenamecursor.getString(0); // 이름 가져오기
                    cafeNames[index].setText(nameValue); // TextView에 설정
                }
                index++;
            } while (cafenamecursor.moveToNext());
        }
        cafenamecursor.close();
    }

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] cafeRatings) {
        for (int i = 0; i < cafeRatings.length; i++) {
            String ratingBarID = "caferating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            cafeRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

        // "카페/디저트" 분류의 별점 값을 30개 가져오는 쿼리
        String query;
        if (num == 0) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30";
        } else if (num == 1) {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30";
        } else {
            query = "SELECT 별점 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30";
        }

        Cursor caferatingcursor = db.rawQuery(query, null);

        int index = 0; // RatingBar 인덱스
        if (caferatingcursor.moveToFirst()) {
            do {
                String ratingString = caferatingcursor.getString(0); // 별점 값을 문자열로 가져오기
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
                if (index < cafeRatings.length) {
                    cafeRatings[index].setRating(ratingValue);
                }
                index++;
            } while (caferatingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < cafeRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (cafeRatings[i]));
        }
        caferatingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
        Cursor cafeimageurlcursor;

        // 쿼리 실행
        if (num == 0) {
            cafeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cafeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cafeimageurlcursor = db.rawQuery("SELECT \"이미지 url\" FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }

        // 커서 데이터를 배열에 저장
        int index = 0;
        if (cafeimageurlcursor.moveToFirst()) {
            do {
                String imageUrl = cafeimageurlcursor.getString(0);
                if (imageUrl.equals("사진 없음")) {
                    imageUrls[index] = "android.resource://" + getPackageName() + "/drawable/no_image";
                } else {
                    imageUrls[index] = imageUrl;
                }
                index++;
            } while (cafeimageurlcursor.moveToNext());
            cafeimageurlcursor.close();

            // ImageView 배열 생성
            int[] imageViewIds = {
                    R.id.cafepicture1, R.id.cafepicture2, R.id.cafepicture3, R.id.cafepicture4, R.id.cafepicture5,
                    R.id.cafepicture6, R.id.cafepicture7, R.id.cafepicture8, R.id.cafepicture9, R.id.cafepicture10,
                    R.id.cafepicture11, R.id.cafepicture12, R.id.cafepicture13, R.id.cafepicture14, R.id.cafepicture15,
                    R.id.cafepicture16, R.id.cafepicture17, R.id.cafepicture18, R.id.cafepicture19, R.id.cafepicture20,
                    R.id.cafepicture21, R.id.cafepicture22, R.id.cafepicture23, R.id.cafepicture24, R.id.cafepicture25,
                    R.id.cafepicture26, R.id.cafepicture27, R.id.cafepicture28, R.id.cafepicture29, R.id.cafepicture30
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

    private void etc_array(SQLiteDatabase db, int num, String[] cafeMenus,  String[] cafeAddress, String[] cafeTel, String[] cafeTime) {

        // Cursor 쿼리 실행
        Cursor cafeMenuCursor;
        if (num == 0) {
            cafeMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cafeMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cafeMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (cafeMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String menuValue = cafeMenuCursor.getString(0); // 대표메뉴 가져오기
                    cafeMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (cafeMenuCursor.moveToNext());
        }
        cafeMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor cafeAddressCursor;
        if (num == 0) {
            cafeAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cafeAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cafeAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (cafeAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String addressValue = cafeAddressCursor.getString(0); // 대표메뉴 가져오기
                    cafeAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (cafeAddressCursor.moveToNext());
        }
        cafeAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        cafeTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        cafeTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor cafeTimeCursor;
        if (num == 0) {
            cafeTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '카페/디저트' LIMIT 30", null);
        } else if (num == 1) {
            cafeTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY 거리 ASC LIMIT 30", null);
        } else {
            cafeTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '카페/디저트' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 30", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (cafeTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 30) { // 최대 30개의 값만 처리
                    String timeValue = cafeTimeCursor.getString(0); // 대표메뉴 가져오기
                    cafeTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (cafeTimeCursor.moveToNext());
        }
        cafeTimeCursor.close();
    }
}
