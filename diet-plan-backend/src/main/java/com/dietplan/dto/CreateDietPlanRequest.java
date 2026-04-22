package com.dietplan.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateDietPlanRequest {
    private String name;
    private String description;
    private LocalDate startDate;
    private String goal;
}
