package com.dietplan.seeder;

import com.dietplan.model.Meal;
import com.dietplan.model.User;
import com.dietplan.model.enums.MealCategory;
import com.dietplan.model.enums.Role;
import com.dietplan.repository.MealRepository;
import com.dietplan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedMeals();
        seedAdminUser();
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail("admin@dietplan.com")) {
            userRepository.save(User.builder()
                    .name("Admin")
                    .email("admin@dietplan.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build());
        }
    }

    private void seedMeals() {
        if (mealRepository.count() > 0) return;

        List<Meal> meals = List.of(
            // ── BREAKFAST ──────────────────────────────────────────────────────
            Meal.builder().name("Oatmeal with Berries").category(MealCategory.BREAKFAST)
                .description("Steel-cut oats topped with fresh blueberries and strawberries")
                .calories(320).proteinG(12.0).carbsG(58.0).fatG(5.0).fiberG(8.0)
                .imageUrl("https://images.unsplash.com/photo-1517673132405-a56a62b18caf?w=400").build(),

            Meal.builder().name("Avocado Toast & Eggs").category(MealCategory.BREAKFAST)
                .description("Whole-grain toast with smashed avocado and two poached eggs")
                .calories(410).proteinG(18.0).carbsG(36.0).fatG(22.0).fiberG(7.0)
                .imageUrl("https://images.unsplash.com/photo-1525351484163-7529414344d8?w=400").build(),

            Meal.builder().name("Greek Yogurt Parfait").category(MealCategory.BREAKFAST)
                .description("Low-fat Greek yogurt layered with granola and mixed fruit")
                .calories(280).proteinG(20.0).carbsG(38.0).fatG(4.0).fiberG(3.0)
                .imageUrl("https://images.unsplash.com/photo-1488477181946-6428a0291777?w=400").build(),

            Meal.builder().name("Banana Protein Smoothie").category(MealCategory.BREAKFAST)
                .description("Blended banana, almond milk, protein powder and peanut butter")
                .calories(350).proteinG(28.0).carbsG(42.0).fatG(8.0).fiberG(4.0)
                .imageUrl("https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=400").build(),

            Meal.builder().name("Veggie Omelette").category(MealCategory.BREAKFAST)
                .description("Three-egg omelette with spinach, tomatoes and feta cheese")
                .calories(290).proteinG(24.0).carbsG(6.0).fatG(19.0).fiberG(2.0)
                .imageUrl("https://images.unsplash.com/photo-1510693206972-df098062cb71?w=400").build(),

            // ── LUNCH ──────────────────────────────────────────────────────────
            Meal.builder().name("Grilled Chicken Salad").category(MealCategory.LUNCH)
                .description("Grilled chicken breast on a bed of romaine, cherry tomatoes and balsamic")
                .calories(380).proteinG(42.0).carbsG(14.0).fatG(16.0).fiberG(5.0)
                .imageUrl("https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400").build(),

            Meal.builder().name("Quinoa Buddha Bowl").category(MealCategory.LUNCH)
                .description("Quinoa, roasted sweet potato, chickpeas and tahini dressing")
                .calories(450).proteinG(18.0).carbsG(65.0).fatG(14.0).fiberG(12.0)
                .imageUrl("https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400").build(),

            Meal.builder().name("Turkey & Avocado Wrap").category(MealCategory.LUNCH)
                .description("Whole-wheat tortilla with turkey, avocado, lettuce and mustard")
                .calories(420).proteinG(32.0).carbsG(38.0).fatG(16.0).fiberG(6.0)
                .imageUrl("https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=400").build(),

            Meal.builder().name("Lentil Soup").category(MealCategory.LUNCH)
                .description("Hearty red lentil soup with cumin, carrots and fresh cilantro")
                .calories(310).proteinG(18.0).carbsG(48.0).fatG(4.0).fiberG(15.0)
                .imageUrl("https://images.unsplash.com/photo-1547592180-85f173990554?w=400").build(),

            Meal.builder().name("Tuna Poke Bowl").category(MealCategory.LUNCH)
                .description("Sushi-grade tuna over brown rice with edamame and sesame")
                .calories(490).proteinG(38.0).carbsG(52.0).fatG(14.0).fiberG(5.0)
                .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400").build(),

            // ── DINNER ─────────────────────────────────────────────────────────
            Meal.builder().name("Baked Salmon & Vegetables").category(MealCategory.DINNER)
                .description("Herb-crusted salmon fillet with roasted asparagus and lemon")
                .calories(460).proteinG(48.0).carbsG(10.0).fatG(24.0).fiberG(4.0)
                .imageUrl("https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400").build(),

            Meal.builder().name("Chicken Stir-Fry").category(MealCategory.DINNER)
                .description("Lean chicken with broccoli, bell peppers in light soy-ginger sauce")
                .calories(390).proteinG(36.0).carbsG(28.0).fatG(12.0).fiberG(6.0)
                .imageUrl("https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400").build(),

            Meal.builder().name("Turkey Meatballs & Zoodles").category(MealCategory.DINNER)
                .description("Lean turkey meatballs with zucchini noodles in marinara sauce")
                .calories(360).proteinG(34.0).carbsG(22.0).fatG(14.0).fiberG(5.0)
                .imageUrl("https://images.unsplash.com/photo-1555126634-323283e090fa?w=400").build(),

            Meal.builder().name("Black Bean Tacos").category(MealCategory.DINNER)
                .description("Corn tortillas with seasoned black beans, avocado and salsa")
                .calories(420).proteinG(16.0).carbsG(62.0).fatG(14.0).fiberG(14.0)
                .imageUrl("https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400").build(),

            Meal.builder().name("Lean Beef Steak & Sweet Potato").category(MealCategory.DINNER)
                .description("Sirloin steak with baked sweet potato and steamed green beans")
                .calories(510).proteinG(45.0).carbsG(38.0).fatG(18.0).fiberG(6.0)
                .imageUrl("https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400").build(),

            // ── SNACKS ─────────────────────────────────────────────────────────
            Meal.builder().name("Apple & Almond Butter").category(MealCategory.SNACK)
                .description("Sliced apple with 2 tbsp natural almond butter")
                .calories(200).proteinG(5.0).carbsG(26.0).fatG(9.0).fiberG(4.0)
                .imageUrl("https://images.unsplash.com/photo-1567306226416-28f0efdc88ce?w=400").build(),

            Meal.builder().name("Mixed Nuts").category(MealCategory.SNACK)
                .description("A small handful of walnuts, almonds and cashews")
                .calories(180).proteinG(5.0).carbsG(8.0).fatG(16.0).fiberG(2.0)
                .imageUrl("https://images.unsplash.com/photo-1548848221-0c2e497ed557?w=400").build(),

            Meal.builder().name("Hummus & Veggie Sticks").category(MealCategory.SNACK)
                .description("Classic hummus with carrot, celery and cucumber sticks")
                .calories(160).proteinG(6.0).carbsG(18.0).fatG(7.0).fiberG(5.0)
                .imageUrl("https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=400").build(),

            Meal.builder().name("Cottage Cheese & Pineapple").category(MealCategory.SNACK)
                .description("Low-fat cottage cheese topped with fresh pineapple chunks")
                .calories(150).proteinG(16.0).carbsG(14.0).fatG(2.0).fiberG(1.0)
                .imageUrl("https://images.unsplash.com/photo-1563636619-e9143da7973b?w=400").build(),

            // ── DRINKS ─────────────────────────────────────────────────────────
            Meal.builder().name("Green Detox Smoothie").category(MealCategory.DRINK)
                .description("Spinach, cucumber, ginger, lemon and coconut water blend")
                .calories(110).proteinG(3.0).carbsG(22.0).fatG(1.0).fiberG(4.0)
                .imageUrl("https://images.unsplash.com/photo-1610970881699-44a5587cabec?w=400").build(),

            Meal.builder().name("Whey Protein Shake").category(MealCategory.DRINK)
                .description("Chocolate whey protein blended with low-fat milk and banana")
                .calories(300).proteinG(32.0).carbsG(32.0).fatG(4.0).fiberG(2.0)
                .imageUrl("https://images.unsplash.com/photo-1593095948071-474c5cc2989d?w=400").build()
        );

        mealRepository.saveAll(meals);
        System.out.println("✅ Seeded " + meals.size() + " meals.");
    }
}
