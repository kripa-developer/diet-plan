package com.dietplan.repository;

import com.dietplan.model.Meal;
import com.dietplan.model.enums.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByCategory(MealCategory category);

    @Query("SELECT m FROM Meal m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(m.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Meal> searchMeals(String query);
}
