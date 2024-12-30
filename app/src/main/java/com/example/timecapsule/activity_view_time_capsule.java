package com.example.timecapsule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class activity_view_time_capsule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_time_capsule);

        // 뷰 연결
        TextView messageContent = findViewById(R.id.message_content);
        TextView statusMessage = findViewById(R.id.status_message);
        TextView messageDate = findViewById(R.id.message_date);
        Button backButton = findViewById(R.id.back_button);

        // Intent로 데이터 받기
        String selectedDate = getIntent().getStringExtra("selectedDate");
        String capsuleData = getIntent().getStringExtra("message");

        // capsuleData를 작성일과 메시지로 분리
        String[] dataParts = capsuleData.split("\\|");
        String creationDate = dataParts[0]; // 작성일
        String message = dataParts[1]; // 메시지

        // 현재 날짜 확인
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date selectedDateObj = parseDate(selectedDate, sdf);
        Date currentDateObj = new Date(); // 오늘 날짜

        // 날짜가 정상적으로 파싱된 경우에만 처리
        if (selectedDateObj != null) {
            if (selectedDateObj.compareTo(currentDateObj) <= 0) {
                // 과거 날짜거나 오늘 날짜면 열람 가능
                messageContent.setVisibility(android.view.View.VISIBLE);
                statusMessage.setVisibility(android.view.View.GONE);
                messageDate.setVisibility(android.view.View.VISIBLE);
                // 메시지 및 작성일 표시
                messageContent.setText(message);
                messageDate.setText("작성일: " + creationDate);

            } else {
                // 미래 날짜면 열람 불가능
                messageContent.setVisibility(android.view.View.GONE);
                statusMessage.setVisibility(android.view.View.VISIBLE);
                messageDate.setVisibility(android.view.View.GONE);
                statusMessage.setText("아직 열 수 없는 타임캡슐입니다.");
                // 메시지 및 작성일 표시
                messageContent.setText(message);
                messageDate.setText("작성일: " + creationDate);
            }
        } else {
            // 날짜가 파싱되지 않은 경우
            messageContent.setVisibility(android.view.View.GONE);
            statusMessage.setVisibility(android.view.View.VISIBLE);
            messageDate.setVisibility(android.view.View.GONE);
            statusMessage.setText("잘못된 날짜 형식입니다.");
        }


        // 돌아가기 버튼 클릭 이벤트
        backButton.setOnClickListener(v -> finish());
    }

    // 날짜 문자열을 Date 객체로 변환 (예외 없는 구조)
    private Date parseDate(String dateString, SimpleDateFormat sdf) {
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null; // 변환 실패 시 null 반환
        }
    }
}
