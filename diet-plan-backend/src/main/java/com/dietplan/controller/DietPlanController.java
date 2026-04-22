package com.dietplan.controller;

import com.dietplan.dto.AddMealToPlanRequest;
import com.dietplan.dto.CreateDietPlanRequest;
import com.dietplan.dto.DietPlanDto;
import com.dietplan.service.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet-plans")
@RequiredArgsConstructor
public class DietPlanController {

    private final DietPlanService dietPlanService;

    @GetMapping
    public ResponseEntity<List<DietPlanDto>> getMyPlans(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(dietPlanService.getUserPlans(user.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietPlanDto> getPlan(@PathVariable Long id,
                                               @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(dietPlanService.getPlanById(id, user.getUsername()));
    }

    @PostMapping
    public ResponseEntity<DietPlanDto> createPlan(@RequestBody CreateDietPlanRequest request,
                                                  @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dietPlanService.createPlan(request, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails user) {
        dietPlanService.deletePlan(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/meals")
    public ResponseEntity<DietPlanDto> addMeal(@PathVariable Long id,
                                               @RequestBody AddMealToPlanRequest request,
                                               @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(dietPlanService.addMealToPlan(id, request, user.getUsername()));
    }

    @DeleteMapping("/{id}/meals/{entryId}")
    public ResponseEntity<Void> removeMeal(@PathVariable Long id,
                                           @PathVariable Long entryId,
                                           @AuthenticationPrincipal UserDetails user) {
        dietPlanService.removeMealFromPlan(id, entryId, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
