package com.dietplan.dto;

import com.dietplan.model.enums.DayOfWeek;
import com.dietplan.model.enums.MealTime;
import lombok.Data;

@Data
public class AddMealToPlanRequest {
    private Long mealId;
    private DayOfWeek dayOfWeek;
    private MealTime mealTime;
    private Integer servings;
}
