package com.dietplan.service;

import com.dietplan.dto.MealDto;
import com.dietplan.exception.ResourceNotFoundException;
import com.dietplan.model.Meal;
import com.dietplan.model.enums.MealCategory;
import com.dietplan.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    public List<MealDto> getAllMeals() {
        return mealRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<MealDto> getMealsByCategory(MealCategory category) {
        return mealRepository.findByCategory(category).stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<MealDto> searchMeals(String query) {
        return mealRepository.searchMeals(query).stream().map(this::toDto).collect(Collectors.toList());
    }

    public MealDto getMealById(Long id) {
        return toDto(findById(id));
    }

    public MealDto createMeal(MealDto dto) {
        Meal meal = toEntity(dto);
        return toDto(mealRepository.save(meal));
    }

    public MealDto updateMeal(Long id, MealDto dto) {
        Meal meal = findById(id);
        meal.setName(dto.getName());
        meal.setDescription(dto.getDescription());
        meal.setCategory(dto.getCategory());
        meal.setCalories(dto.getCalories());
        meal.setProteinG(dto.getProteinG());
        meal.setCarbsG(dto.getCarbsG());
        meal.setFatG(dto.getFatG());
        meal.setFiberG(dto.getFiberG());
        meal.setImageUrl(dto.getImageUrl());
        return toDto(mealRepository.save(meal));
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    private Meal findById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));
    }

    public MealDto toDto(Meal meal) {
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .description(meal.getDescription())
                .category(meal.getCategory())
                .calories(meal.getCalories())
                .proteinG(meal.getProteinG())
                .carbsG(meal.getCarbsG())
                .fatG(meal.getFatG())
                .fiberG(meal.getFiberG())
                .imageUrl(meal.getImageUrl())
                .build();
    }

    private Meal toEntity(MealDto dto) {
        return Meal.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .calories(dto.getCalories())
                .proteinG(dto.getProteinG())
                .carbsG(dto.getCarbsG())
                .fatG(dto.getFatG())
                .fiberG(dto.getFiberG())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
