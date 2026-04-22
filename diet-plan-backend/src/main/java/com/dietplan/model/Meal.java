package com.dietplan.model;

import com.dietplan.model.enums.MealCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealCategory category;

    @Column(nullable = false)
    private Integer calories;   // kcal

    private Double proteinG;    // grams
    private Double carbsG;      // grams
    private Double fatG;        // grams
    private Double fiberG;

    private String imageUrl;
}
