package com.example.timecapsule;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class activity_add_time_capsule extends AppCompatActivity {
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_capsule);

        // 뷰 연결
        EditText messageInput = findViewById(R.id.message_input);
        Button datePickerButton = findViewById(R.id.date_picker_button);
        Button saveButton = findViewById(R.id.save_button);

        // 날짜 선택 버튼
        datePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, day1) -> {
                selectedDate = year1 + "-" + (month1 + 1) + "-" + day1;
                datePickerButton.setText(selectedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        // 저장 버튼
        saveButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();

            if (message.isEmpty() || selectedDate == null) {
                Toast.makeText(this, "메시지와 열람 날짜를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 작성일 추가
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String creationDate = sdf.format(new Date());

            // 데이터 저장 (SharedPreferences 사용)
            SharedPreferences sharedPreferences = getSharedPreferences("TimeCapsuleApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // 작성일과 메시지를 JSON 형식으로 저장
            String capsuleData = creationDate + "|" + message;
            editor.putString(selectedDate, capsuleData);
            editor.apply();

            Toast.makeText(this, "타임캡슐이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            finish(); // 작성 화면 닫기
        });
    }
}