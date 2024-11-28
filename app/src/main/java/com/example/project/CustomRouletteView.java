package com.example.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import java.util.List;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
;
public class CustomRouletteView extends View {

    private float centerX;  // 뷰의 중심 X 좌표
    private float centerY;  // 뷰의 중심 Y 좌표
    private List<String> rouletteDataList;  // 룰렛 텍스트 데이터
    private int[] shapeColors;              // 섹션 색상 배열
    private Paint fillPaint = new Paint();  // 섹션 색상 페인트
    private Paint textPaint = new Paint();  // 텍스트 페인트
    private Paint arrowPaint = new Paint(); // 화살표 페인트
    private RotateListener rotateListener; // 회전 이벤트 콜백
    private long rotateDuration = 2000; // 기본 회전 지속 시간: 2초




    public CustomRouletteView(Context context) {
        super(context);
        init();
    }

    public CustomRouletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRouletteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 섹션 색상 페인트 설정
        fillPaint.setStyle(Paint.Style.FILL);
        // 텍스트 페인트 설정
        textPaint.setColor(0xFF000000); // 검정색 텍스트
        textPaint.setTextSize(40f);    // 텍스트 크기
        textPaint.setTextAlign(Paint.Align.CENTER); // 텍스트 정렬

        // 화살표 페인트 설정
        arrowPaint.setColor(0xFFFF0000); // 빨간색 화살표
        arrowPaint.setStyle(Paint.Style.FILL);

    }

    public void setRouletteData(List<String> data, int[] colors) {
        this.rouletteDataList = data;
        this.shapeColors = colors;
        invalidate(); // 뷰를 다시 그리도록 요청
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 데이터와 색상이 설정되지 않았다면 아무것도 그리지 않음
        if (rouletteDataList == null || shapeColors == null) return;

        RectF rectF = new RectF(
                (getWidth() - 450) / 2 + getPaddingLeft(),    // 좌측
                (getHeight() - 450) / 2 + getPaddingTop(),   // 상단
                (getWidth() + 450) / 2 - getPaddingRight(),    // 우측
                (getHeight() + 450) / 2 - getPaddingBottom()    // 하단
        );

        // 중심 좌표 계산
        centerX = rectF.centerX();
        centerY = rectF.centerY();

        // 룰렛 그리기
        drawRoulette(canvas, rectF);

    }

    private void drawRoulette(Canvas canvas, RectF rectF) {
        // 데이터 개수가 유효하지 않으면 그리지 않음
        if (rouletteDataList.size() < 2 || rouletteDataList.size() > 8) {
            throw new IllegalArgumentException("룰렛 데이터는 2개 이상 8개 이하이어야 합니다.");
        }

        // 각 섹션의 각도 계산
        float sweepAngle = 360f / rouletteDataList.size();
        float radius = (rectF.right - rectF.left) / 2 * 0.5f; // 텍스트 위치 계산에 사용

        for (int i = 0; i < rouletteDataList.size(); i++) {
            // 색상 설정
            fillPaint.setColor(ContextCompat.getColor(getContext(), shapeColors[i]));

            // 섹션 그리기
            float startAngle = sweepAngle * i;
            canvas.drawArc(rectF, startAngle, sweepAngle, true, fillPaint);

            // 텍스트 위치 계산
            double medianAngle = Math.toRadians(startAngle + sweepAngle / 2f); // 섹션 중앙 각도
            float x = (float) (centerX + radius * Math.cos(medianAngle));
            float y = (float) (centerY + radius * Math.sin(medianAngle)) + 20; // 약간의 패딩 추가

            // 텍스트 그리기
            String text = rouletteDataList.get(i);
            canvas.drawText(text, x, y, textPaint);
        }
    }
    /**
     * 룰렛 회전 함수
     * @param toDegrees : 종료 각도(시작 각도 : 0)
     */
    public void rotateRoulette(float toDegrees) {
        Animation.AnimationListener animListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {
                if (rotateListener != null) {
                    rotateListener.onRotateStart();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (rotateListener != null) {
                    rotateListener.onRotateEnd(getRouletteRotateResult(toDegrees));
                }
            }
        };

        RotateAnimation rotateAnim = new RotateAnimation(
                0f, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnim.setDuration(rotateDuration);
        rotateAnim.setFillAfter(true);
        rotateAnim.setAnimationListener(animListener);

        startAnimation(rotateAnim);
    }

    private int getRouletteRotateResult(float toDegrees) {
        float normalizedDegrees = toDegrees % 360; // 360도를 기준으로 각도를 정규화
        int numSections = rouletteDataList.size(); // 섹션 개수
        float degreesPerSection = 360f / numSections;

        // 결과 인덱스 계산
        int resultIndex = (int) ((numSections - (normalizedDegrees / degreesPerSection)) % numSections);
        return resultIndex;
    }

    public void setRotateListener(RotateListener listener) {
        this.rotateListener = listener;
    }

    public interface RotateListener {
        void onRotateStart();
        void onRotateEnd(int resultIndex);
    }

    public void setRotateDuration(long duration) {
        this.rotateDuration = duration;
    }
}
