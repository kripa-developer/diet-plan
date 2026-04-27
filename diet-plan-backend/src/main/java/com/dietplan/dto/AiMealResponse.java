package com.dietplan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiMealResponse {
    private String mealName;
    private String category;
    private int calories;
    private int proteinG;
    private int carbsG;
    private int fatG;
    private String description;
    private String aiReasoning; // Why the AI suggested this
}
