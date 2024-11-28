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

public class Second2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // DatabaseHelper 생성
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second2);

        LinearLayout btnRice2 = (LinearLayout) findViewById(R.id.btnRice2);
        LinearLayout btnSushi2 = (LinearLayout) findViewById(R.id.btnSushi2);
        LinearLayout btnSteak2 = (LinearLayout) findViewById(R.id.btnSteak2);
        LinearLayout btnFast2 = (LinearLayout) findViewById(R.id.btnFast2);
        LinearLayout btnCafe2 = (LinearLayout) findViewById(R.id.btnCafe2);
        Button btnDis = (Button) findViewById(R.id.btnDis);
        Button btnRat = (Button) findViewById(R.id.btnRat);
        Button btnRe = (Button) findViewById(R.id.btnRe);
        
        RatingBar[] chinaFoodRatings = new RatingBar[14];
        TextView[] chinaFoodNames = new TextView[14];
        String[] imageUrls = new String[14];
        String[] chinaFoodMenus = new String[14];
        String[] chinaFoodAddress = new String[14];
        String[] chinaFoodTel = new String[14];
        String[] chinaFoodTime = new String[14];

        LinearLayout[] btnChina = new LinearLayout[14];
        for (int i = 1; i <= 14; i++) {
            String buttonID = "btnChina_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnChina[i - 1] = findViewById(resID); // 배열에 할당
        }

        // 한식 버튼 클릭 이벤트
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

        // 패스트푸드/분식 버튼 클릭 이벤트
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
                name_array(db, 1, chinaFoodNames);
                rating_array(db, 1, chinaFoodRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, chinaFoodMenus, chinaFoodAddress, chinaFoodTel, chinaFoodTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, chinaFoodNames);
                rating_array(db, 2, chinaFoodRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, chinaFoodMenus, chinaFoodAddress, chinaFoodTel, chinaFoodTime);
            }
        });
        // 돌아가기 버튼 클릭 이벤트
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동
                Intent intent = new Intent(Second2Activity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 현재 Activity 종료
            }
        });
        for (int i = 0; i < btnChina.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnChina[i].setOnClickListener(v -> {
                Intent intent = new Intent(Second2Activity.this, ThirdActivity.class);
                intent.putExtra("foodTel", chinaFoodTel[index]);
                intent.putExtra("foodTime", chinaFoodTime[index]);
                intent.putExtra("foodAddress", chinaFoodAddress[index]);
                intent.putExtra("foodMenus", chinaFoodMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", chinaFoodRatings[index].getRating());
                intent.putExtra("foodNames", chinaFoodNames[index].getText().toString());

                startActivity(intent);
            });
        }
        name_array(db, 0, chinaFoodNames);
        rating_array(db, 0, chinaFoodRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, chinaFoodMenus, chinaFoodAddress, chinaFoodTel, chinaFoodTime);
    }
    private void name_array(SQLiteDatabase db, int num, TextView[] chinaFoodNames) {
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

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] chinaFoodRatings) {
        for (int i = 0; i < chinaFoodRatings.length; i++) {
            String ratingBarID = "chinafoodrating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            chinaFoodRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

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
                if (index < chinaFoodRatings.length) {
                    chinaFoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (chinafoodratingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < chinaFoodRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (chinaFoodRatings[i]));
        }
        chinafoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
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


    private void etc_array(SQLiteDatabase db, int num, String[] chinaFoodMenus,  String[] chinaFoodAddress, String[] chinaFoodTel, String[] chinaFoodTime) {

        // Cursor 쿼리 실행
        Cursor chinaFoodMenuCursor;
        if (num == 0) {
            chinaFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            chinaFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            chinaFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (chinaFoodMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 14) { // 최대 14개의 값만 처리
                    String menuValue = chinaFoodMenuCursor.getString(0); // 대표메뉴 가져오기
                    chinaFoodMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (chinaFoodMenuCursor.moveToNext());
        }
        chinaFoodMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor chinaFoodAddressCursor;
        if (num == 0) {
            chinaFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            chinaFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            chinaFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (chinaFoodAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 14) { // 최대 14개의 값만 처리
                    String addressValue = chinaFoodAddressCursor.getString(0); // 대표메뉴 가져오기
                    chinaFoodAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (chinaFoodAddressCursor.moveToNext());
        }
        chinaFoodAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 14) { // 최대 14개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        chinaFoodTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        chinaFoodTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor chinaFoodTimeCursor;
        if (num == 0) {
            chinaFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '중식' LIMIT 14", null);
        } else if (num == 1) {
            chinaFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '중식' ORDER BY 거리 ASC LIMIT 14", null);
        } else {
            chinaFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '중식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 14", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (chinaFoodTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 14) { // 최대 14개의 값만 처리
                    String timeValue = chinaFoodTimeCursor.getString(0); // 대표메뉴 가져오기
                    chinaFoodTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (chinaFoodTimeCursor.moveToNext());
        }
        chinaFoodTimeCursor.close();
    }
}
