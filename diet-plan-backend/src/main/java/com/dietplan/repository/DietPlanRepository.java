package com.dietplan.repository;

import com.dietplan.model.DietPlan;
import com.dietplan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
    List<DietPlan> findByUser(User user);
    List<DietPlan> findByUserOrderByIdDesc(User user);
}
