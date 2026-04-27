package com.dietplan.service;

import com.dietplan.dto.AiMealResponse;
import com.dietplan.model.User;
import com.dietplan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiMealService {

    private final UserRepository userRepository;
    private final GeminiAiService geminiService;

    public AiMealResponse generateMealSuggestion(String userPrompt) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        double bmi = user.getBmi() != null ? user.getBmi() : 22.0;
        int target = user.getDailyCalorieTarget() != null ? user.getDailyCalorieTarget() : 2000;

        String systemPrompt = String.format(
            "Act as a professional nutritionist and gourmet chef. Your goal is to suggest ONE perfect meal for a user.\n" +
            "User Profile: BMI is %.1f, Daily Calorie Target is %d kcal.\n" +
            "Rules:\n" +
            "1. Suggest a healthy, delicious meal based on their craving.\n" +
            "2. If the craving is unhealthy (junk food/sweets), suggest a high-protein 'Healthy Swap' that satisfies the same taste profile.\n" +
            "3. Return ONLY a JSON object with these fields: mealName, category (BREAKFAST/LUNCH/DINNER/SNACK), calories, proteinG, carbsG, fatG, description, aiReasoning.\n" +
            "4. aiReasoning should be encouraging and explain why this fits their BMI/goals.",
            bmi, target
        );

        try {
            return geminiService.getCompletion(systemPrompt, userPrompt);
        } catch (Exception e) {
            // LOG THE ERROR TO CONSOLE
            System.err.println("=== AI GENERATION ERROR ===");
            e.printStackTrace();
            System.err.println("===========================");
            
            // Fallback to a simple response if Gemini fails or API key is missing
            return AiMealResponse.builder()
                    .mealName("Healthy Greek Bowl")
                    .category("LUNCH")
                    .calories(450)
                    .proteinG(32)
                    .carbsG(40)
                    .fatG(15)
                    .description("Grilled chicken, feta, olives, and cucumbers over brown rice.")
                    .aiReasoning("I'm currently in basic mode (Check your terminal for the actual AI error!). This bowl is a great balanced choice for your " + target + "kcal goal.")
                    .build();
        }
    }
}
