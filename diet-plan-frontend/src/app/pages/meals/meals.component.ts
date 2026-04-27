import { Component, OnInit } from '@angular/core';
import { MealService } from '../../core/services/meal.service';
import { DietPlanService } from '../../core/services/diet-plan.service';
import { Meal } from '../../core/models/meal.model';
import { DietPlan, DayOfWeek, MealTime } from '../../core/models/diet-plan.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-meals',
  templateUrl: './meals.component.html',
  styleUrls: ['./meals.component.css']
})
export class MealsComponent implements OnInit {
  meals: Meal[] = [];
  plans: DietPlan[] = [];
  loading = true;
  searchQuery = '';
  selectedCategory = '';

  categories = ['', 'BREAKFAST', 'LUNCH', 'DINNER', 'SNACK', 'DRINK'];

  // Add-to-plan dialog state
  showDialog = false;
  selectedMeal: Meal | null = null;
  selectedPlanId: number | null = null;
  selectedDay: DayOfWeek = 'MONDAY';
  selectedTime: MealTime = 'BREAKFAST';
  servings = 1;

  days: DayOfWeek[] = ['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'];
  times: MealTime[] = ['BREAKFAST','MID_MORNING','LUNCH','AFTERNOON_SNACK','DINNER','EVENING'];

  constructor(
    private mealService: MealService,
    private planService: DietPlanService,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadMeals();
    this.planService.getMyPlans().subscribe(p => this.plans = p);
  }

  loadMeals() {
    this.loading = true;
    this.mealService.getMeals(this.selectedCategory || undefined, this.searchQuery || undefined)
      .subscribe(m => { this.meals = m; this.loading = false; });
  }

  onSearch() { this.loadMeals(); }
  onCategory(c: string) { this.selectedCategory = c; this.loadMeals(); }

  openAddDialog(meal: Meal) {
    this.selectedMeal = meal;
    this.selectedPlanId = this.plans[0]?.id ?? null;
    this.showDialog = true;
  }

  closeDialog() { this.showDialog = false; this.selectedMeal = null; }

  addToPlan() {
    if (!this.selectedPlanId || !this.selectedMeal?.id) return;
    this.planService.addMealToPlan(this.selectedPlanId, {
      mealId: this.selectedMeal.id,
      dayOfWeek: this.selectedDay,
      mealTime: this.selectedTime,
      servings: this.servings
    }).subscribe({
      next: () => {
        this.snack.open('✅ Meal added to plan!', 'Close', { duration: 3000 });
        this.closeDialog();
      },
      error: () => this.snack.open('Failed to add meal', 'Close', { duration: 3000 })
    });
  }
}
