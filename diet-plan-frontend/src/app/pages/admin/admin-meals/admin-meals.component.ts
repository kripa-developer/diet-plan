import { Component, OnInit } from '@angular/core';
import { MealService } from '../../../core/services/meal.service';
import { Meal } from '../../../core/models/meal.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin-meals',
  templateUrl: './admin-meals.component.html',
  styleUrls: ['./admin-meals.component.css']
})
export class AdminMealsComponent implements OnInit {
  meals: Meal[] = [];
  loading = true;
  showForm = false;
  editing = false;

  categories = ['BREAKFAST', 'LUNCH', 'DINNER', 'SNACK', 'DRINK'];

  form: Meal = this.blankForm();

  constructor(private mealService: MealService, private snack: MatSnackBar) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.mealService.getMeals().subscribe(m => { this.meals = m; this.loading = false; });
  }

  blankForm(): Meal {
    return { name: '', category: 'BREAKFAST', calories: 0,
             proteinG: 0, carbsG: 0, fatG: 0, fiberG: 0, description: '', imageUrl: '' };
  }

  openCreate() { this.form = this.blankForm(); this.editing = false; this.showForm = true; }

  openEdit(meal: Meal) {
    this.form = { ...meal };
    this.editing = true;
    this.showForm = true;
  }

  save() {
    if (this.editing && this.form.id) {
      this.mealService.updateMeal(this.form.id, this.form).subscribe({
        next: m => {
          this.meals = this.meals.map(x => x.id === m.id ? m : x);
          this.showForm = false;
          this.snack.open('Meal updated!', 'Close', { duration: 2000 });
        }
      });
    } else {
      this.mealService.createMeal(this.form).subscribe({
        next: m => {
          this.meals.unshift(m);
          this.showForm = false;
          this.snack.open('Meal created!', 'Close', { duration: 2000 });
        }
      });
    }
  }

  delete(id: number) {
    if (!confirm('Delete this meal?')) return;
    this.mealService.deleteMeal(id).subscribe(() => {
      this.meals = this.meals.filter(m => m.id !== id);
      this.snack.open('Meal deleted', 'Close', { duration: 2000 });
    });
  }

  cancel() { this.showForm = false; }
}
