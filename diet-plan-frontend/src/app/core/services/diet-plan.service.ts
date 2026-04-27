import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddMealToPlanRequest, CreateDietPlanRequest, DietPlan } from '../models/diet-plan.model';

@Injectable({ providedIn: 'root' })
export class DietPlanService {
  private readonly API = 'http://localhost:8811/api/diet-plans';

  constructor(private http: HttpClient) {}

  getMyPlans(): Observable<DietPlan[]> {
    return this.http.get<DietPlan[]>(this.API);
  }

  getPlanById(id: number): Observable<DietPlan> {
    return this.http.get<DietPlan>(`${this.API}/${id}`);
  }

  createPlan(req: CreateDietPlanRequest): Observable<DietPlan> {
    return this.http.post<DietPlan>(this.API, req);
  }

  deletePlan(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }

  addMealToPlan(planId: number, req: AddMealToPlanRequest): Observable<DietPlan> {
    return this.http.post<DietPlan>(`${this.API}/${planId}/meals`, req);
  }

  removeMealFromPlan(planId: number, entryId: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${planId}/meals/${entryId}`);
  }
}
