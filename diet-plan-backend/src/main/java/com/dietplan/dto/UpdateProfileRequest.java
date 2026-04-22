package com.dietplan.dto;

import com.dietplan.model.enums.ActivityLevel;
import com.dietplan.model.enums.Gender;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private Integer age;
    private Double weightKg;
    private Double heightCm;
    private Gender gender;
    private ActivityLevel activityLevel;
}
