package com.dietplan.service;

import com.dietplan.dto.UpdateProfileRequest;
import com.dietplan.dto.UserProfileDto;
import com.dietplan.exception.ResourceNotFoundException;
import com.dietplan.model.User;
import com.dietplan.model.enums.ActivityLevel;
import com.dietplan.model.enums.Gender;
import com.dietplan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileDto getProfile(String email) {
        User user = findByEmail(email);
        return toDto(user);
    }

    public UserProfileDto updateProfile(String email, UpdateProfileRequest req) {
        User user = findByEmail(email);
        if (req.getName() != null) user.setName(req.getName());
        if (req.getAge() != null) user.setAge(req.getAge());
        if (req.getWeightKg() != null) user.setWeightKg(req.getWeightKg());
        if (req.getHeightCm() != null) user.setHeightCm(req.getHeightCm());
        if (req.getGender() != null) user.setGender(req.getGender());
        if (req.getActivityLevel() != null) user.setActivityLevel(req.getActivityLevel());
        userRepository.save(user);
        return toDto(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserProfileDto toDto(User user) {
        Double bmi = calculateBmi(user);
        Integer calories = calculateDailyCalories(user);
        return UserProfileDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .weightKg(user.getWeightKg())
                .heightCm(user.getHeightCm())
                .gender(user.getGender())
                .activityLevel(user.getActivityLevel())
                .bmi(bmi)
                .dailyCalorieTarget(calories)
                .build();
    }

    /**
     * BMI = weight(kg) / (height(m))^2
     */
    private Double calculateBmi(User user) {
        if (user.getWeightKg() == null || user.getHeightCm() == null) return null;
        double heightM = user.getHeightCm() / 100.0;
        return Math.round((user.getWeightKg() / (heightM * heightM)) * 10.0) / 10.0;
    }

    /**
     * Harris-Benedict equation + activity multiplier
     */
    private Integer calculateDailyCalories(User user) {
        if (user.getWeightKg() == null || user.getHeightCm() == null
                || user.getAge() == null || user.getGender() == null) return null;

        double bmr;
        if (user.getGender() == Gender.MALE) {
            bmr = 88.362 + (13.397 * user.getWeightKg())
                    + (4.799 * user.getHeightCm()) - (5.677 * user.getAge());
        } else {
            bmr = 447.593 + (9.247 * user.getWeightKg())
                    + (3.098 * user.getHeightCm()) - (4.330 * user.getAge());
        }

        double multiplier = 1.2; // default SEDENTARY
        if (user.getActivityLevel() != null) {
            multiplier = switch (user.getActivityLevel()) {
                case SEDENTARY       -> 1.2;
                case LIGHTLY_ACTIVE  -> 1.375;
                case MODERATELY_ACTIVE -> 1.55;
                case VERY_ACTIVE     -> 1.725;
                case EXTRA_ACTIVE    -> 1.9;
            };
        }
        return (int) Math.round(bmr * multiplier);
    }
}
