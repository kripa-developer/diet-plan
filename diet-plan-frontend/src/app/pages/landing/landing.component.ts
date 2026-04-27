import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent {
  features = [
    { icon: '🥗', title: 'Meal Library', desc: 'Browse 20+ nutritionist-curated meals across all categories.' },
    { icon: '📋', title: 'Weekly Plans', desc: 'Build a full Mon–Sun diet plan tailored to your goals.' },
    { icon: '📊', title: 'Macro Tracking', desc: 'Auto-calculate calories, protein, carbs and fat per day.' },
    { icon: '🎯', title: 'BMI & Calories', desc: 'Harris-Benedict formula computes your daily calorie target.' },
    { icon: '⚡', title: 'Instant Setup', desc: 'Register, set your profile, and get a plan in minutes.' },
    { icon: '🔒', title: 'Secure & Private', desc: 'JWT-secured API — your data belongs to you alone.' },
  ];

  constructor(private router: Router) {}
  goRegister() { this.router.navigate(['/register']); }
}
