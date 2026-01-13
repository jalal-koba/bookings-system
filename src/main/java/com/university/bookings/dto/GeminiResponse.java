package com.university.bookings.dto;

import java.time.LocalDateTime;

public class GeminiResponse {

    private LocalDateTime suggestedTime;
    private String note;

    public GeminiResponse(LocalDateTime suggestedTime, String note) {
        this.suggestedTime = suggestedTime;
        this.note = note;
    }

    public LocalDateTime getSuggestedTime() {
        return suggestedTime;
    }

    public String getNote() {
        return note;
    }
}
