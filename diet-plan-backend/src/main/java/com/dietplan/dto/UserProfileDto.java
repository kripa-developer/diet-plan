package com.dietplan.dto;

import com.dietplan.model.enums.ActivityLevel;
import com.dietplan.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Double weightKg;
    private Double heightCm;
    private Gender gender;
    private ActivityLevel activityLevel;
    private Double bmi;
    private Integer dailyCalorieTarget;
}
