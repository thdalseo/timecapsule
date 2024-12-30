package com.example.timecapsule;

import java.util.Date;

public class TimeCapsule {
    private String date; // yyyy-MM-dd 형식의 문자열
    private String message; // 저장된 메시지
    private Date parsedDate; // Date 객체

    public TimeCapsule(String date, String message, Date parsedDate) {
        this.date = date;
        this.message = message;
        this.parsedDate = parsedDate;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public Date getParsedDate() {
        return parsedDate;
    }
}
