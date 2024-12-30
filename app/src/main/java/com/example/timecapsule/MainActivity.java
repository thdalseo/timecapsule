package com.example.timecapsule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TimeCapsule> timeCapsules; // 날짜와 메시지를 함께 관리
    private ArrayAdapter<String> adapter;
    private ArrayList<String> timeCapsuleDates; // 리스트뷰에 표시할 날짜 목록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 연결
        ListView timeCapsuleList = findViewById(R.id.time_capsule_list);
        Button addTimeCapsuleButton = findViewById(R.id.add_time_capsule_button);

        // 데이터 초기화
        timeCapsules = new ArrayList<>();
        timeCapsuleDates = new ArrayList<>();

        // 어댑터 설정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeCapsuleDates);
        timeCapsuleList.setAdapter(adapter);

        // 데이터 로드
        loadTimeCapsules();

        // "타임캡슐 추가" 버튼 클릭 이벤트
        addTimeCapsuleButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_add_time_capsule.class);
            startActivity(intent);
        });

        // 리스트뷰 항목 클릭 이벤트
        timeCapsuleList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, activity_view_time_capsule.class);
            intent.putExtra("selectedDate", timeCapsules.get(position).getDate()); // 선택된 날짜 전달
            intent.putExtra("message", timeCapsules.get(position).getMessage()); // 선택된 메시지 전달
            startActivity(intent);
        });
    }

    // SharedPreferences에서 데이터 로드
    private void loadTimeCapsules() {
        SharedPreferences sharedPreferences = getSharedPreferences("TimeCapsuleApp", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        timeCapsules.clear();
        timeCapsuleDates.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String date = entry.getKey(); // 날짜
            String message = entry.getValue().toString(); // 메시지
            try {
                // TimeCapsule 객체로 추가
                timeCapsules.add(new TimeCapsule(date, message, sdf.parse(date)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 날짜 기준으로 정렬
        Collections.sort(timeCapsules, Comparator.comparing(TimeCapsule::getParsedDate));

        // 정렬된 결과를 리스트뷰에 표시
        for (TimeCapsule capsule : timeCapsules) {
            timeCapsuleDates.add(capsule.getDate()); // 날짜 목록만 추가
        }

        // 어댑터 데이터 갱신
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 데이터 갱신
        loadTimeCapsules();
    }
}
