package com.book.book_recommender.controller;
import com.book.book_recommender.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class RecommendController {
    private final GeminiService geminiService;
    @Autowired
    public RecommendController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }
    @GetMapping("/recommend")
    public String getRecommendation(@RequestParam String prompt) {
        return geminiService.generateContent(prompt);
    }
}