import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meal } from '../models/meal.model';

@Injectable({ providedIn: 'root' })
export class MealService {
  private readonly API = 'http://localhost:8811/api/meals';

  constructor(private http: HttpClient) {}

  getMeals(category?: string, search?: string): Observable<Meal[]> {
    let params = new HttpParams();
    if (category) params = params.set('category', category);
    if (search) params = params.set('search', search);
    return this.http.get<Meal[]>(this.API, { params });
  }

  getMealById(id: number): Observable<Meal> {
    return this.http.get<Meal>(`${this.API}/${id}`);
  }

  createMeal(meal: Meal): Observable<Meal> {
    return this.http.post<Meal>(this.API, meal);
  }

  updateMeal(id: number, meal: Meal): Observable<Meal> {
    return this.http.put<Meal>(`${this.API}/${id}`, meal);
  }

  deleteMeal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}
