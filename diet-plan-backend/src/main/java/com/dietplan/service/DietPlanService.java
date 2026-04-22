package com.dietplan.service;

import com.dietplan.dto.AddMealToPlanRequest;
import com.dietplan.dto.CreateDietPlanRequest;
import com.dietplan.dto.DietPlanDto;
import com.dietplan.exception.ResourceNotFoundException;
import com.dietplan.model.DietPlan;
import com.dietplan.model.DietPlanMeal;
import com.dietplan.model.Meal;
import com.dietplan.model.User;
import com.dietplan.repository.DietPlanMealRepository;
import com.dietplan.repository.DietPlanRepository;
import com.dietplan.repository.MealRepository;
import com.dietplan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietPlanService {

    private final DietPlanRepository dietPlanRepository;
    private final DietPlanMealRepository dietPlanMealRepository;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final MealService mealService;

    public List<DietPlanDto> getUserPlans(String email) {
        User user = findUser(email);
        return dietPlanRepository.findByUserOrderByIdDesc(user).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    public DietPlanDto getPlanById(Long planId, String email) {
        DietPlan plan = findPlan(planId);
        assertOwner(plan, email);
        return toDto(plan);
    }

    public DietPlanDto createPlan(CreateDietPlanRequest req, String email) {
        User user = findUser(email);
        DietPlan plan = DietPlan.builder()
                .name(req.getName())
                .description(req.getDescription())
                .startDate(req.getStartDate())
                .goal(req.getGoal())
                .user(user)
                .build();
        return toDto(dietPlanRepository.save(plan));
    }

    public void deletePlan(Long planId, String email) {
        DietPlan plan = findPlan(planId);
        assertOwner(plan, email);
        dietPlanRepository.delete(plan);
    }

    @Transactional
    public DietPlanDto addMealToPlan(Long planId, AddMealToPlanRequest req, String email) {
        DietPlan plan = findPlan(planId);
        assertOwner(plan, email);
        Meal meal = mealRepository.findById(req.getMealId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found"));

        DietPlanMeal entry = DietPlanMeal.builder()
                .dietPlan(plan)
                .meal(meal)
                .dayOfWeek(req.getDayOfWeek())
                .mealTime(req.getMealTime())
                .servings(req.getServings() != null ? req.getServings() : 1)
                .build();
        plan.getMeals().add(entry);
        return toDto(dietPlanRepository.save(plan));
    }

    @Transactional
    public void removeMealFromPlan(Long planId, Long entryId, String email) {
        DietPlan plan = findPlan(planId);
        assertOwner(plan, email);
        dietPlanMealRepository.deleteByDietPlanIdAndId(planId, entryId);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private DietPlan findPlan(Long id) {
        return dietPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diet plan not found"));
    }

    private void assertOwner(DietPlan plan, String email) {
        if (!plan.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You do not own this plan");
        }
    }

    private DietPlanDto toDto(DietPlan plan) {
        List<DietPlanDto.DietPlanMealDto> mealDtos = plan.getMeals().stream()
                .map(entry -> DietPlanDto.DietPlanMealDto.builder()
                        .id(entry.getId())
                        .meal(mealService.toDto(entry.getMeal()))
                        .dayOfWeek(entry.getDayOfWeek())
                        .mealTime(entry.getMealTime())
                        .servings(entry.getServings())
                        .build())
                .collect(Collectors.toList());

        int totalCalories = plan.getMeals().stream()
                .mapToInt(e -> e.getMeal().getCalories() * (e.getServings() != null ? e.getServings() : 1))
                .sum();

        return DietPlanDto.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .startDate(plan.getStartDate())
                .goal(plan.getGoal())
                .meals(mealDtos)
                .totalCalories(totalCalories)
                .build();
    }
}
