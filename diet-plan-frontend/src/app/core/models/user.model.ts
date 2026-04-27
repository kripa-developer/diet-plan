export interface User {
  id?: number;
  name: string;
  email: string;
  role?: string;
  age?: number;
  weightKg?: number;
  heightCm?: number;
  gender?: 'MALE' | 'FEMALE' | 'OTHER';
  activityLevel?: 'SEDENTARY' | 'LIGHTLY_ACTIVE' | 'MODERATELY_ACTIVE' | 'VERY_ACTIVE' | 'EXTRA_ACTIVE';
  bmi?: number;
  dailyCalorieTarget?: number;
}

export interface AuthResponse {
  token: string;
  email: string;
  name: string;
  role: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}
