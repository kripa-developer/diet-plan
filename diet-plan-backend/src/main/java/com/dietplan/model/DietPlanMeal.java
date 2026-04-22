package com.dietplan.model;

import com.dietplan.model.enums.DayOfWeek;
import com.dietplan.model.enums.MealTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diet_plan_meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietPlanMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_plan_id", nullable = false)
    private DietPlan dietPlan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private MealTime mealTime;

    private Integer servings;
}
