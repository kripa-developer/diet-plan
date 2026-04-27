import { Meal } from './meal.model';

export type DayOfWeek = 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY' | 'SATURDAY' | 'SUNDAY';
export type MealTime = 'BREAKFAST' | 'MID_MORNING' | 'LUNCH' | 'AFTERNOON_SNACK' | 'DINNER' | 'EVENING';

export interface DietPlanMealEntry {
  id?: number;
  meal: Meal;
  dayOfWeek: DayOfWeek;
  mealTime: MealTime;
  servings: number;
}

export interface DietPlan {
  id?: number;
  name: string;
  description?: string;
  startDate?: string;
  goal?: string;
  meals: DietPlanMealEntry[];
  totalCalories?: number;
}

export interface CreateDietPlanRequest {
  name: string;
  description?: string;
  startDate?: string;
  goal?: string;
}

export interface AddMealToPlanRequest {
  mealId: number;
  dayOfWeek: DayOfWeek;
  mealTime: MealTime;
  servings: number;
}
