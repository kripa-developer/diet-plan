import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DietPlanService } from '../../../core/services/diet-plan.service';
import { DietPlan, DayOfWeek, DietPlanMealEntry } from '../../../core/models/diet-plan.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-diet-plan-detail',
  templateUrl: './diet-plan-detail.component.html',
  styleUrls: ['./diet-plan-detail.component.css']
})
export class DietPlanDetailComponent implements OnInit {
  plan: DietPlan | null = null;
  loading = true;

  days: DayOfWeek[] = ['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'];

  constructor(
    private route: ActivatedRoute,
    private planService: DietPlanService,
    private snack: MatSnackBar
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.planService.getPlanById(id).subscribe(p => { this.plan = p; this.loading = false; });
  }

  getMealsForDay(day: DayOfWeek): DietPlanMealEntry[] {
    return this.plan?.meals.filter(m => m.dayOfWeek === day) ?? [];
  }

  remove(entryId: number) {
    if (!this.plan?.id) return;
    this.planService.removeMealFromPlan(this.plan.id, entryId).subscribe(() => {
      if (this.plan) {
        this.plan.meals = this.plan.meals.filter(m => m.id !== entryId);
        this.plan.totalCalories = this.plan.meals.reduce(
          (s, e) => s + e.meal.calories * e.servings, 0);
      }
      this.snack.open('Meal removed', 'Close', { duration: 2000 });
    });
  }

  formatDay(d: string) { return d.charAt(0) + d.slice(1).toLowerCase(); }
  formatTime(t: string) { return t.replace(/_/g,' ').toLowerCase().replace(/\b\w/g, c => c.toUpperCase()); }
}
