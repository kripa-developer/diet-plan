package com.dietplan.dto;

import com.dietplan.model.enums.MealCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    private Long id;
    private String name;
    private String description;
    private MealCategory category;
    private Integer calories;
    private Double proteinG;
    private Double carbsG;
    private Double fatG;
    private Double fiberG;
    private String imageUrl;
}
