package com.university.bookings.controller;

import org.springframework.web.bind.annotation.*;

import com.university.bookings.dto.GeminiResponse;
import com.university.bookings.service.GeminiService;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/suggest/{serviceId}")
    public GeminiResponse suggest(@PathVariable Long serviceId) {
        return geminiService.suggestBestTime(serviceId);
    }
}
