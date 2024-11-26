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

        RatingBar[] koreanFoodRatings = new RatingBar[51];
        TextView[] koreanFoodNames = new TextView[51];
        String[] imageUrls = new String[51];
        String[] koreanFoodMenus = new String[51];
        String[] koreanFoodAddress = new String[51];
        String[] koreanFoodTel = new String[51];
        String[] koreanFoodTime = new String[51];

        LinearLayout[] btnRice = new LinearLayout[51];
        for (int i = 1; i <= 51; i++) {
            String buttonID = "btnRice_" + i; // 동적 ID 생성
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // 리소스 ID 가져오기
            btnRice[i - 1] = findViewById(resID); // 배열에 할당
        }

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
                name_array(db, 1, koreanFoodNames);
                rating_array(db, 1, koreanFoodRatings);
                pictures_array(db, 1, imageUrls);
                etc_array(db, 1, koreanFoodMenus, koreanFoodAddress, koreanFoodTel, koreanFoodTime);
            }
        });
        // 별점순 버튼 클릭 이벤트
        btnRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_array(db, 2, koreanFoodNames);
                rating_array(db, 2, koreanFoodRatings);
                pictures_array(db, 2, imageUrls);
                etc_array(db, 2, koreanFoodMenus, koreanFoodAddress, koreanFoodTel, koreanFoodTime);
            }
        });
        for (int i = 0; i < btnRice.length; i++) {
            final int index = i;  // i 값을 final로 선언하여 Intent에서 사용할 수 있도록 함
            btnRice[i].setOnClickListener(v -> {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("foodTel", koreanFoodTel[index]);
                intent.putExtra("foodTime", koreanFoodTime[index]);
                intent.putExtra("foodAddress", koreanFoodAddress[index]);
                intent.putExtra("foodMenus", koreanFoodMenus[index]);
                intent.putExtra("imageUrls", imageUrls[index]);
                intent.putExtra("foodRatings", koreanFoodRatings[index].getRating());
                intent.putExtra("foodNames", koreanFoodNames[index].getText().toString());

                startActivity(intent);
            });
        }

        name_array(db, 0, koreanFoodNames);
        rating_array(db, 0, koreanFoodRatings);
        pictures_array(db, 0, imageUrls);
        etc_array(db, 0, koreanFoodMenus, koreanFoodAddress, koreanFoodTel, koreanFoodTime);
    }

    private void name_array(SQLiteDatabase db, int num, TextView[] koreanFoodNames) {
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

    private void rating_array(SQLiteDatabase db, int num, RatingBar[] koreanFoodRatings) {
        for (int i = 0; i < koreanFoodRatings.length; i++) {
            String ratingBarID = "koreanfoodrating" + (i + 1); // ID 문자열 생성
            int resID = getResources().getIdentifier(ratingBarID, "id", getPackageName()); // 리소스 ID 가져오기
            koreanFoodRatings[i] = findViewById(resID); // 배열에 RatingBar 할당
        }

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
                if (index < koreanFoodRatings.length) {
                    koreanFoodRatings[index].setRating(ratingValue);
                }
                index++;
            } while (koreanfoodratingcursor.moveToNext());
        }
        // 배열에 저장된 값 확인용 Log 출력 (선택 사항)
        for (int i = 0; i < koreanFoodRatings.length; i++) {
            Log.d("TEL_ARRAY", "별점 " + (i + 1) + ": " + (koreanFoodRatings[i]));
        }
        koreanfoodratingcursor.close();
    }

    private void pictures_array(SQLiteDatabase db, int num, String[] imageUrls) {
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

    private void etc_array(SQLiteDatabase db, int num, String[] koreanFoodMenus,  String[] koreanFoodAddress, String[] koreanFoodTel, String[] koreanFoodTime) {

        // Cursor 쿼리 실행
        Cursor koreanFoodMenuCursor;
        if (num == 0) {
            koreanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            koreanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            koreanFoodMenuCursor = db.rawQuery("SELECT 대표메뉴 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (koreanFoodMenuCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 51) { // 최대 51개의 값만 처리
                    String menuValue = koreanFoodMenuCursor.getString(0); // 대표메뉴 가져오기
                    koreanFoodMenus[index] = menuValue; // 배열에 저장
                }
                index++;
            } while (koreanFoodMenuCursor.moveToNext());
        }
        koreanFoodMenuCursor.close();


        // Cursor 쿼리 실행
        Cursor koreanFoodAddressCursor;
        if (num == 0) {
            koreanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            koreanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            koreanFoodAddressCursor = db.rawQuery("SELECT 주소 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (koreanFoodAddressCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 51) { // 최대 51개의 값만 처리
                    String addressValue = koreanFoodAddressCursor.getString(0); // 대표메뉴 가져오기
                    koreanFoodAddress[index] = addressValue; // 배열에 저장
                }
                index++;
            } while (koreanFoodAddressCursor.moveToNext());
        }
        koreanFoodAddressCursor.close();

        // Cursor 쿼리 실행
        Cursor telCursor;
        if (num == 0) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            telCursor = db.rawQuery("SELECT 전화번호 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }

        // Cursor로 데이터를 가져와 배열에 저장
        if (telCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 51) { // 최대 51개의 값만 처리
                    String telValue = telCursor.getString(0); // 전화번호 가져오기
                    if (telValue == null || telValue.trim().isEmpty()) {
                        koreanFoodTel[index] = "확인 필요"; // 데이터가 없으면 "확인 필요"로 설정
                    } else {
                        koreanFoodTel[index] = telValue; // 데이터가 있으면 배열에 저장
                    }
                }
                index++;
            } while (telCursor.moveToNext());
        }
        telCursor.close();

        // Cursor 쿼리 실행
        Cursor koreanFoodTimeCursor;
        if (num == 0) {
            koreanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '한식' LIMIT 51", null);
        } else if (num == 1) {
            koreanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '한식' ORDER BY 거리 ASC LIMIT 51", null);
        } else {
            koreanFoodTimeCursor = db.rawQuery("SELECT 영업시간 FROM restaurantDB WHERE 분류 = '한식' ORDER BY CASE WHEN 별점 = '별점 없음' THEN 1 ELSE 0 END, 별점 DESC LIMIT 51", null);
        }
        // Cursor로 데이터를 가져와 배열에 저장
        if (koreanFoodTimeCursor.moveToFirst()) {
            int index = 0;
            do {
                if (index < 51) { // 최대 51개의 값만 처리
                    String timeValue = koreanFoodTimeCursor.getString(0); // 대표메뉴 가져오기
                    koreanFoodTime[index] = timeValue; // 배열에 저장
                }
                index++;
            } while (koreanFoodTimeCursor.moveToNext());
        }
        koreanFoodTimeCursor.close();
    }
}
