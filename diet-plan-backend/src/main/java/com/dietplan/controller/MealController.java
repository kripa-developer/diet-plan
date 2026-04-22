package com.dietplan.controller;

import com.dietplan.dto.MealDto;
import com.dietplan.model.enums.MealCategory;
import com.dietplan.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealDto>> getMeals(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {

        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(mealService.searchMeals(search));
        }
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(mealService.getMealsByCategory(MealCategory.valueOf(category.toUpperCase())));
        }
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDto> getMeal(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @PostMapping
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mealService.createMeal(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealDto> updateMeal(@PathVariable Long id, @RequestBody MealDto dto) {
        return ResponseEntity.ok(mealService.updateMeal(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
