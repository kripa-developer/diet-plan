package com.dietplan.controller;

import com.dietplan.dto.AiMealResponse;
import com.dietplan.service.AiMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin
public class AiMealController {

    private final AiMealService aiMealService;

    @GetMapping("/suggest")
    public ResponseEntity<AiMealResponse> getAiSuggestion(@RequestParam(defaultValue = "") String prompt) {
        return ResponseEntity.ok(aiMealService.generateMealSuggestion(prompt));
    }
}
