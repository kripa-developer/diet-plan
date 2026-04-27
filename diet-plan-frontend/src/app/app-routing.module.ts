import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard, AdminGuard } from './core/guards/auth.guard';

import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MealsComponent } from './pages/meals/meals.component';
import { DietPlansComponent } from './pages/diet-plans/diet-plans.component';
import { DietPlanDetailComponent } from './pages/diet-plans/diet-plan-detail/diet-plan-detail.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AdminMealsComponent } from './pages/admin/admin-meals/admin-meals.component';

const routes: Routes = [
  { path: '',          component: LandingComponent },
  { path: 'login',     component: LoginComponent },
  { path: 'register',  component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent,    canActivate: [AuthGuard] },
  { path: 'meals',     component: MealsComponent,        canActivate: [AuthGuard] },
  { path: 'plans',     component: DietPlansComponent,    canActivate: [AuthGuard] },
  { path: 'plans/:id', component: DietPlanDetailComponent, canActivate: [AuthGuard] },
  { path: 'profile',   component: ProfileComponent,      canActivate: [AuthGuard] },
  { path: 'admin/meals', component: AdminMealsComponent, canActivate: [AuthGuard, AdminGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top' })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
