package com.dietplan.dto;

import com.dietplan.model.enums.DayOfWeek;
import com.dietplan.model.enums.MealTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietPlanDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private String goal;
    private List<DietPlanMealDto> meals;
    private Integer totalCalories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DietPlanMealDto {
        private Long id;
        private MealDto meal;
        private DayOfWeek dayOfWeek;
        private MealTime mealTime;
        private Integer servings;
    }
}
