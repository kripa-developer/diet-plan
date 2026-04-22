package com.dietplan.repository;

import com.dietplan.model.DietPlanMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DietPlanMealRepository extends JpaRepository<DietPlanMeal, Long> {
    List<DietPlanMeal> findByDietPlanId(Long dietPlanId);
    void deleteByDietPlanIdAndId(Long dietPlanId, Long id);
}
