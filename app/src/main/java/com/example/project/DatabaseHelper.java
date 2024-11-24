package com.example.project;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crawling.db"; // assets 폴더의 데이터베이스 이름
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전
    private final Context context;
    private static final String DATABASE_PATH = "/data/data/com.example.project/databases/"; // 앱 데이터베이스 경로

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        // 데이터베이스가 존재하지 않으면 복사
        if (!checkDatabaseExists()) {
            copyDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 이미 복사된 데이터베이스를 사용하기 때문에 새로 생성하지 않음
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 업그레이드 시 기존 데이터베이스 삭제 후 복사
        context.deleteDatabase(DATABASE_NAME);
        copyDatabase();
    }

    private boolean checkDatabaseExists() {
        // 데이터베이스 파일이 이미 존재하는지 확인
        File databaseFile = new File(DATABASE_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    private void copyDatabase() {
        try {
            // assets에서 데이터베이스 파일 읽기
            InputStream input = context.getAssets().open(DATABASE_NAME);
            String outputFileName = DATABASE_PATH + DATABASE_NAME;

            // 데이터베이스 디렉토리가 없으면 생성
            File databaseFolder = new File(DATABASE_PATH);
            if (!databaseFolder.exists()) {
                databaseFolder.mkdirs();
            }

            // 내부 저장소로 데이터베이스 파일 복사
            OutputStream output = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // 스트림 닫기
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
