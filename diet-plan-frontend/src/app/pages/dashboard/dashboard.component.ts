import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { DietPlanService } from '../../core/services/diet-plan.service';
import { User } from '../../core/models/user.model';
import { DietPlan } from '../../core/models/diet-plan.model';
import { MatDialog } from '@angular/material/dialog';
import { AiMealGeneratorComponent } from '../../shared/ai-meal-generator/ai-meal-generator.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  Math = Math; // expose to template
  user: User | null = null;
  plans: DietPlan[] = [];
  loading = true;

  constructor(
    private userService: UserService, 
    private planService: DietPlanService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.userService.getProfile().subscribe(u => {
      this.user = u;
      this.loading = false;
    });
    this.planService.getMyPlans().subscribe(p => this.plans = p);
  }

  openAiGenerator() {
    this.dialog.open(AiMealGeneratorComponent, {
      width: '500px',
      panelClass: 'premium-dialog'
    });
  }

  get bmiStatus(): { label: string; color: string } {
    const b = this.user?.bmi ?? 0;
    if (b < 18.5) return { label: 'Underweight', color: '#f59e0b' };
    if (b < 25)   return { label: 'Normal',      color: '#22c55e' };
    if (b < 30)   return { label: 'Overweight',  color: '#f97316' };
    return             { label: 'Obese',        color: '#ef4444' };
  }

  get calorieProgress(): number {
    const plan = this.plans[0];
    if (!plan || !this.user?.dailyCalorieTarget) return 0;
    return Math.min(100, Math.round((plan.totalCalories ?? 0) / this.user.dailyCalorieTarget * 100));
  }

  get latestPlan(): DietPlan | null { return this.plans[0] ?? null; }

  get proteinTotal(): number {
    return Math.round(this.latestPlan?.meals.reduce((s, e) => s + (e.meal.proteinG ?? 0) * e.servings, 0) ?? 0);
  }
  get carbsTotal(): number {
    return Math.round(this.latestPlan?.meals.reduce((s, e) => s + (e.meal.carbsG ?? 0) * e.servings, 0) ?? 0);
  }
  get fatTotal(): number {
    return Math.round(this.latestPlan?.meals.reduce((s, e) => s + (e.meal.fatG ?? 0) * e.servings, 0) ?? 0);
  }
}
