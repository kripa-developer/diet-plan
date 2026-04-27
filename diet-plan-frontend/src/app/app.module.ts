import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

// Angular Material
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatBadgeModule } from '@angular/material/badge';
import { MatSidenavModule } from '@angular/material/sidenav';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Auth interceptor
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthService } from './core/services/auth.service';

@Injectable()
class JwtInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.auth.getToken();
    if (token) {
      req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }
    return next.handle(req);
  }
}

// Pages
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MealsComponent } from './pages/meals/meals.component';
import { DietPlansComponent } from './pages/diet-plans/diet-plans.component';
import { DietPlanDetailComponent } from './pages/diet-plans/diet-plan-detail/diet-plan-detail.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AdminMealsComponent } from './pages/admin/admin-meals/admin-meals.component';

// Shared
import { NavbarComponent } from './shared/navbar/navbar.component';
import { MealCardComponent } from './shared/meal-card/meal-card.component';
import { AiMealGeneratorComponent } from './shared/ai-meal-generator/ai-meal-generator.component';

const MATERIAL = [
  MatSnackBarModule, MatDialogModule, MatProgressSpinnerModule, MatTooltipModule,
  MatMenuModule, MatIconModule, MatButtonModule, MatChipsModule, MatFormFieldModule,
  MatInputModule, MatSelectModule, MatTabsModule, MatCardModule, MatBadgeModule,
  MatSidenavModule
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent, MealCardComponent, AiMealGeneratorComponent,
    LandingComponent,
    LoginComponent, RegisterComponent,
    DashboardComponent,
    MealsComponent,
    DietPlansComponent, DietPlanDetailComponent,
    ProfileComponent,
    AdminMealsComponent,
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule,
    HttpClientModule, FormsModule, ReactiveFormsModule,
    AppRoutingModule, ...MATERIAL
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
