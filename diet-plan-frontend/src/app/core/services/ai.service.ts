import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AiMealResponse {
  mealName: string;
  category: string;
  calories: number;
  proteinG: number;
  carbsG: number;
  fatG: number;
  description: string;
  aiReasoning: string;
}

@Injectable({
  providedIn: 'root'
})
export class AiService {
  private apiUrl = `${environment.apiUrl}/ai`;

  constructor(private http: HttpClient) {}

  getSuggestion(prompt?: string): Observable<AiMealResponse> {
    let params = new HttpParams();
    if (prompt) {
      params = params.set('prompt', prompt);
    }
    return this.http.get<AiMealResponse>(`${this.apiUrl}/suggest`, { params });
  }
}
